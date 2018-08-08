package com.agnitio.prospectscalendar.fragments;


import android.animation.ObjectAnimator;
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

import com.agnitio.prospectscalendar.R;
import com.agnitio.prospectscalendar.interfaces.CalModConstants;
import com.agnitio.prospectscalendar.models.CalModChartResults;
import com.agnitio.prospectscalendar.models.CalModHomeApi;
import com.agnitio.prospectscalendar.retrofit.CalModApiService;
import com.agnitio.prospectscalendar.retrofit.CalModApiUtils;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;
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

    private LineChartView mLineChart;
    String themeColor="E85B36",themeColordark="#020001";

    private static final DecimalFormat VALUE_FORMATTER = new DecimalFormat("#,##,###");

    private ShimmerFrameLayout mShimmerViewContainer;

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


//        ObjectAnimator animation = ObjectAnimator.ofFloat(mBalanceSheet, "translationX", 100f);
//        animation.setDuration(2000);
//        animation.start();


        // Bind the views

        // getting arguments...
        if (getArguments() != null) {
            if (getArguments().getString(CalModConstants.PageParams.BUNDLE_PARAMS_THEME_TYPE).
                    equals(CalModConstants.PageParams.THEME_TYPE_APP)){
                themeColor = getArguments().getString(CalModConstants.PageParams.BUNDLE_PARAMS_THEME_COLOR_PRIMARY);
                themeColordark = getArguments().getString(CalModConstants.PageParams.BUNDLE_PARAMS_THEME_COLOR_PRIMARY_DARK);
                mBalanceSheet.setCardBackgroundColor(Color.parseColor(themeColor));
            }

        }

        // Show loading while dashboard data is not loaded
        mShimmerViewContainer = getView().findViewById(R.id.dashboard_shimmer_view_container);
        mShimmerViewContainer.startShimmerAnimation();

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
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
                mBalanceSheet.setVisibility(View.VISIBLE);

            }

            @Override
            public void onFailure(Call<CalModHomeApi> call, Throwable t) {
                Log.e("Url error", t.getLocalizedMessage());
            }
        });

        // Get the view
        mLineChart = getView().findViewById(R.id.line_chart);

        // Generate and set line chart data


    }

    /**
     * Generates the dataset for the line chart
     *
     * @param calModChartResultsList
     */
    private void setLineChartData(List<CalModChartResults> calModChartResultsList, String themecolorprimary, String themeColordark) {

        List<AxisValue> axisValues = new ArrayList<>();
        LineChartData data = new LineChartData();
        List<PointValue> values = new ArrayList<>();

        // Retrieve the months
        List<String> months = Arrays.asList(
//                getActivity().getResources().getStringArray(R.array.expense_months)
        );

        // Generate the values
        for (int i = 0; i < calModChartResultsList.size(); i++) {
            values.add(new PointValue(i, (float) calModChartResultsList.get(i).getMonth_data()));
            axisValues.add(new AxisValue(i).setLabel(calModChartResultsList.get(i).getMonth_name()));
        }

        Line chartLine = new Line(values);
        chartLine.setColor(Color.parseColor(themeColordark))
                .setCubic(false)
                .setHasLabels(true)
                .setPointColor(Color.parseColor(themecolorprimary))
                .setStrokeWidth(2);

        List<Line> lines = new ArrayList<>();
        lines.add(chartLine);

        Typeface labelTypeface = ResourcesCompat.getFont(getActivity(), R.font.roboto_medium);

        data.setLines(lines);
        data.setAxisXBottom(
                new Axis(axisValues)
                        .setHasSeparationLine(false)
                        .setHasLines(false)
                        .setTypeface(labelTypeface)
                        .setTextColor(ContextCompat.getColor(getActivity(),R.color.black))
        );
        mLineChart.setLineChartData(data);
        // For build-up animation we have to disable viewport recalculation.
        mLineChart.setViewportCalculationEnabled(false);

        // And set initial max viewport and current viewport- remember to set viewports after data.
        Viewport viewport = new Viewport(mLineChart.getMaximumViewport());

        mLineChart.setCurrentViewport(viewport);
        mLineChart.setMaxZoom(2);

        mLineChart.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.white));

        mLineChart.setOnValueTouchListener(new LineChartOnValueSelectListener() {
            @Override
            public void onValueSelected(int lineIndex, int pointIndex, PointValue value) {
                Toast.makeText(
                        getActivity(),
                        "Data: "
                                + String.valueOf(value.getX())
                                + " " + String.valueOf(value.getY()),
                        Toast.LENGTH_SHORT
                ).show();
            }

            @Override
            public void onValueDeselected() {

            }
        });

        mLineChart.setZoomType(ZoomType.HORIZONTAL);
    }

}
