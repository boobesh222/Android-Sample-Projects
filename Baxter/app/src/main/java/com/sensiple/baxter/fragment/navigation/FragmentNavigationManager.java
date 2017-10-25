package com.sensiple.baxter.fragment.navigation;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.sensiple.baxter.BuildConfig;
import com.sensiple.baxter.MainActivity;
import com.sensiple.baxter.R;
import com.sensiple.baxter.fragment.FragmentHome;
import com.sensiple.baxter.fragment.FragmentComedy;
import com.sensiple.baxter.fragment.FragmentDrama;
import com.sensiple.baxter.fragment.FragmentMusical;
import com.sensiple.baxter.fragment.FragmentThriller;


public class FragmentNavigationManager implements NavigationManager {

    private static FragmentNavigationManager sInstance;

    private FragmentManager mFragmentManager;
    private MainActivity mActivity;

    public static FragmentNavigationManager obtain(MainActivity activity) {
        if (sInstance == null) {
            sInstance = new FragmentNavigationManager();
        }
        sInstance.configure(activity);
        return sInstance;
    }

    private void configure(MainActivity activity) {
        mActivity = activity;
        mFragmentManager = mActivity.getSupportFragmentManager();
    }

    @Override
    public void showFragmentHome(String title) {
        showFragment(FragmentHome.newInstance(title), false);
    }

    @Override
    public void showFragmentAdmin(String title) {
        showFragment(FragmentHome.newInstance(title), false);
    }

    @Override
    public void showFragmentDirectory(String title) {
        showFragment(FragmentHome.newInstance(title), false);
    }

    @Override
    public void showFragmentBids(String title) {
        showFragment(FragmentHome.newInstance(title), false);
    }

    @Override
    public void showFragmentManagement(String title) {
        showFragment(FragmentHome.newInstance(title), false);
    }


    @Override
    public void showFragmentFields(String title) {
        showFragment(FragmentHome.newInstance(title), false);
    }

    @Override
    public void showFragmentFabrication(String title) {
        showFragment(FragmentComedy.newInstance(title), false);
    }

    @Override
    public void showFragmentConfigurations(String title) {
        showFragment(FragmentHome.newInstance(title), false);
    }

    @Override
    public void showFragmentWareHouse(String title) {
        showFragment(FragmentHome.newInstance(title), false);
    }

    private void showFragment(Fragment fragment, boolean allowStateLoss) {
        FragmentManager fm = mFragmentManager;


        FragmentTransaction ft = fm.beginTransaction()
            .replace(R.id.container, fragment);

        ft.addToBackStack(null);

        if (allowStateLoss || !BuildConfig.DEBUG) {
            ft.commitAllowingStateLoss();
        } else {
            ft.commit();
        }

        fm.executePendingTransactions();
    }
}
