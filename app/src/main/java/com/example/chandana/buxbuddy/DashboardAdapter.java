package com.example.chandana.buxbuddy;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by CHANDANA on 10-04-2016.
 */
public class DashboardAdapter extends FragmentPagerAdapter {
    int uid;

    public DashboardAdapter(FragmentManager fm, int uid) {


        super(fm);
        this.uid = uid;
    }



    @Override
    public Fragment getItem(int position) {

                Dashboard g = new Dashboard();
                g.setData(this.uid);
                return g;

    }

    @Override
    public int getCount() {
        return 2;
    }
}
