package com.sensiple.baxter.print;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.pdf.PrintedPdfDocument;
import android.util.Log;

import com.sensiple.baxter.R;
import com.sensiple.baxter.fragment.FragmentComedy;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import static com.sensiple.baxter.fragment.FragmentComedy.formValues;

/**
 * Created by boobeshb on 4/12/2017.
 */

public class MyPrintAdapter extends PrintDocumentAdapter {


    PrintedPdfDocument myPdfDocument;
    Context applicationContext;
    private int pageHeight;
    private int pageWidth;
    int totalPages = 1 ;
    Map projectDetailsMap;
    int radioButtonId;

    public MyPrintAdapter(Context context,int id) {
        super();
        applicationContext = context;
        radioButtonId = id;
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes, CancellationSignal cancellationSignal, LayoutResultCallback callback, Bundle extras) {

        projectDetailsMap  = formValues;

        myPdfDocument = new PrintedPdfDocument(applicationContext, newAttributes);

        pageHeight =
                newAttributes.getMediaSize().getHeightMils() / 1000 * 72;
        pageWidth =
                newAttributes.getMediaSize().getWidthMils() / 1000 * 72;

        if (cancellationSignal.isCanceled()) {
            callback.onLayoutCancelled();
            return;
        }


        if (totalPages > 0) {
            PrintDocumentInfo.Builder builder = new PrintDocumentInfo
                    .Builder("print_output.pdf")
                    .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                    .setPageCount(totalPages);

            PrintDocumentInfo info = builder.build();

            callback.onLayoutFinished(info, true);
        } else {
            callback.onLayoutFailed("Page count is zero.");

            Log.e("on layout ", "On write finished ");

        }
    }

  /*  private int computePageCount(PrintAttributes printAttributes) {
        int itemsPerPage = 4; // default item count for portrait mode

        PrintAttributes.MediaSize pageSize = printAttributes.getMediaSize();
        if (!pageSize.isPortrait()) {
            // Six items per page in landscape orientation
            itemsPerPage = 6;
        }

        // Determine number of print items
        int printItemCount = 2;

        return (int) Math.ceil(printItemCount / itemsPerPage);
    }*/



    @Override
    public void onWrite(PageRange[] pages, ParcelFileDescriptor destination, CancellationSignal cancellationSignal, WriteResultCallback callback) {
        // Iterate over each page of the document,
        // check if it's in the output range.
        for (int i = 0; i < totalPages; i++) {
             System.out.print("for loop count "+ "      " + i);
            Log.d("ON write for loop count" , ""+i);
            if (pageInRange(pages, i))
            {
                System.out.print("if loop "+ "      " + i);
                Log.d("ON write if loop" , ""+i);

                PdfDocument.PageInfo newPage = new PdfDocument.PageInfo.Builder(pageWidth,
                        pageHeight, i).create();

                PdfDocument.Page page =
                        myPdfDocument.startPage(newPage);

                if (cancellationSignal.isCanceled()) {
                    callback.onWriteCancelled();
                    myPdfDocument.close();
                    myPdfDocument = null;
                    return;
                }
                drawPage(page, i);
                myPdfDocument.finishPage(page);
            }
        }

        try {
            myPdfDocument.writeTo(new FileOutputStream(
                    destination.getFileDescriptor()));
        } catch (IOException e) {
            callback.onWriteFailed(e.toString());
            return;
        } finally {
            myPdfDocument.close();
            myPdfDocument = null;
        }

        callback.onWriteFinished(pages);

    }


    private void drawPage(PdfDocument.Page page,
                          int pagenumber) {


        Log.d("drawPage" , ""+pagenumber);
        int baselineConstant = 20;
        int spaceBetweenSection = 40;
        int spaceBetweenHeadingAndContents = 30;
        Canvas canvas = page.getCanvas();
        int titleBaseLine = 72;
        pagenumber++;
        int leftMarginForHeading = 130;
        int leftMarginForSubheading = 50;

        Paint headingPaint = new Paint();
        headingPaint.setColor(Color.BLUE);
        headingPaint.setTextSize(30);


        Paint bodyPaint = new Paint();
        bodyPaint.setColor(Color.BLACK);
        bodyPaint.setTextSize(17);

        canvas.drawText("New Project Details",leftMarginForHeading,titleBaseLine,headingPaint);
        canvas.drawText("Project Information",leftMarginForSubheading,titleBaseLine = titleBaseLine+spaceBetweenSection,headingPaint);
        canvas.drawText("Date Entered " + " : "  +projectDetailsMap.get("entered_date"),leftMarginForSubheading,titleBaseLine = titleBaseLine+spaceBetweenHeadingAndContents,bodyPaint );
        canvas.drawText("Date Requested " + "  : " + projectDetailsMap.get("requested_date"),leftMarginForSubheading,titleBaseLine = titleBaseLine+baselineConstant,bodyPaint);
        canvas.drawText("Time Requested " + "  : " + projectDetailsMap.get("time"),leftMarginForSubheading,titleBaseLine = titleBaseLine+baselineConstant,bodyPaint);
        canvas.drawText("Short Description " + "  : " + projectDetailsMap.get("shortdescription"),leftMarginForSubheading,titleBaseLine = titleBaseLine+baselineConstant,bodyPaint);
        canvas.drawText("Description " + "  : " + projectDetailsMap.get("description"),leftMarginForSubheading,titleBaseLine = titleBaseLine+baselineConstant,bodyPaint);
        canvas.drawText("Notes " + "  : " + projectDetailsMap.get("notes"),leftMarginForSubheading,titleBaseLine = titleBaseLine+baselineConstant,bodyPaint);
        canvas.drawText("Billing Information",leftMarginForSubheading,titleBaseLine = titleBaseLine+spaceBetweenSection,headingPaint);
        canvas.drawText("Customer Name" + "  : " + projectDetailsMap.get("customername"),leftMarginForSubheading,titleBaseLine = titleBaseLine+spaceBetweenHeadingAndContents,bodyPaint);
        canvas.drawText("Choose Customer " + "  : " + projectDetailsMap.get("chooseCustomer"),leftMarginForSubheading,titleBaseLine = titleBaseLine+baselineConstant,bodyPaint);
        canvas.drawText("customer Address " + "  : " + projectDetailsMap.get("customerAddress"),leftMarginForSubheading,titleBaseLine = titleBaseLine+baselineConstant,bodyPaint);

        switch(radioButtonId) {
            case R.id.radioCustomerPo:
                canvas.drawText("customer Po " + "  : " + projectDetailsMap.get("customerPO"),leftMarginForSubheading,titleBaseLine = titleBaseLine+baselineConstant,bodyPaint);
                break;
            case R.id.radioJob:
                canvas.drawText("customer Job  " + "  : " + projectDetailsMap.get("customerJob"),leftMarginForSubheading,titleBaseLine = titleBaseLine+baselineConstant,bodyPaint);
                break;
        }

        canvas.drawText("Requested By " + "  : " + projectDetailsMap.get("requestedBy"),leftMarginForSubheading,titleBaseLine = titleBaseLine+baselineConstant,bodyPaint);
        canvas.drawText("Shipping Information",leftMarginForSubheading,titleBaseLine = titleBaseLine+spaceBetweenSection,headingPaint);
        canvas.drawText("Project Name " + "  : " + projectDetailsMap.get("projectName"),leftMarginForSubheading,titleBaseLine = titleBaseLine+spaceBetweenHeadingAndContents,bodyPaint);
        canvas.drawText("Complete Check Box " + "  : " + projectDetailsMap.get("completecheckbox"),leftMarginForSubheading,titleBaseLine = titleBaseLine+baselineConstant,bodyPaint);
        canvas.drawText("No Material Needed Checkbox " + "  : " + projectDetailsMap.get("noMaterialNeededcheckbox"),leftMarginForSubheading,titleBaseLine = titleBaseLine+baselineConstant,bodyPaint);
        canvas.drawText("Closed Check Box" + "  : " + projectDetailsMap.get("closedcheckbox"),leftMarginForSubheading,titleBaseLine = titleBaseLine+baselineConstant,bodyPaint);


        // Make sure page numbers start at 1

    }

    private boolean pageInRange(PageRange[] pageRanges, int page)
    {
        for (int i = 0; i<pageRanges.length; i++)
        {
            if ((page >= pageRanges[i].getStart()) &&
                    (page <= pageRanges[i].getEnd()))
                return true;
        }
        return false;
    }

    @Override
    public void onFinish() {
        super.onFinish();
        Log.d("onfinish ","On finish method printing success ");
    }

   /* public Boolean containsPage(PageRange[] page,int i){
        if (page.length<i){
            return true;
        }else{
            return false ;
        }

    }*/

   /* private void drawPage(PdfDocument.Page page) {
        Canvas canvas = page.getCanvas();

        // units are in points (1/72 of an inch)
        int titleBaseLine = 72;
        int leftMargin = 54;

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(36);
        canvas.drawText("Test Title", leftMargin, titleBaseLine, paint);

        paint.setTextSize(11);
        canvas.drawText("Test paragraph", leftMargin, titleBaseLine + 25, paint);

        paint.setColor(Color.BLUE);
        canvas.drawRect(100, 100, 172, 172, paint);
    }*/

}
