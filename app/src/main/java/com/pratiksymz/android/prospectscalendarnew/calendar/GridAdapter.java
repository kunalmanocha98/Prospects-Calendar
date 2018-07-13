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
    /**
     * LOG LOG_TAG for Logging
     */
    private static final String LOG_TAG = GridAdapter.class.getSimpleName();

    /**
     * The context of the parent activity
     */
    private Context mContext;

    /**
     * @LayoutInflater to inflate the month grid view
     */
    private LayoutInflater mInflater;

    /**
     * @ArrayList of dates of a month
     */
    private List<Date> mMonthlyDates;

    /**
     * Calendar instance for the current date
     */
    private Calendar mCurrentDate;

    /**
     * @ArrayList of Calendar Cell Events
     */
    private List<CellEvent> mAllEvents;

    /**
     * The cell height
     */
    private int mHeight;

    /**
     * Public Constructor
     *
     * @param context      The context of the parent activity
     * @param monthlyDates The List of dates of a month
     * @param currentDate  The current date instance
     * @param allEvents    The list of calendar cell events
     * @param height       The grid height
     */
    public GridAdapter(Context context, List<Date> monthlyDates, Calendar currentDate, List<CellEvent> allEvents, int height) {
        super(context, R.layout.layout_calendar_cell);

        mContext = context;
        mMonthlyDates = monthlyDates;
        mCurrentDate = currentDate;
        mAllEvents = allEvents;
        mInflater = LayoutInflater.from(context);
        mHeight = height;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the current Date
        Date mDate = mMonthlyDates.get(position);
        // Get the calendar instance
        Calendar dateCal = Calendar.getInstance();

        // Set the calendar time
        dateCal.setTime(mDate);

        int dayValue = dateCal.get(Calendar.DAY_OF_MONTH);
        int displayMonth = dateCal.get(Calendar.MONTH) + 1;
        int displayYear = dateCal.get(Calendar.YEAR);
        int currentMonth = mCurrentDate.get(Calendar.MONTH) + 1;
        int currentYear = mCurrentDate.get(Calendar.YEAR);

        View view = convertView;
        if (view == null) {
            view = mInflater.inflate(R.layout.layout_calendar_cell, parent, false);
        }

        int remainder = mHeight % 10;
        view.setMinimumHeight((mHeight - remainder) / 6);

        if (displayMonth == currentMonth && displayYear == currentYear) {
            view.setBackgroundColor(mContext.getColor(R.color.white));

        } else {
            view.setBackgroundColor(mContext.getColor(R.color.lighterGray));
        }

        // Add the day to calendar
        TextView cellNumber = view.findViewById(R.id.calendar_date_id);
        cellNumber.setText(String.valueOf(dayValue));

        // Add events to the calendar
        TextView eventIndicator = view.findViewById(R.id.event_id);

        LinearLayout linearLayout = view.findViewById(R.id.event_wrapper);
        linearLayout.setBackgroundColor(Color.parseColor("#ffffff"));
        Calendar eventCalendar = Calendar.getInstance();
        for (int i = 0; i < mAllEvents.size(); i++) {
            eventCalendar.setTime(mAllEvents.get(i).getDate());
            if (dayValue == eventCalendar.get(Calendar.DAY_OF_MONTH) && displayMonth == eventCalendar.get(Calendar.MONTH) + 1
                    && displayYear == eventCalendar.get(Calendar.YEAR)) {
                linearLayout.setBackgroundColor(Color.parseColor("#ed5050"));
                eventIndicator.setText("₹20000");
            }
        }
        return view;
    }

    @Override
    public int getCount() {
        return mMonthlyDates.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return mMonthlyDates.get(position);
    }

    @Override
    public int getPosition(Object item) {
        return mMonthlyDates.indexOf(item);
    }
}