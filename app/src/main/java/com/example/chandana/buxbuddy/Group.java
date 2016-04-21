package com.example.chandana.buxbuddy;

//import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Group extends Fragment implements AdapterView.OnItemClickListener,AlertDialog.OnClickListener {
    private int gid,uid,tid;
    private Boolean admin;

    public Group(){

    }
    public void setData(int uid,int gid){
        this.uid = uid;
        this.gid = gid;
    }
    ListView t;
    ArrayList<String> trans=new ArrayList<String>();
    EventsMenuDB db;
    List<Event> list=new ArrayList<Event>();
    List<Event> list1=new ArrayList<Event>();
    ArrayList<Integer> transids=new ArrayList<Integer>();
    Event e;

    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        t = (ListView) getActivity().findViewById(R.id.listView);
        db =  new EventsMenuDB(getActivity());
        admin = db.checkAdmin(uid,gid);
        list = db.getTransactionsList(gid);

        ListIterator<Event> iterator = list.listIterator();
        while(iterator.hasNext()){
            e = iterator.next();
            trans.add(e.transactionName);
            transids.add(e.transactionId);
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,trans);
        t.setAdapter(adapter);
        t.setOnItemClickListener(this);
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent i = new Intent(getContext(), add_transaction.class);
                i.putExtra("gid",gid);
                i.putExtra("uid",uid);
                startActivity(i);
            }
        });

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        Toast.makeText(getActivity(),"Yes Button Clicked",Toast.LENGTH_SHORT).show();
                        db.updateStatus(tid, 1);
                        list1=db.getUserTransaction(tid);
                        db.deleteTransaction(tid);
                        ListIterator<Event> iterator = list1.listIterator();
                        while(iterator.hasNext()){
                            e= iterator.next();
                            db.updatefund(e.userId,gid,-1*e.amount);
                        }
                        Intent i = new Intent(getContext(), Group_slide.class);
                        i.putExtra("gid",gid);
                        i.putExtra("uid", uid);
                        startActivity(i);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        db.updateStatus(tid,-1);
                        Toast.makeText(getActivity(),"No Button Clicked",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        DialogInterface.OnClickListener dialogClickListenerForPayment = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        Toast.makeText(getActivity(),"Yes Button Clicked",Toast.LENGTH_SHORT).show();
                        db.updateStatusForPayment(uid,e.userId,gid,1);
                        db.updatefund(uid,gid,-1*e.amount);
                        db.updatefund(e.userId,gid,e.amount);
                        Intent i = new Intent(getContext(), Group_slide.class);
                        i.putExtra("gid",gid);
                        i.putExtra("uid", uid);
                        startActivity(i);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        db.updateStatusForPayment(uid,e.userId,gid,-1);
                        Toast.makeText(getActivity(),"No Button Clicked",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        if(admin) {
            list = db.getRequestList(gid);
            if(list.size()==0){
                Log.i("ListEmpty","True");
            }
            ListIterator<Event> iterate = list.listIterator();
            while (iterate.hasNext()) {
                e = iterate.next();
                tid=e.transactionId;
                if(e.status==0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage(e.message).setPositiveButton("Accept", dialogClickListener)
                            .setNegativeButton("Reject", dialogClickListener).show();
                }
            }
        }

        list = db.getPaymentsList(uid, gid);
        ListIterator<Event> iterate = list.listIterator();
        while (iterate.hasNext()) {
            e = iterate.next();
            if(e.amount>=0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage(e.userName+" requests approval for payment of Rs."+e.amount).setPositiveButton("Accept", dialogClickListenerForPayment)
                        .setNegativeButton("Reject", dialogClickListenerForPayment).show();
            }
        }
   }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){

        return inflater.inflate(R.layout.activity_group,container,false);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(getContext(), transaction.class);
        i.putExtra("gid",gid);
        i.putExtra("uid",uid);
        i.putExtra("tid",transids.get(position));
        startActivity(i);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }
    // @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_group);
////        Intent i=getIntent();
////        gid=i.getIntExtra("gid", -1);
////        uid=i.getIntExtra("uid",-1);
//
//        t = (ListView) findViewById(R.id.listView);
//
//        list = db.getTransactionsList(gid);
//        ListIterator<Event> iterator = list.listIterator();
//        while(iterator.hasNext()){
//            e = iterator.next();
//            trans.add(e.transactionName);
//            transids.add(e.transactionId);
//        }
//        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,trans);
//        t.setAdapter(adapter);
//        t.setOnItemClickListener(this);
//
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        Toast.makeText(this, "Group number "+gid,Toast.LENGTH_SHORT).show();
//
//       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();*/
//                Intent i = new Intent(getApplicationContext(), add_transaction.class);
//                i.putExtra("gid",gid);
//                i.putExtra("uid",uid);
//                startActivity(i);
//            }
//        });
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getActivity().getMenuInflater().inflate(R.menu.menu_group, menu);
//        return true;
//    }

  /*

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), "You chose "+ position,Toast.LENGTH_SHORT).show();
//        Intent i = new Intent(getContext(), transaction.class);
//        i.putExtra("gid",gid);
//        i.putExtra("uid",uid);
//        i.putExtra("tid",transids.get(position));
//        startActivity(i);

    }


    public void onBackPressed() {
        super.getActivity().onBackPressed();
//        Intent i = new Intent(getContext(), Dashboard.class);
//        i.putExtra("uid",uid);
//        startActivity(i);
    }*/
}
