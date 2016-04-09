package com.example.chandana.buxbuddy;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class GroupUser extends Fragment {
    private int gid,uid;
    public GroupUser(){

    }
    public void setData(int uid,int gid){
        this.uid = uid;
        this.gid = gid;
    }
    ListView t;
    ArrayList<String> members=new ArrayList<String>();
    EventsMenuDB db;
    List<Event> list=new ArrayList<Event>();
    ArrayList<Integer> transid=new ArrayList<Integer>();
    Event e;

    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        t = (ListView) getActivity().findViewById(R.id.listView5);
        TextView tv = (TextView) getActivity().findViewById(R.id.textView2);
        db =  new EventsMenuDB(getActivity());
        list = db.getFundsList(gid);

        ListIterator<Event> iterator = list.listIterator();
        while(iterator.hasNext()){
            e= iterator.next();
            if(e.userId!=uid) {
                members.add(e.userName + " : " + e.fund);
            }
            else
            {
                tv.setText("Your Amount : "+e.fund);
            }
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,members);
        t.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
        return inflater.inflate(R.layout.activity_group_user,container,false);
    }

}
