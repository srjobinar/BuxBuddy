package com.example.chandana.buxbuddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by CHANDANA on 21-04-2016.
 */
public class paymentlistAdapter extends ArrayAdapter<Event> {



    private Context context;
    private List<Event> eventsData;
    LayoutInflater inflator;
    OnSelectListener listener;


    public paymentlistAdapter(Context context, int resource,List<Event> eventsData,OnSelectListener listener) {
        super(context, resource, eventsData);
        this.context = context;
        this.eventsData = eventsData;
        this.listener = listener;
        inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(convertView == null){
            v = inflator.inflate(R.layout.payment_list_item, null);
        }



        TextView tv = (TextView) v.findViewById(R.id.tvListItem1);
        tv.setText(getItem(position).userName);

        RadioButton rb = (RadioButton) v.findViewById(R.id.radioButton);
        rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                listener.onSelect(position,isChecked);
            }
        });



        return v;
    }
    public interface OnSelectListener{
        public void onSelect(int position,boolean checked);
    }
}
