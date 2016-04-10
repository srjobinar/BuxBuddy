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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_slide);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        Intent i=getIntent();
        uid=i.getIntExtra("uid", -1);
        gid=i.getIntExtra("gid", -1);

        v = (ViewPager) findViewById(R.id.pager);
        pa = new PagerAdapter(getSupportFragmentManager(),uid,gid);
        v.setAdapter(pa);
        actionbar = getSupportActionBar();
        actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        if(actionbar== null){
            Log.i("actionbar", "null");
        }
        actionbar.addTab(actionbar.newTab().setText("Transactions").setTabListener(this));
        actionbar.addTab(actionbar.newTab().setText("Users").setTabListener(this));
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}
