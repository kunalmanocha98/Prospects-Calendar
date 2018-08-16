package com.agnitio.calendar.calendar;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
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
import android.widget.ProgressBar;
import android.widget.TextView;


import com.agnitio.calendar.Dialogs.CalModCustomDialog;
import com.agnitio.calendar.R;
import com.agnitio.calendar.activities.CalModActivityAddEvent;
import com.agnitio.calendar.interfaces.CalModConstants;
import com.agnitio.calendar.models.CalModDayApi;
import com.agnitio.calendar.models.CalModDayResult;
import com.agnitio.calendar.retrofit.CalModApiService;
import com.agnitio.calendar.retrofit.CalModApiUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CalModMyCalendarView extends LinearLayout implements CalModCustomDialog.DialognxtListener {
    /**
     * LOG LOG_TAG for Logging
     */
    private static final String LOG_TAG = CalModMyCalendarView.class.getSimpleName();

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
    private SimpleDateFormat formatter = new SimpleDateFormat("MMMM, YYYY", Locale.ENGLISH);

    /**
     * Instance of the current mCalendar
     */
    private Calendar mCalendar = Calendar.getInstance();

    /**
     * The Parent activity's mContext
     */
    private Context mContext;

    LinearLayout mcalendarheader;

    /**
     * Adapter to set the day cells on the view
     */
    private CalModGridAdapter mCalModGridAdapter;

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
    List<CalModCellEvent> mCalModCellEvents = new ArrayList<>();
    /**
     * @Object of CalModApiService
     */
    CalModApiService calModApiService;
    /**
     * @ProgressBar of calendar
     */
    ProgressBar progressBar;

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
    public CalModMyCalendarView(Context context) {
        super(context);
    }

    /**
     * Public Constructor
     *
     * @param context The Context of the Activity
     * @param attrs   The attribute of the mCalendar
     */
    public CalModMyCalendarView(Context context, AttributeSet attrs) {
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

    private FragmentActivity c;


    public FragmentActivity getmContext() {
        return c;
    }

    public void setmContext(FragmentActivity context, int themecolor) {
        this.c = context;
        this.mcalendarheader.setBackgroundColor(themecolor);
//        c.getSupportFragmentManager();
    }

    public CalModMyCalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        View rootView = inflater.inflate(R.layout.cal_mod_layout_calendar, this);
        mcalendarheader=rootView.findViewById(R.id.calendar_header);
        mPreviousButton = rootView.findViewById(R.id.previous_month);
        mNextButton = rootView.findViewById(R.id.next_month);
        mCurrentDate = rootView.findViewById(R.id.display_current_date);
        mCalendarGridView = rootView.findViewById(R.id.calendar_grid);
        mCellsRecyclerView = rootView.findViewById(R.id.recycler_adapter);
        gridLayoutManager = new GridLayoutManager(mContext, 7);
        gridLayoutManager.setOrientation(VERTICAL);
        mCellsRecyclerView.setLayoutManager(gridLayoutManager);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        calModApiService = CalModApiUtils.getApiService();
        mCurrentDate.setText(CalModConstants.DateFormatter.converttodateformat("MMMM, YYYY",Calendar.getInstance().getTime()));
    }

    /**
     * Helper method to set the previous button click event
     */
    private void setPreviousButtonClickEvent() {
        mPreviousButton.setOnClickListener(view -> {
            progressBar.setVisibility(View.VISIBLE);
            mCalendar.add(Calendar.MONTH, -1);
            setUpCalendarAdapter();
        });
    }

    /**
     * Helper method to set the next button click event
     */
    private void setNextButtonClickEvent() {

        mNextButton.setOnClickListener(view -> {
            progressBar.setVisibility(View.VISIBLE);
            mCalendar.add(Calendar.MONTH, 1);
            setUpCalendarAdapter();
        });
    }

    /**
     * Helper method to setup the Calendar grid item click events
     */
    private void setGridCellClickEvents() {
        mCalendarGridView.setOnItemClickListener((parent, view, position, id) -> {
            Intent editorIntent = new Intent(mContext, CalModActivityAddEvent.class);

            showDialog(dayValueInCells.get(position));
        });
    }

    public void showDialog(Date date) {
        CalModCustomDialog dialog = new CalModCustomDialog(mContext, R.style.MyDialog, date,this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.show();
    }

    /**
     * Helper method to setup the mCalendar adapter to inflate the cell items
     */
    private void setUpCalendarAdapter() {
        dayValueInCells.clear();
        mCalModCellEvents.clear();

        Calendar calendar = (Calendar) mCalendar.clone();
        SimpleDateFormat month_date = new SimpleDateFormat("MMM");
        String month_name = month_date.format(calendar.getTime());
//        Toast.makeText(mContext, ""+month_name, Toast.LENGTH_SHORT).show();
        getdates(month_name);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        int firstDayOfTheMonth = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        calendar.add(Calendar.DAY_OF_MONTH, -firstDayOfTheMonth);

        while (dayValueInCells.size() < MAX_CALENDAR_COLUMN) {
            dayValueInCells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        Log.d(LOG_TAG, "Number of dates: " + dayValueInCells.size());


        // Determine the height of the calendar to be inflated during runtime, if not already known

    }

    private void getdates(String month_name) {
        calModApiService.monthwisedataresults(month_name).enqueue(new Callback<CalModDayApi>() {
            @Override
            public void onResponse(Call<CalModDayApi> call, Response<CalModDayApi> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatusMessage().equals("success")) {
                        setcalendar(response.body().getCalModDayResults());
                        Log.e("response", "success");
                    }
                } else {
                    Log.e("response", "unsuccessful");

                }
            }

            @Override
            public void onFailure(Call<CalModDayApi> call, Throwable t) {
                setcalendar(Collections.emptyList());
                Log.e("response", "failed");
            }
        });
    }

    private void setcalendar(List<CalModDayResult> calModDayResults) {
        setValues(calModDayResults);
        if (heightDetermined) {
            final ViewTreeObserver observe = mCalendarGridView.getViewTreeObserver();
            observe.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    mCalendarGridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    height[0] = mCalendarGridView.getHeight();
                    setValues(calModDayResults);
                    heightDetermined = false;
                }
            });
        } else {
            // Height already determined. Set the cells on the grid
            setValues(calModDayResults);
        }

    }

    /**
     * Helper method to adapt the calendar grid onto the rootview
     */
    private void setValues(List<CalModDayResult> calModDayResults) {
        progressBar.setVisibility(View.GONE);
        String formattedDate = formatter.format(mCalendar.getTime());
        mCurrentDate.setText(formattedDate);
        mCalModGridAdapter = new CalModGridAdapter(mContext, dayValueInCells, mCalendar, calModDayResults, height[0]);
        mCalendarGridView.setAdapter(mCalModGridAdapter);
    }

    /**
     * Helper method to add the suffix "st, nd, rd, th.." to the dates
     *
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

    @Override
    public void onnextclick(CalModCustomDialog dialog, Date date) {
        dialog.dismiss();
        showDialog(addsubdays(date,1));
    }

    @Override
    public void onpreviousclick(CalModCustomDialog dialog, Date date) {
        dialog.dismiss();
        showDialog(addsubdays(date,-1));
    }

    public static Date addsubdays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }
}