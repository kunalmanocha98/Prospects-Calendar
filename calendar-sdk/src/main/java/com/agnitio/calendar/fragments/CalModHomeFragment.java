package com.agnitio.calendar.fragments;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.agnitio.calendar.R;
import com.agnitio.calendar.interfaces.CalModConstants;
import com.agnitio.calendar.models.CalModChartResults;
import com.agnitio.calendar.models.CalModHomeApi;
import com.agnitio.calendar.retrofit.CalModApiService;
import com.agnitio.calendar.retrofit.CalModApiUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalModHomeFragment extends Fragment {
    TextView mHomeYearView;
    TextView mHomeDayView;
    ImageView mHomeDayStatus;
    TextView mHomeMonthView;
    ImageView mHomeMonthStatus;
    CardView mBalanceSheet;

    private LineChart lineChart;
    String themeColor="#E85B36",themeColordark="#020001";

    private static final DecimalFormat VALUE_FORMATTER = new DecimalFormat("#,##,###");


    public CalModHomeFragment() {
        // Required empty public constructor
    }

    public static CalModHomeFragment newInstance(Bundle bundle) {
        CalModHomeFragment calModHomeFragment =new CalModHomeFragment();
        calModHomeFragment.setArguments(bundle);
        return calModHomeFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.cal_mod_fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mHomeYearView=getView().findViewById(R.id.home_expenses_year);
        mHomeDayView=getView().findViewById(R.id.home_expenses_day);
        mHomeDayStatus=getView().findViewById(R.id.view_day_status);
        mHomeMonthView=getView().findViewById(R.id.home_expenses_month);
        mHomeMonthStatus=getView().findViewById(R.id.view_month_status);
        mBalanceSheet=getView().findViewById(R.id.balance_sheet);


        if (getArguments() != null) {
            if (getArguments().getString(CalModConstants.PageParams.BUNDLE_PARAMS_THEME_TYPE).
                    equals(CalModConstants.PageParams.THEME_TYPE_APP)){
                themeColor = getArguments().getString(CalModConstants.PageParams.BUNDLE_PARAMS_THEME_COLOR_PRIMARY);
                themeColordark = getArguments().getString(CalModConstants.PageParams.BUNDLE_PARAMS_THEME_COLOR_PRIMARY_DARK);
                mBalanceSheet.setCardBackgroundColor(Color.parseColor(themeColor));
            }

        }

        // Show loading while dashboard data is not loaded

        // Set the CalModHomeApi data
        CalModApiService calModApiService = CalModApiUtils.getApiService();
        calModApiService.homeResults().enqueue(new Callback<CalModHomeApi>() {
            @Override
            public void onResponse(Call<CalModHomeApi> call, Response<CalModHomeApi> response) {
                if (response.isSuccessful() && response.body().getStatusMessage().equals("success")) {
//                    CalModHomeResult result = home.getCalModHomeResult();
                    setLineChartData(response.body().getCalModHomeResult().getCalModChartResultsList(),themeColor,themeColordark);
                    mHomeYearView.setText("Rs. " + VALUE_FORMATTER.format(response.body().getCalModHomeResult().getYearTotal()));
                    mHomeMonthView.setText("Rs. " + VALUE_FORMATTER.format(response.body().getCalModHomeResult().getMonthTotal()));
                    mHomeDayView.setText("Rs. " + VALUE_FORMATTER.format(response.body().getCalModHomeResult().getTodayTotal()));

                    if (response.body().getCalModHomeResult().getMonthStatus().equals("inc")) {
                        mHomeMonthStatus.setImageDrawable(getActivity().getDrawable(R.drawable.cal_mod_ic_value_up));
                    } else {
                        mHomeMonthStatus.setImageDrawable(getActivity().getDrawable(R.drawable.cal_mod_ic_value_down));
                    }

                    if (response.body().getCalModHomeResult().getTodayStatus().equals("inc")) {
                        mHomeDayStatus.setImageDrawable(getActivity().getDrawable(R.drawable.cal_mod_ic_value_up));
                    } else {
                        mHomeDayStatus.setImageDrawable(getActivity().getDrawable(R.drawable.cal_mod_ic_value_down));
                    }


                } else {
//                    Log.d("No Result",response.body().getStatusCode() + " " + response.body().getStatusMessage());
                }

                // Stopping Shimmer Effect's animation after data is loaded to ListView
                mBalanceSheet.setVisibility(View.VISIBLE);

            }

            @Override
            public void onFailure(Call<CalModHomeApi> call, Throwable t) {
                Log.e("Url error", t.getLocalizedMessage());
            }
        });

        // Get the view
        lineChart = getView().findViewById(R.id.line_chart);

        // Generate and set line chart data


    }

    /**
     * Generates the dataset for the line chart
     *
     * @param calModChartResultsList
     */
    private void setLineChartData(List<CalModChartResults> calModChartResultsList, String themecolorprimary, String themeColordark) {
        List<Entry> valsComp1 = new ArrayList<Entry>();



        for (int calmodres=0;calmodres<calModChartResultsList.size();calmodres++){
            Entry entry=new Entry((float)calmodres,(float) calModChartResultsList.get(calmodres).getMonth_data());
            valsComp1.add(entry);
        }

        LineDataSet setComp1 = new LineDataSet(valsComp1, "Yearly Expenses");
        setComp1.setColor(getResources().getColor(R.color.colorPrimary));
        setComp1.setLineWidth(2.0f);
        setComp1.setCircleRadius(0.5f);
        setComp1.setCircleColor(Color.parseColor("#E85B36"));
        setComp1.setDrawFilled(true);
        setComp1.setFillColor(Color.parseColor("#E85B36"));


        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(setComp1);

        LineData data = new LineData(dataSets);
        lineChart.setData(data);
        lineChart.invalidate();// refresh


       IAxisValueFormatter formatter = (value,axis) -> calModChartResultsList.get((int) value).getMonth_name();

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setGranularity(1f);// minimum axis-step (interval) is 1
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(formatter);


        YAxis yAxisright=lineChart.getAxisRight();
        YAxis yAxisleft=lineChart.getAxisLeft();
        yAxisright.setEnabled(false);
        yAxisleft.setEnabled(true);

        lineChart.animateXY(3000,4000);
    }
}
