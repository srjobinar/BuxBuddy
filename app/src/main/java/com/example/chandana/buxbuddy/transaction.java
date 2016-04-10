package com.example.chandana.buxbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class transaction extends AppCompatActivity{


    int gid,uid,tid;
    Boolean admin;
    ListView t;
    ArrayList<String> transMembers=new ArrayList<String>();
    EventsMenuDB db = new EventsMenuDB(this);
    List<Event> list=new ArrayList<Event>();
    List<Event> list1=new ArrayList<Event>();
    Event e;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i=getIntent();
        gid=i.getIntExtra("gid", -1);
        uid=i.getIntExtra("uid",-1);
        tid=i.getIntExtra("tid",-1);

        t = (ListView) findViewById(R.id.listView4);
        TextView tv = (TextView) findViewById(R.id.textView);

        list = db.getTransactionMembersList(tid);
        admin = db.checkAdmin(uid,gid);
        ListIterator<Event> iterator = list.listIterator();
        while(iterator.hasNext()){
            e= iterator.next();
            if(e.userId!=uid) {
                transMembers.add(e.userName + " : " + e.amount);
            }
            else
            {
                tv.setText("Your Amount : "+e.amount);
            }
        }

        Button deleteButton = (Button) findViewById(R.id.button3);

        deleteButton.setOnClickListener(new Button.OnClickListener(){
              public void onClick(View v){
                  db.deleteTransaction(tid);
                  list1=db.getUserTransaction(tid);
                  db.deleteTransaction(tid);
                  ListIterator<Event> iterator = list1.listIterator();
                  while(iterator.hasNext()){
                      e= iterator.next();
                      db.updatefund(e.userId,gid,-1*e.amount);
                  }
                  Intent i = new Intent(getApplicationContext(), Group_slide.class);
                  i.putExtra("gid",gid);
                  i.putExtra("uid", uid);
                  startActivity(i);
              }
        });
        if(admin){
            deleteButton.setVisibility(View.VISIBLE);
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,transMembers);
        t.setAdapter(adapter);

    }
}
