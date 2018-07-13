package com.pratiksymz.android.prospectscalendarnew.calendar;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pratiksymz.android.prospectscalendarnew.R;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GridAdapter extends ArrayAdapter {
    private static final String TAG = GridAdapter.class.getSimpleName();
    private LayoutInflater mInflater;
    private List<Date> monthlyDates;
    private Calendar currentDate;
    private List<CellEvent> allEvents;
    private int height;

    public GridAdapter(Context context, List<Date> monthlyDates, Calendar currentDate, List<CellEvent> allEvents, int height) {
        super(context, R.layout.single_cell_layout);
        this.monthlyDates = monthlyDates;
        this.currentDate = currentDate;
        this.allEvents = allEvents;
        mInflater = LayoutInflater.from(context);
        this.height=height;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Date mDate = monthlyDates.get(position);
        Calendar dateCal = Calendar.getInstance();
        dateCal.setTime(mDate);
        int dayValue = dateCal.get(Calendar.DAY_OF_MONTH);
        int displayMonth = dateCal.get(Calendar.MONTH) + 1;
        int displayYear = dateCal.get(Calendar.YEAR);
        int currentMonth = currentDate.get(Calendar.MONTH) + 1;
        int currentYear = currentDate.get(Calendar.YEAR);
        View view = convertView;
        if(view == null){
            view = mInflater.inflate(R.layout.single_cell_layout, parent, false);
        }
        int remainder=height%10;
        view.setMinimumHeight((height-remainder)/6);
        if(displayMonth == currentMonth && displayYear == currentYear){
//            view.setBackgroundColor(Color.parseColor("#FF5733"));
        }else{
//            view.setBackgroundColor(Color.parseColor("#cccccc"));
        }
        //Add day to calendar
        TextView cellNumber = (TextView)view.findViewById(R.id.calendar_date_id);

        cellNumber.setText(String.valueOf(dayValue));
        //Add events to the calendar
        TextView eventIndicator = (TextView)view.findViewById(R.id.event_id);
        LinearLayout cv=view.findViewById(R.id.event_wrapper);
//        cv.setVisibility(View.GONE);
        cv.setBackgroundColor(Color.parseColor("#ffffff"));
        Calendar eventCalendar = Calendar.getInstance();
        for(int i = 0; i < allEvents.size(); i++){
            eventCalendar.setTime(allEvents.get(i).getDate());
            if(dayValue == eventCalendar.get(Calendar.DAY_OF_MONTH) && displayMonth == eventCalendar.get(Calendar.MONTH) + 1
                    && displayYear == eventCalendar.get(Calendar.YEAR)){
                cv.setBackgroundColor(Color.parseColor("#ed5050"));
                eventIndicator.setText("₹20000");
//                cv.setVisibility(View.VISIBLE);
            }
        }
        return view;
    }
    @Override
    public int getCount() {
        return monthlyDates.size();
    }
    @Nullable
    @Override
    public Object getItem(int position) {
        return monthlyDates.get(position);
    }
    @Override
    public int getPosition(Object item) {
        return monthlyDates.indexOf(item);
    }
}