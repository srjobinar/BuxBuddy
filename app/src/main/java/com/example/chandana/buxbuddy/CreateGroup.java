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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class CreateGroup extends AppCompatActivity {

    EventsMenuDB db = new EventsMenuDB(this);
    EventListAdapter.OnSelectListener listener;
    List<Event> list=new ArrayList<Event>();
    List<Event> userlist=new ArrayList<Event>();
    EditText grpname;
    int uid,grpid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent i=getIntent();
        uid=i.getIntExtra("uid", -1);
        userlist = db.getUsersList(uid);
        final JSONArray  list_json = new JSONArray();


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
                try {
                    JSONObject o1 = new JSONObject();
                    o1.put("name",grpname.getText().toString());
                    o1.put("type", "createGroup");
                    grpid = (int) db.creategroup(grpname.getText().toString());
                    Log.i("check", grpid + "");
                    ListIterator<Event> iterator = list.listIterator();
                    Event e;
                    while (iterator.hasNext()) {
                        e = iterator.next();
                        JSONObject ob = new JSONObject();
                        ob.put("userid", e.userId);
                        ob.put("groupid",grpid);
                        ob.put("admin", 0);
                        list_json.put(ob);

                    }

                    JSONObject ob = new JSONObject();
                    ob.put("userid",uid);
                    ob.put("groupid",grpid);
                    ob.put("admin", 1);
                    list_json.put(ob);


                    JSONObject obj = new JSONObject();
                    obj.put("type","createGroup");
                    obj.put("users", list_json.toString());
                    obj.put("name", grpname.getText().toString());
                    ScriptRunner run = new ScriptRunner(obj, new ScriptRunner.ScriptFinishListener() {
                        @Override
                        public void finish(String result, int resultCode) {
                            if(resultCode==ScriptRunner.SUCCESS){
                                Log.d("tag::",result);
                                try {
                                    JSONObject obj = new JSONObject(result);
                                    grpid = (int) obj.get("groupid");
                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                }
//                                    Intent i = new Intent(getApplicationContext(), DashboardSlide.class);
//                                    i.putExtra("uid",userid);
//                                    startActivity(i);
//                                    setContentView(R.layout.activity_dashboard);
                            }
                        }
                    });
                    run.execute();

                    Intent i = new Intent(getApplicationContext(), Group_slide.class);
                    i.putExtra("gid", grpid);
                    i.putExtra("uid", uid);
                    startActivity(i);
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        });


    }

}
