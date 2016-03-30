package com.example.chandana.buxbuddy;

import java.util.List;


import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;


public class EventListAdapter extends ArrayAdapter<Event> {
	
	
	
	private Context context;
	private List<Event> eventsData;
	LayoutInflater inflator;
	OnSelectListener listener;

	
	public EventListAdapter(Context context, int resource,List<Event> eventsData,OnSelectListener listener) {
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
			 v = inflator.inflate(R.layout.events_list_item, null);
		}



		TextView tv = (TextView) v.findViewById(R.id.tvListItem);
		tv.setText(getItem(position).userName);

		CheckBox cb = (CheckBox) v.findViewById(R.id.checkBox);
		cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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



//public class EventListAdapter extends ArrayAdapter<String> {
//	
//	
//	
//	private Context context;
//	private List<String> eventsList;
//	LayoutInflater inflator;
//
//	
//	public EventListAdapter(Context context, int resource,List<String> eventsData) {
//		super(context, resource, eventsData);
//		this.context = context;
//		this.eventsList = eventsData;
//		
//		inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		
//	}
//	
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		View v = convertView;
//		if(convertView == null){
//			 v = inflator.inflate(R.layout.event_list_item, null);
//		}
//		
//		TextView tv = (TextView) v.findViewById(R.id.listItem);
//		tv.setText(getItem(position));
//		
//		return v;
//	}
//	
//
//}

