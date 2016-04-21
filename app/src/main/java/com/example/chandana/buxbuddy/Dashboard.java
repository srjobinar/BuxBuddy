package com.example.chandana.buxbuddy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Dashboard extends Fragment implements AdapterView.OnItemClickListener {
    int uid;

    public Dashboard(){

    }
    public void setData(int uid){
        this.uid = uid;
    }

    ListView g;
    ArrayList<String> groups=new ArrayList<String>();
    ArrayList<Integer> groupids=new ArrayList<Integer>();
    EventsMenuDB db;
    List<Event> list=new ArrayList<Event>();
    Event e;

    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);


        db = new EventsMenuDB(getActivity());
        list = db.getGroupsList(uid);
        ListIterator<Event> iterator = list.listIterator();
        while(iterator.hasNext()){
            e = iterator.next();
            groups.add(e.groupName);
            groupids.add(e.groupId);
        }


        g = (ListView) getActivity().findViewById(R.id.listView2);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,groups);
        g.setAdapter(adapter);
        g.setOnItemClickListener(this);

//        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), CreateGroup.class);
                i.putExtra("uid",uid);
                startActivity(i);
            }
        });




    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){

        return inflater.inflate(R.layout.activity_dashboard,container,false);
    }
    //@Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_dashboard);
//
//        Intent i=getIntent();
//        uid=i.getIntExtra("uid", -1);
//
//        list = db.getGroupsList(uid);
//    ListIterator<Event> iterator = list.listIterator();
//    while(iterator.hasNext()){
//        e = iterator.next();
//        groups.add(e.groupName);
//        groupids.add(e.groupId);
//    }
//
//
//    g = (ListView) findViewById(R.id.listView2);
//    ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,groups);
//    g.setAdapter(adapter);
//    g.setOnItemClickListener(this);
//
//    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//    setSupportActionBar(toolbar);
//
//    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//    fab.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            Intent i = new Intent(getApplicationContext(), CreateGroup.class);
//            i.putExtra("uid",uid);
//            startActivity(i);
//        }
//    });
//}

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(getContext(), Group_slide.class);
        i.putExtra("gid",groupids.get(position));
        i.putExtra("uid", uid);
        Log.i("check", groupids.get(position)+"");
        startActivity(i);
        //Toast.makeText(this, position,Toast.LENGTH_SHORT).show();
    }

}
