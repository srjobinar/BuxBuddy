package com.example.chandana.buxbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Dashboard extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView g;
    ArrayList<String> groups=new ArrayList<String>();
    ArrayList<Integer> groupids=new ArrayList<Integer>();
    EventsMenuDB db = new EventsMenuDB(this);
    List<Event> list=new ArrayList<Event>();
    Event e;
    int uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Intent i=getIntent();
        uid=i.getIntExtra("uid", -1);

        list = db.getGroupsList(uid);
        ListIterator<Event> iterator = list.listIterator();
        while(iterator.hasNext()){
            e = iterator.next();
            groups.add(e.groupName);
            groupids.add(e.groupId);
        }


        g = (ListView) findViewById(R.id.listView2);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,groups);
        g.setAdapter(adapter);
        g.setOnItemClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CreateGroup.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(getApplicationContext(), Group.class);
        i.putExtra("gid",groupids.get(position));
        i.putExtra("uid",uid);
        Log.i("check",groupids.get(position)+"");
        startActivity(i);
        //Toast.makeText(this, position,Toast.LENGTH_SHORT).show();
    }
}
