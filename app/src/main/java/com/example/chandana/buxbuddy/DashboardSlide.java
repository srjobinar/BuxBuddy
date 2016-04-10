package com.example.chandana.buxbuddy;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

public class DashboardSlide extends AppCompatActivity implements ActionBar.TabListener {

    ViewPager v;
    DashboardAdapter da;
    private ActionBar actionbar;
    int uid,gid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_slide);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        Intent i=getIntent();
        uid=i.getIntExtra("uid", -1);
        gid=i.getIntExtra("gid", -1);

        v = (ViewPager) findViewById(R.id.pager1);
        da = new DashboardAdapter(getSupportFragmentManager(),uid);
        v.setAdapter(da);
        actionbar = getSupportActionBar();
        actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        if(actionbar== null){
            Log.i("actionbar", "null");
        }
        actionbar.addTab(actionbar.newTab().setText("Groups").setTabListener(this));
        actionbar.addTab(actionbar.newTab().setText("Personal Funds").setTabListener(this));
        v.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                actionbar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        v.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}
