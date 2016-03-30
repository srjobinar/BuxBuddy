package com.example.chandana.buxbuddy;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

public class add_transaction extends AppCompatActivity {

    EventsMenuDB db=new EventsMenuDB(this);
    EventListAdapter.OnSelectListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ListView lv = (ListView) findViewById(R.id.listView3);
        listener = new EventListAdapter.OnSelectListener() {
            @Override
            public void onSelect(int position, boolean checked) {
                Log.i("Check", position + " ");
            }
        };
        EventListAdapter exhibitionAdapter = new EventListAdapter(this, R.layout.events_list_item,db.getGroupsList(),listener);
        lv.setAdapter(exhibitionAdapter);
    }

}
