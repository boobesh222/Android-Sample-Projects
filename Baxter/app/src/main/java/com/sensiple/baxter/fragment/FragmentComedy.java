package com.sensiple.baxter.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.print.PrintManager;
import android.print.pdf.PrintedPdfDocument;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.sensiple.baxter.R;
import com.sensiple.baxter.print.GeneratePrintCopy;
import com.sensiple.baxter.print.MyPrintAdapter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FragmentComedy extends Fragment {

    private static final String KEY_MOVIE_TITLE = "key_title";
     GeneratePrintCopy gpc = new GeneratePrintCopy();
     EditText mTxtEnteredDate, mTxtRequestedDate, mTxtGetTime, mTxtShortDescription, mTxtDescription, mTxtNotes;
     EditText customerName,customerAddress,chooseCustomer,requestedBy,customerPO,customerJob,projectName;
     CheckBox complete,noMaterialNeeded,closed;
     Button mSignInBtn ,printBtn;
     Spinner mgetLocationSpinner;
     Spinner mgetJobClassSpinner;
     int mYear, mMonth, mDay, mHour, mMinute;
     String mgetLocation, mgetjobClass, mgetEnteredDate, mgetRequestedDate, mgetTime, mShortDescription, mDescription, mNotes;
     public static  Map<String,String>  formValues = new HashMap<>();
     RadioGroup radioCustomer;
     RadioButton radioCustomerPo,radioJob;
     Integer radioButtonSelectedId;
     public FragmentComedy() {
        // Required empty public constructor
     }

    /**
     * Use this factory method to create a new instance of
     * this fragment.
     *
     * @return A new instance of fragment FragmentComedy.
     */
    public static FragmentComedy newInstance(String movieTitle) {
        FragmentComedy fragmentComedy = new FragmentComedy();
        Bundle args = new Bundle();
        args.putString(KEY_MOVIE_TITLE, movieTitle);
        fragmentComedy.setArguments(args);

        return fragmentComedy;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.formactivity, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
           initializeViews(view);
          // printDetails();

    }

    public void printDetails() {


        PrintManager pmanager = (PrintManager) getActivity().getSystemService(Context.PRINT_SERVICE);
        String jobName = "document";
        pmanager.print(jobName, new MyPrintAdapter(getActivity(),radioCustomer.getCheckedRadioButtonId()), null);
    }


    public void initializeViews(View view){
        mTxtEnteredDate = (EditText)view.findViewById(R.id.dateEntered_EditText);
        mTxtRequestedDate = (EditText)view.findViewById(R.id.dateRequest_EditText);
        mTxtGetTime = (EditText)view.findViewById(R.id.timerequest_EditText);
        mTxtShortDescription = (EditText)view.findViewById(R.id.shortdescription_EditText);
        mTxtDescription = (EditText)view.findViewById(R.id.description_EditText);
        mTxtNotes = (EditText)view.findViewById(R.id.notes_EditText);
        mgetLocationSpinner = (Spinner)view.findViewById(R.id.locationSpinner);
        mgetJobClassSpinner = (Spinner)view.findViewById(R.id.jobclassSpinner);
        mSignInBtn = (Button) view.findViewById(R.id.saveBtn);
        printBtn = (Button)view.findViewById(R.id.printBtn);

        /*billing information */
        customerName = (EditText)view.findViewById(R.id.customer_EditText);
        chooseCustomer = (EditText)view.findViewById(R.id.choosecustomer_EditText);
        customerAddress = (EditText)view.findViewById(R.id.customerAddress_EditText);
        customerPO =(EditText)view.findViewById(R.id.customerPO_EditText);
        customerJob = (EditText)view.findViewById(R.id.job_EditText);
        requestedBy = (EditText)view.findViewById(R.id.requested_EditText);
        radioCustomerPo = (RadioButton)view.findViewById(R.id.radioCustomerPo);
        radioJob = (RadioButton)view.findViewById(R.id.radioJob);
        radioCustomer=(RadioGroup)view.findViewById(R.id.radioCustomer);

        /*shipping and delivery */
        projectName = (EditText)view.findViewById(R.id.projectName_EditText);
        complete = (CheckBox)view.findViewById(R.id.checkBox1);
        noMaterialNeeded = (CheckBox)view.findViewById(R.id.checkBox2);
        closed=(CheckBox)view.findViewById(R.id.checkBox3);

        List<String> locationlist = new ArrayList<String>();
        locationlist.add("BKE Warehouse");
        locationlist.add("BKE Warehouse2");
        locationlist.add("BKE Warehouse3");
        locationlist.add("BKE Warehouse4");

        List<String> joblist = new ArrayList<String>();
        joblist.add("Warehouse");
        joblist.add("Warehouse2");
        joblist.add("Warehouse3");
        joblist.add("Warehouse4");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_spinner_item,locationlist);

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_spinner_item,joblist);

        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        dataAdapter2.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        mgetLocationSpinner.setAdapter(dataAdapter);
        mgetJobClassSpinner.setAdapter(dataAdapter2);




        mTxtEnteredDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                mTxtEnteredDate.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        mTxtRequestedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerRequestedDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                mTxtRequestedDate.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerRequestedDialog.show();
            }
        });

        mTxtGetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                mTxtGetTime.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        mSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mgetLocation = String.valueOf(mgetLocationSpinner.getSelectedItem());
                mgetjobClass = String.valueOf(mgetJobClassSpinner.getSelectedItem());
                mgetEnteredDate = mTxtEnteredDate.getText().toString();
                mgetRequestedDate = mTxtRequestedDate.getText().toString();
                mgetTime = mTxtGetTime.getText().toString();
                mShortDescription = mTxtShortDescription.getText().toString();
                mDescription = mTxtDescription.getText().toString();
                mNotes = mTxtNotes.getText().toString();
                // Toast.makeText(v.getContext(),mgetLocation+" "+mgetRequestedDate+""+mgetjobClass , Toast.LENGTH_LONG).show();

                formValues.put("Location",mgetLocation);
                formValues.put("jobclass",mgetLocation);
                formValues.put("entered_date",mgetEnteredDate);
                formValues.put("requested_date",mgetRequestedDate);
                formValues.put("time",mgetTime);
                formValues.put("shortdescription",mShortDescription);
                formValues.put("description",mDescription);
                formValues.put("notes",mNotes);

                formValues.put("customername",customerName.getText().toString());
                formValues.put("chooseCustomer",chooseCustomer.getText().toString());
                formValues.put("customerAddress",customerAddress.getText().toString());
                formValues.put("requestedBy",requestedBy.getText().toString());
                formValues.put("projectName",projectName.getText().toString());
                formValues.put("completecheckbox",complete.getText().toString());
                formValues.put("noMaterialNeededcheckbox",noMaterialNeeded.getText().toString());
                formValues.put("closedcheckbox",closed.getText().toString());

                radioButtonSelectedId = radioCustomer.getCheckedRadioButtonId();

                switch(radioButtonSelectedId) {
                    case R.id.radioCustomerPo:
                        formValues.put("customerPO",customerPO.getText().toString());
                        break;
                    case R.id.radioJob:
                        formValues.put("customerJob",customerJob.getText().toString());
                        break;
                }


                showCompletionDialog();

            }
        });

        printBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Under process", Toast.LENGTH_SHORT).show();
                printDetails();
            }
        });

    }

    public void showCompletionDialog(){
        final Dialog confirmationDialog = new Dialog(getActivity());
        confirmationDialog.setContentView(R.layout.dialoglayout);
        ((TextView)confirmationDialog.findViewById(R.id.dateentered_Textview)).setText(formValues.get("entered_date"));
        ((TextView)confirmationDialog.findViewById(R.id.daterequested_Textview)).setText(formValues.get("requested_date"));
        ((TextView)confirmationDialog.findViewById(R.id.timerequested_Textview)).setText(formValues.get("time"));
        ((TextView)confirmationDialog.findViewById(R.id.shortdescription_Textview)).setText(formValues.get("shortdescription"));
        ((TextView)confirmationDialog.findViewById(R.id.description_Textview)).setText(formValues.get("description"));
        ((TextView)confirmationDialog.findViewById(R.id.notes_Textview)).setText(formValues.get("notes"));
        ((TextView)confirmationDialog.findViewById(R.id.location_spinner_Text)).setText(formValues.get("Location"));
        ((TextView)confirmationDialog.findViewById(R.id.jobClass_spinner_Text)).setText(formValues.get("jobclass"));

        ((TextView)confirmationDialog.findViewById(R.id.customer_Textview)).setText(formValues.get("customername"));
        ((TextView)confirmationDialog.findViewById(R.id.customerAddress_Textview)).setText(formValues.get("customerAddress"));
        ((TextView)confirmationDialog.findViewById(R.id.customerPO_Textview)).setText(formValues.get("customerPO"));
        ((TextView)confirmationDialog.findViewById(R.id.customerName_Textview)).setText(formValues.get("chooseCustomer"));
        ((TextView)confirmationDialog.findViewById(R.id.requested_Textview)).setText(formValues.get("requestedBy"));

        ((TextView)confirmationDialog.findViewById(R.id.projectName_Textview)).setText(formValues.get("projectName"));
        // ((TextView)confirmationDialog.findViewById(R.id.checkBox1)).setText(formValues.get("completecheckbox"));
        //((TextView)confirmationDialog.findViewById(R.id.checkBox2)).setText(formValues.get("noMaterialNeededcheckbox"));
        //((TextView)confirmationDialog.findViewById(R.id.checkBox3)).setText(formValues.get("closedcheckbox"));
        ((Button)confirmationDialog.findViewById(R.id.dismiss)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmationDialog.dismiss();
            }
        });

        confirmationDialog.show();
    }
}


