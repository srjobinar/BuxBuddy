package com.example.chandana.buxbuddy;

import android.os.Bundle;
import com.example.chandana.buxbuddy.Group;
import com.example.chandana.buxbuddy.GroupUser;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Jobin A R on 4/8/2016.
 */
public class PagerAdapter extends FragmentPagerAdapter {
  int uid,gid;

    public PagerAdapter(FragmentManager fm, int uid,int gid) {


        super(fm);
        this.uid = uid;
        this.gid = gid;
    }



    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                Group g = new Group();
                g.setData(this.uid,this.gid);
                return g;
            case 1:
                GroupUser gu = new GroupUser();
                gu.setData(this.uid,this.gid);
                return gu;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
