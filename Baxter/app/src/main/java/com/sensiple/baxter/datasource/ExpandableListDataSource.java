package com.sensiple.baxter.datasource;

import android.content.Context;

import com.sensiple.baxter.R;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class ExpandableListDataSource {


    public static Map<String, List<String>> getData(Context context) {
        Map<String, List<String>> expandableListData = new LinkedHashMap<>();

        List<String> baxterMenus = Arrays.asList(context.getResources().getStringArray(R.array.baxterMenuList));

        List<String> homeMenus = Arrays.asList(context.getResources().getStringArray(R.array.Home_menus));
        List<String> adminMenus = Arrays.asList(context.getResources().getStringArray(R.array.Adminstrator_menus));
        List<String> directoryMenus = Arrays.asList(context.getResources().getStringArray(R.array.Directory_menus));
        List<String> bidsMenus = Arrays.asList(context.getResources().getStringArray(R.array.Bids_menus));
        List<String> manageMenus = Arrays.asList(context.getResources().getStringArray(R.array.Management_menus));
        List<String> fieldsMenus = Arrays.asList(context.getResources().getStringArray(R.array.Fields_menus));
        List<String> fabricatiomMenus = Arrays.asList(context.getResources().getStringArray(R.array.Fabrication_menus));
        List<String> configureMenus = Arrays.asList(context.getResources().getStringArray(R.array.configure_menus));
        List<String> warehousemenus = Arrays.asList(context.getResources().getStringArray(R.array.warehouse_menus));

        expandableListData.put(baxterMenus.get(0), homeMenus);
        expandableListData.put(baxterMenus.get(1), adminMenus);
        expandableListData.put(baxterMenus.get(2), directoryMenus);
        expandableListData.put(baxterMenus.get(3), bidsMenus);
        expandableListData.put(baxterMenus.get(4), manageMenus);
        expandableListData.put(baxterMenus.get(5),fieldsMenus);
        expandableListData.put(baxterMenus.get(6),fabricatiomMenus);
        expandableListData.put(baxterMenus.get(7),configureMenus);
        expandableListData.put(baxterMenus.get(8),warehousemenus);
        return expandableListData;
    }
}
