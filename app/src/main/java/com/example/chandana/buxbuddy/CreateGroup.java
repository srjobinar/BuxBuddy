package com.example.chandana.buxbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class CreateGroup extends AppCompatActivity {

    EventsMenuDB db = new EventsMenuDB(this);
    EventListAdapter.OnSelectListener listener;
    List<Event> list=new ArrayList<Event>();
    List<Event> userlist=new ArrayList<Event>();
    EditText grpname;
    int uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent i=getIntent();
        uid=i.getIntExtra("uid",-1);
        userlist = db.getUsersList(uid);


        ListView lv = (ListView) findViewById(R.id.listView2);

        listener = new EventListAdapter.OnSelectListener() {
            @Override
            public void onSelect(int position, boolean checked) {
                Log.i("Check",position+" ");
                if(checked)
                    list.add(userlist.get(position));
                else
                    list.remove(userlist.get(position));
            }
        };
        EventListAdapter exhibitionAdapter = new EventListAdapter(this, R.layout.events_list_item,userlist,listener);
        lv.setAdapter(exhibitionAdapter);


        Button btn = (Button) findViewById(R.id.button);
        grpname =   (EditText) findViewById(R.id.editText);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              int grpid;
               grpid = (int) db.creategroup(grpname.getText().toString());
               Log.i("check",grpid+"");
                ListIterator<Event> iterator = list.listIterator();
                while(iterator.hasNext()){
                    db.createusergroup(iterator.next().userId,grpid,0);
                }
                    db.createusergroup(iterator.next().userId,grpid,0);

                Intent i = new Intent(getApplicationContext(), Group.class);
                i.putExtra("gid",grpid);
                i.putExtra("uid",uid);
                startActivity(i);
            }
        });


    }

}
