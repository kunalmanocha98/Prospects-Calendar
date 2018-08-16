package com.agnitio.calendar.fragments;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.agnitio.calendar.R;
import com.agnitio.calendar.adapters.CalModDaySectionAdapter;
import com.agnitio.calendar.interfaces.CalModConstants;
import com.agnitio.calendar.models.CalModDayApi;
import com.agnitio.calendar.retrofit.CalModApiService;
import com.agnitio.calendar.retrofit.CalModApiUtils;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalModDayFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private CalModApiService calModApiService;
    private SwipeRefreshLayout swipeRefreshLayout;
    RelativeLayout wrapper_startdate, wrapper_endate;
    TextView txt_startdate,txt_enddate;
    String themeColor = "E85B36", themeColordark = "#020001";
    private DatePickerDialog startdatePicker, enddatePicker;


    public CalModDayFragment() {
        // Required empty public constructor
    }

    public static CalModDayFragment newInstance(Bundle bundle) {
        CalModDayFragment calModDayFragment = new CalModDayFragment();
        calModDayFragment.setArguments(bundle);
        return calModDayFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.cal_mod_fragment_day, container, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Inflate the layout for this fragment
        swipeRefreshLayout = getView().findViewById(R.id.swiperefreshlayout_day_section);
        wrapper_endate = getView().findViewById(R.id.contentwrapper_enddate);
        wrapper_startdate = getView().findViewById(R.id.contentwrapper_startdate);
        txt_startdate=getView().findViewById(R.id.txt_start_date);
        txt_enddate=getView().findViewById(R.id.txt_end_date);
        txt_startdate.setText(CalModConstants.DateFormatter.converttodateformat("EEE, dd MMM yy",Calendar.getInstance().getTime()));
        txt_enddate.setText(CalModConstants.DateFormatter.converttodateformat("EEE, dd MMM yy",Calendar.getInstance().getTime()));
        startdatePicker = new DatePickerDialog(getActivity(), R.style.MyDatepicker, startdateSetListener, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        enddatePicker = new DatePickerDialog(getActivity(), R.style.MyDatepicker, enddateSetListener, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        wrapper_startdate.setOnClickListener(view -> startdatePicker.show());
        wrapper_endate.setOnClickListener(view -> enddatePicker.show());

        swipeRefreshLayout.setOnRefreshListener(this);

        // Get the Day Api Data
        getdata(getView());
    }

    private void getdata(View view) {
        if (getArguments() != null) {
            if (getArguments().getString(CalModConstants.PageParams.BUNDLE_PARAMS_THEME_TYPE).
                    equals(CalModConstants.PageParams.THEME_TYPE_APP)) {
                themeColor = getArguments().getString(CalModConstants.PageParams.BUNDLE_PARAMS_THEME_COLOR_PRIMARY);
                themeColordark = getArguments().getString(CalModConstants.PageParams.BUNDLE_PARAMS_THEME_COLOR_PRIMARY_DARK);
            }

        }
        swipeRefreshLayout.setRefreshing(true);
        calModApiService = CalModApiUtils.getApiService();
        calModApiService.dayResults().enqueue(new Callback<CalModDayApi>() {
            @Override
            public void onResponse(Call<CalModDayApi> call, Response<CalModDayApi> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatusMessage().equals("success")) {

                        // Call the CalModDaySection Adapterhttp://bydegreestest.agnitioworld.com/calendar_management/day.php
                        CalModDaySectionAdapter adapter = new CalModDaySectionAdapter(getActivity(), response.body().getCalModDayResults(), themeColor);
                        RecyclerView recyclerView = view.findViewById(R.id.day_section_recycler_view);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.setAdapter(adapter);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                } else {
                    Log.d("No Result", "no result");
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<CalModDayApi> call, Throwable t) {
                Log.e("Url error", t.getLocalizedMessage());
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onRefresh() {
        getdata(getView());
    }

    DatePickerDialog.OnDateSetListener startdateSetListener = (datePicker, year, month, day) -> {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
        cal.set(Calendar.MONTH, datePicker.getMonth());
        cal.set(Calendar.YEAR, datePicker.getYear());
        String startdate= CalModConstants.DateFormatter.converttodateformat("EEE, dd MMM yy",cal.getTime());
        txt_startdate.setText(startdate);
    };

    DatePickerDialog.OnDateSetListener enddateSetListener = (datePicker, year, month, day) -> {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
        cal.set(Calendar.MONTH, datePicker.getMonth());
        cal.set(Calendar.YEAR, datePicker.getYear());
        String enddate= CalModConstants.DateFormatter.converttodateformat("EEE, dd MMM yy",cal.getTime());
        txt_enddate.setText(enddate);
    };
}
