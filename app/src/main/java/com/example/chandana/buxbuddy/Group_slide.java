package com.example.chandana.buxbuddy;


import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ListIterator;

public class Group_slide extends AppCompatActivity implements ActionBar.TabListener,AlertDialog.OnClickListener{

    ViewPager v;
    PagerAdapter pa;
    private ActionBar actionbar;
    int uid,gid;
    SharedPreferences sharedPref;
    int can_leave;
    EventsMenuDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_slide);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        db = new EventsMenuDB(this);

        sharedPref = getSharedPreferences("data", MODE_PRIVATE);

        Intent i=getIntent();
        uid=i.getIntExtra("uid", -1);
        gid=i.getIntExtra("gid", -1);
        can_leave = db.can_leave(gid, uid);
        invalidateOptionsMenu();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.group, menu);
        MenuItem leavegroup = menu.findItem(R.id.action_leavegroup);
        if (can_leave<0) {
            leavegroup.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_leavegroup:
                // User chose the "Settings" item, show the app settings UI...
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                String message;
                if(can_leave>0){
                    message = "Your remaining fund of Rs."+can_leave+" will be donated equally. Are you sure?";
                }
                else {
                    message = "Are You Sure?";
                }
                builder.setMessage(message).setPositiveButton("Accept",dialogClickListener)
                        .setNegativeButton("Reject", dialogClickListener).show();
                return true;

            case R.id.action_logout:
                SharedPreferences.Editor prefEditor = sharedPref.edit();
                prefEditor.putInt("isLogged",0);
                prefEditor.commit();
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
                return true;
            case R.id.action_requestPayment:
                Intent j = new Intent(this, payment.class);
                j.putExtra("gid", gid);
                j.putExtra("uid", uid);
                startActivity(j);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }
    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    db.divide_fund(uid,gid,can_leave);
                    db.removeUser(uid,gid);
                    Intent i = new Intent(getApplicationContext(), DashboardSlide.class);
                    i.putExtra("uid", uid);
                    startActivity(i);
                    break;

                case DialogInterface.BUTTON_NEGATIVE:

                    break;
            }
        }
    };
}
