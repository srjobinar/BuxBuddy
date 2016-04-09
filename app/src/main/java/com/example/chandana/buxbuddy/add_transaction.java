package com.example.chandana.buxbuddy;

import android.content.Intent;
import android.net.Uri;
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

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class add_transaction extends AppCompatActivity {

    EventsMenuDB db = new EventsMenuDB(this);
    EventListAdapter.OnSelectListener listener;
    List<Event> list = new ArrayList<Event>();
    List<Event> userlist = new ArrayList<Event>();
    EditText amount,trans_name;
    int gid,uid,amnt_per_user;
    Event e;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    //private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        gid = i.getIntExtra("gid", -1);
        uid = i.getIntExtra("uid",-1);
        Log.i("check",gid+"");
        userlist = db.getMembersList(gid,uid);
        ListView lv = (ListView) findViewById(R.id.listView3);
        listener = new EventListAdapter.OnSelectListener() {
            @Override
            public void onSelect(int position, boolean checked) {
                if (checked)
                    list.add(userlist.get(position));
                else
                    list.remove(userlist.get(position));
            }
        };
        EventListAdapter exhibitionAdapter = new EventListAdapter(this, R.layout.events_list_item,userlist, listener);
        lv.setAdapter(exhibitionAdapter);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        Button btn = (Button) findViewById(R.id.button2);

        btn.setOnClickListener(new View.OnClickListener()

                               {
                                   @Override
                                   public void onClick (View v){
                                       amount = (EditText) findViewById(R.id.editText2);
                                       trans_name = (EditText) findViewById(R.id.editText3);
                                       amnt_per_user = (Integer.parseInt(amount.getText().toString())/(list.size()+1));
                                       int transid;
                                       transid = (int) db.createtransaction(Integer.parseInt(amount.getText().toString())
                                                            ,trans_name.getText().toString(),gid);
                                       Log.i("check", transid + "");
                                       Log.i("amount",amount.getText().toString());
                                       Log.i("amount_per_user",amnt_per_user+"");
                                       Log.i("trans",trans_name.getText().toString());
                                       ListIterator<Event> iterator = list.listIterator();
                                       while (iterator.hasNext()) {
                                           e =iterator.next();
                                           db.createusertransaction(e.userId, transid, -1 * amnt_per_user);
                                           db.updatefund(e.userId,gid, -1 * amnt_per_user);
                                       }
                                       db.createusertransaction(uid, transid, list.size()*amnt_per_user);
                                       db.updatefund(uid,gid, list.size()*amnt_per_user);

                                       Intent i = new Intent(getApplicationContext(), Group_slide.class);
                                       i.putExtra("gid",gid);
                                       i.putExtra("uid",uid);
                                       startActivity(i);
                                   }
                               }

        );
    }




   /* @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "add_transaction Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.chandana.buxbuddy/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "add_transaction Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.chandana.buxbuddy/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
*/


}
