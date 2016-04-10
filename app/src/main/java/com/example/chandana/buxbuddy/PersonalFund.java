package com.example.chandana.buxbuddy;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class PersonalFund extends Fragment{
    private int uid;

    public PersonalFund(){

    }
    public void setData(int uid){
        this.uid = uid;
    }

    ListView g;
    ArrayList<String> values=new ArrayList<String>();
    EventsMenuDB db;
    List<Event> list=new ArrayList<Event>();
    Event e;

    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);


        db = new EventsMenuDB(getActivity());
        list = db.getPersonalFundsList(uid);
        ListIterator<Event> iterator = list.listIterator();
        while(iterator.hasNext()){
            e = iterator.next();
            values.add(e.groupName+" : "+e.fund);
            Log.i(e.groupName,e.fund+"");
        }


        g = (ListView) getActivity().findViewById(R.id.listView6);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,values);
        g.setAdapter(adapter);

//        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){

        return inflater.inflate(R.layout.activity_personal_fund,container,false);
    }
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_personal_funds);
//    }
}
