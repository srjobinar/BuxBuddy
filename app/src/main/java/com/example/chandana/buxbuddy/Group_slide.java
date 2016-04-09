package com.example.chandana.buxbuddy;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

public class Group_slide extends AppCompatActivity implements ActionBar.TabListener{

    ViewPager v;
    PagerAdapter pa;
    private ActionBar actionbar;
    int uid,gid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_slide);
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
    public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
        v.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }
}
