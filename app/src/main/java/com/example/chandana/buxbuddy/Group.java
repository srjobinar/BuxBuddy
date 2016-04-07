package com.example.chandana.buxbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Group extends AppCompatActivity implements AdapterView.OnItemClickListener {

    int gid,uid;
    ListView t;
    ArrayList<String> trans=new ArrayList<String>();
    EventsMenuDB db = new EventsMenuDB(this);
    List<Event> list=new ArrayList<Event>();
    ArrayList<Integer> transids=new ArrayList<Integer>();
    Event e;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        Intent i=getIntent();
        gid=i.getIntExtra("gid", -1);
        uid=i.getIntExtra("uid",-1);

        t = (ListView) findViewById(R.id.listView);

        list = db.getTransactionsList(gid);
        ListIterator<Event> iterator = list.listIterator();
        while(iterator.hasNext()){
            e = iterator.next();
            trans.add(e.transactionName);
            transids.add(e.transactionId);
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,trans);
        t.setAdapter(adapter);
        t.setOnItemClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Toast.makeText(this, "Group number "+gid,Toast.LENGTH_SHORT).show();

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent i = new Intent(getApplicationContext(), add_transaction.class);
                i.putExtra("gid",gid);
                i.putExtra("uid",uid);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_group, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "You chose "+ position,Toast.LENGTH_SHORT).show();
        Intent i = new Intent(getApplicationContext(), transaction.class);
        i.putExtra("gid",gid);
        i.putExtra("uid",uid);
        i.putExtra("tid",transids.get(position));
        startActivity(i);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), Dashboard.class);
        i.putExtra("uid",uid);
        startActivity(i);
    }
}
