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

public class payment extends AppCompatActivity {

    EventsMenuDB db = new EventsMenuDB(this);
    paymentlistAdapter.OnSelectListener listener;
    Event user;
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
        setContentView(R.layout.activity_payment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        gid = i.getIntExtra("gid", -1);
        uid = i.getIntExtra("uid",-1);
        Log.i("check",gid+"");
        userlist = db.getPositiveMembersList(gid,uid);
        ListView lv = (ListView) findViewById(R.id.listView8);
        listener = new paymentlistAdapter.OnSelectListener() {
            @Override
            public void onSelect(int position, boolean checked) {
                if (checked)
                    user = userlist.get(position);
            }
        };
        paymentlistAdapter exhibitionAdapter = new paymentlistAdapter(this, R.layout.payment_list_item,userlist, listener);
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
                                       db.requestPayment(gid, uid, user, Integer.parseInt(amount.getText().toString()));

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
