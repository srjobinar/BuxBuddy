package com.example.chandana.buxbuddy;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by CHANDANA on 10-04-2016.
 */
public class DashboardAdapter extends FragmentPagerAdapter {
    private int uid;

    public DashboardAdapter(FragmentManager fm, int uid) {


        super(fm);
        this.uid = uid;
    }



    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
            Dashboard g = new Dashboard();
            g.setData(this.uid);
            return g;
            case 1:
                PersonalFund pf = new PersonalFund();
                pf.setData(this.uid);
                return pf;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 2;
    }
}
