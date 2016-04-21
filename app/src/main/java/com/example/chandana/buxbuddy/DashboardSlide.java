package com.example.chandana.buxbuddy;

import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class DashboardSlide extends AppCompatActivity implements ActionBar.TabListener {

    ViewPager v;
    DashboardAdapter da;
    private ActionBar actionbar;
    int uid;
    EventsMenuDB db;
    List<Event> grplist=new ArrayList<Event>();
    Event e;
    private Boolean admin;
    public static final int NOTIFICATION_ID = 0;
    SharedPreferences sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_slide);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        sharedPref = getSharedPreferences("data",MODE_PRIVATE);
        Intent i=getIntent();
        uid=i.getIntExtra("uid", -1);

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

        db =  new EventsMenuDB(this);
        grplist=db.getRequestGroupsList();
        if(grplist.size()==0)
        {
            Log.i("ListEmpty","TrueHERE");

        }
        else{
        ListIterator<Event> iterate = grplist.listIterator();
        while (iterate.hasNext()) {
            e = iterate.next();
            admin=db.checkAdmin(uid,e.groupId);
            if(admin)
            {
                Log.i("Admin","true");
                userNotify(e.groupName);
            }
        }
        }

        // userNotify();
    }

    public void userNotify(String gname)
    {
        NotificationManager notificationManager = (NotificationManager) getSystemService(this
                .NOTIFICATION_SERVICE);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setVibrate(new long[] {1, 1, 1})
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentTitle("Rollback Request")
                .setAutoCancel(true)
                .setContentText("You have requests pending in "+gname);

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
       // notificationManager.cancel(NOTIFICATION_ID);

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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_group, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_logout:
                SharedPreferences.Editor prefEditor = sharedPref.edit();
                prefEditor.putInt("isLogged",0);
                prefEditor.commit();
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private Boolean exit = false;


    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
