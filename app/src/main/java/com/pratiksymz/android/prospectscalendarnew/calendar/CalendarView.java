package com.pratiksymz.android.prospectscalendarnew.calendar;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pratiksymz.android.prospectscalendarnew.EditorActivity;
import com.pratiksymz.android.prospectscalendarnew.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class CalendarView extends LinearLayout {
    /**
     * LOG LOG_TAG for Logging
     */
    private static final String LOG_TAG = CalendarView.class.getSimpleName();

    /**
     * ImageView Buttons to skip between month view pages
     */
    private ImageView mPreviousButton, mNextButton;

    /**
     * TextView representing the current date
     */
    private TextView mCurrentDate;

    /**
     * GridView to adapt the indivisual date cells
     */
    private GridView mCalendarGridView;

    /**
     * Constant to represt the maximum no. of columns in the mCalendar
     */
    private static final int MAX_CALENDAR_COLUMN = 42;

    /**
     * Variables to keep track of the selected day cell's values
     */
    private int mSelectedMonth, mSelectedYear;

    /**
     * A Date Formatter to format the retrieved date in the desired format
     */
    private SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);

    /**
     * Instance of the current mCalendar
     */
    private Calendar mCalendar = Calendar.getInstance(Locale.ENGLISH);

    /**
     * The Parent activity's mContext
     */
    private Context mContext;

    /**
     * Adapter to set the day cells on the view
     */
    private GridAdapter mGridAdapter;

    /**
     * RecyclerView for the GridView to adapt the date items
     */
    private RecyclerView mCellsRecyclerView;

    /**
     * @LayoutManager for the GridLayout
     */
    private GridLayoutManager gridLayoutManager;

    /**
     * @ArrayList to store the day values
     */
    List<Date> dayValueInCells = new ArrayList<>();

    /**
     * @ArrayList of Calendar Cell Events
     */
    List<CellEvent> mCellEvents = new ArrayList<>();

    /**
     * @array of cell height (final)
     */
    final int[] height = {0};

    /**
     * @boolean flag variable
     */
    boolean heightDetermined = true;

    /**
     * Input Date Format
     */
    SimpleDateFormat dFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

    /**
     * Output Date Format
     */
    SimpleDateFormat dFormatFinal = new SimpleDateFormat("dd MMMM yyyy", Locale.US);

    /**
     * Public Constructor
     *
     * @param context The Context of the Activity
     */
    public CalendarView(Context context) {
        super(context);
    }

    /**
     * Public Constructor
     *
     * @param context The Context of the Activity
     * @param attrs   The attribute of the mCalendar
     */
    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        // Initialize the Calendar UI
        initializeUILayout();

        // Setup the mCalendar adapter to adapt the cell items
        setUpCalendarAdapter();

        // Set the event for the previous button to view the previous month
        setPreviousButtonClickEvent();

        // Set the event for the next button to view the next month
        setNextButtonClickEvent();

        // Set the event for mCalendar cell on click
        setGridCellClickEvents();
    }

    /**
     * Public Constructor
     *
     * @param context      The Context of the Activity
     * @param attrs        The attribute of the mCalendar
     * @param defStyleAttr The default attributes of the mCalendar
     */
    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Helper method to initialize the Calendar UI
     */
    private void initializeUILayout() {
        // Inflate the UI
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(
                mContext.LAYOUT_INFLATER_SERVICE
        );

        // Get the root view
        View rootView = inflater.inflate(R.layout.layout_calendar, this);

        mPreviousButton = rootView.findViewById(R.id.previous_month);
        mNextButton = rootView.findViewById(R.id.next_month);
        mCurrentDate = rootView.findViewById(R.id.display_current_date);
        mCalendarGridView = rootView.findViewById(R.id.calendar_grid);
        mCellsRecyclerView = rootView.findViewById(R.id.recycler_adapter);
        gridLayoutManager = new GridLayoutManager(mContext, 7);
        gridLayoutManager.setOrientation(VERTICAL);
        mCellsRecyclerView.setLayoutManager(gridLayoutManager);
    }

    /**
     * Helper method to set the previous button click event
     */
    private void setPreviousButtonClickEvent() {
        mPreviousButton.setOnClickListener(view -> {
            mCalendar.add(Calendar.MONTH, -1);
            setUpCalendarAdapter();
        });
    }

    /**
     * Helper method to set the next button click event
     */
    private void setNextButtonClickEvent() {
        mNextButton.setOnClickListener(view -> {
            mCalendar.add(Calendar.MONTH, 1);
            setUpCalendarAdapter();
        });
    }

    /**
     * Helper method to setup the Calendar grid item click events
     */
    private void setGridCellClickEvents() {
        mCalendarGridView.setOnItemClickListener((parent, view, position, id) -> {
            Intent editorIntent = new Intent(mContext, EditorActivity.class);

            // TODO: Extract the current date to send it to the EditorActivity
            // Set the intent extras
            String currentDate = dFormatFinal.format(dayValueInCells.get(position));
            String[] dateParts = currentDate.split(" ");
            dateParts[0] = setDayOfMonthSuffix(dateParts[0]);
            currentDate = mergeDateParts(dateParts);
            editorIntent.putExtra("current_date", currentDate);

            // Start the activity
            mContext.startActivity(editorIntent);
        });
    }

    /**
     * Helper method to setup the mCalendar adapter to inflate the cell items
     */
    private void setUpCalendarAdapter() {
        dayValueInCells.clear();
        mCellEvents.clear();

        String dateInString = "27-06-2018";
        Date date = null;
        try {
            date = dFormat.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Add a placeholder cell event to the list
        mCellEvents.add(new CellEvent("Hello", date));

        Calendar calendar = (Calendar) mCalendar.clone();

        calendar.set(Calendar.DAY_OF_MONTH, 1);

        int firstDayOfTheMonth = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        calendar.add(Calendar.DAY_OF_MONTH, -firstDayOfTheMonth);

        while (dayValueInCells.size() < MAX_CALENDAR_COLUMN) {
            dayValueInCells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        Log.d(LOG_TAG, "Number of dates: " + dayValueInCells.size());

        String formattedDate = formatter.format(mCalendar.getTime());
        mCurrentDate.setText(formattedDate);

        // Determine the height of the calendar to be inflated during runtime, if not already known
        if (heightDetermined) {
            final ViewTreeObserver observe = mCalendarGridView.getViewTreeObserver();
            observe.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    mCalendarGridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    height[0] = mCalendarGridView.getHeight();
                    setValues();
                    heightDetermined = false;
                }
            });
        } else {
            // Height already determined. Set the cells on the grid
            setValues();
        }
    }

    /**
     * Helper method to adapt the calendar grid onto the rootview
     */
    private void setValues() {
        mGridAdapter = new GridAdapter(mContext, dayValueInCells, mCalendar, mCellEvents, height[0]);
        mCalendarGridView.setAdapter(mGridAdapter);
    }

    /**
     * Helper method to add the suffix "st, nd, rd, th.." to the dates
     * @param dayOfMonth The day of the month
     * @return The date with the respective suffix
     */
    private String setDayOfMonthSuffix(String dayOfMonth) {
        int day = Integer.parseInt(dayOfMonth);
        if (day >= 11 && day <= 13) {
            return dayOfMonth + "th";
        }

        switch (day % 10) {
            case 1:
                return dayOfMonth + "st";
            case 2:
                return dayOfMonth + "nd";
            case 3:
                return dayOfMonth + "rd";
            default:
                return dayOfMonth + "th";
        }
    }

    /**
     * Helper method to merge the different date parts to obtain the desired date format
     *
     * @param dateParts The @ArrayList of split date String
     * @return The merged date string
     */
    private String mergeDateParts(String[] dateParts) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < dateParts.length; i++) {
            builder.append(dateParts[i]);
            if (i != dateParts.length - 1) {
                builder.append(" ");
            }
        }

        return builder.toString();
    }
}