package com.example.chandana.buxbuddy;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    ArrayList<Integer> userids=new ArrayList<Integer>();
    ArrayList<Integer> funds=new ArrayList<Integer>();
    int myfund;
    Event e;
    MenuItem admin,pay;
    Boolean isadmin;

    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        t = (ListView) getActivity().findViewById(R.id.listView5);
        registerForContextMenu(t);
        TextView tv = (TextView) getActivity().findViewById(R.id.textView2);
        db =  new EventsMenuDB(getActivity());
        list = db.getFundsList(gid);
        isadmin = db.checkAdmin(uid, gid);

        ListIterator<Event> iterator = list.listIterator();
        while(iterator.hasNext()){
            e= iterator.next();
            if(e.userId!=uid) {
                members.add(e.userName + " : " + e.fund);
                userids.add(e.userId);
                funds.add(e.fund);
            }
            else
            {
                myfund=e.fund;
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
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = new MenuInflater(getActivity());
        inflater.inflate(R.menu.context_menu, menu);
        pay = menu.findItem(R.id.action_requestPayment);
        admin = menu.findItem(R.id.action_makeAdmin);
        if(!isadmin){
            admin.setVisible(false);
        }
        if(myfund>=0){
            pay.setVisible(false);
        }
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.action_makeAdmin:
                db.makeadmin(gid,userids.get(info.position));
                return true;
            case R.id.action_requestPayment:
                if(funds.get(info.position)<0){
                    Toast.makeText(getActivity(),"Can Request Payment To Users With Positive Funds Only",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent i = new Intent(getActivity(), payment.class);
                    i.putExtra("gid", gid);
                    i.putExtra("uid", uid);
                    i.putExtra("to", userids.get(info.position));
                    startActivity(i);
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

}
