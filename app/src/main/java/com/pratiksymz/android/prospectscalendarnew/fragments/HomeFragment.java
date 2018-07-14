package com.pratiksymz.android.prospectscalendarnew.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.pratiksymz.android.prospectscalendarnew.Retrofit.ApiService;
import com.pratiksymz.android.prospectscalendarnew.Retrofit.ApiUtils;
import com.pratiksymz.android.prospectscalendarnew.R;
import com.pratiksymz.android.prospectscalendarnew.model.HomeApi;
import com.pratiksymz.android.prospectscalendarnew.model.HomeResult;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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
public class HomeFragment extends Fragment {
    @BindView(R.id.home_expenses_year)
    TextView mHomeYearView;

    @BindView(R.id.home_expenses_day)
    TextView mHomeDayView;

    @BindView(R.id.view_day_status)
    ImageView mHomeDayStatus;

    @BindView(R.id.home_expenses_month)
    TextView mHomeMonthView;

    @BindView(R.id.view_month_status)
    ImageView mHomeMonthStatus;

    @BindView(R.id.balance_sheet)
    CardView mBalanceSheet;

    private LineChartView mLineChart;

    private static final DecimalFormat VALUE_FORMATTER = new DecimalFormat("#,##,###");

    private ShimmerFrameLayout mShimmerViewContainer;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        // Bind the views
        ButterKnife.bind(this, rootView);

        // Show loading while dashboard data is not loaded
        mShimmerViewContainer = rootView.findViewById(R.id.dashboard_shimmer_view_container);
        mShimmerViewContainer.startShimmerAnimation();

        // Set the HomeApi data
        ApiService apiService = ApiUtils.getApiService();
        apiService.homeResults().enqueue(new Callback<HomeApi>() {
            @Override
            public void onResponse(Call<HomeApi> call, Response<HomeApi> response) {
                HomeApi home = response.body();
                if (home.getStatusCode() == 101 && home.getStatusMessage().equals("success")) {
                    HomeResult result = home.getHomeResult();

                    mHomeYearView.setText("Rs. " + VALUE_FORMATTER.format(result.getYearTotal()));
                    mHomeMonthView.setText("Rs. " + VALUE_FORMATTER.format(result.getMonthTotal()));
                    mHomeDayView.setText("Rs. " + VALUE_FORMATTER.format(result.getTodayTotal()));

                    if (result.getMonthStatus().equals("inc")) {
                        mHomeMonthStatus.setImageDrawable(getActivity().getDrawable(R.drawable.ic_value_up));
                    } else {
                        mHomeMonthStatus.setImageDrawable(getActivity().getDrawable(R.drawable.ic_value_down));
                    }

                    if (result.getTodayStatus().equals("inc")) {
                        mHomeDayStatus.setImageDrawable(getActivity().getDrawable(R.drawable.ic_value_up));
                    } else {
                        mHomeDayStatus.setImageDrawable(getActivity().getDrawable(R.drawable.ic_value_down));
                    }

                } else {
                    Log.d("No Result",
                            home.getStatusCode() + " " + home.getStatusMessage()
                    );
                }

                // Stopping Shimmer Effect's animation after data is loaded to ListView
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
                mBalanceSheet.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<HomeApi> call, Throwable t) {
                Log.e("Url error", t.getLocalizedMessage());
            }
        });

        // Get the view
        mLineChart = rootView.findViewById(R.id.line_chart);
        
        // Generate and set line chart data
        setLineChartData();

        return rootView;
    }

    /**
     * Generates the dataset for the line chart
     */
    private void setLineChartData() {
        List<AxisValue> axisValues = new ArrayList<>();
        List<PointValue> values = new ArrayList<>();

        // Retrieve the months
        List<String> months = Arrays.asList(
                getActivity().getResources().getStringArray(R.array.expense_months)
        );

        // Generate the values
        for (int i = 0; i < months.size(); i++) {
            values.add(new PointValue(i, (float) Math.random() * 100000));
            axisValues.add(new AxisValue(i).setLabel(months.get(i)));
        }

        Line chartLine = new Line(values);
        chartLine.setColor(getActivity().getColor(R.color.violet))
                .setCubic(false)
                .setHasLabels(true)
                .setPointColor(getActivity().getColor(R.color.amber))
                .setStrokeWidth(2);

        List<Line> lines = new ArrayList<>();
        lines.add(chartLine);

        Typeface labelTypeface = ResourcesCompat.getFont(getActivity(), R.font.roboto_medium);

        LineChartData lineChartData = new LineChartData(lines);
        lineChartData.setAxisXBottom(
                new Axis(axisValues)
                        .setHasSeparationLine(false)
                        .setHasLines(false)
                        .setTypeface(labelTypeface)
                        .setTextColor(getActivity().getColor(R.color.black))
        );

        lineChartData.setAxisYLeft(
                new Axis()
                        .setHasSeparationLine(false)
                        .setHasLines(false)
                        .setTextSize(0)
                        .setTypeface(labelTypeface)
                        .setTextColor(getActivity().getColor(R.color.white))
        );

        mLineChart.setLineChartData(lineChartData);
        // For build-up animation we have to disable viewport recalculation.
        mLineChart.setViewportCalculationEnabled(false);

        // And set initial max viewport and current viewport- remember to set viewports after data.
        Viewport viewport = new Viewport(mLineChart.getMaximumViewport());

        mLineChart.setCurrentViewport(viewport);
        mLineChart.setMaxZoom(2);

        mLineChart.setBackgroundColor(getActivity().getColor(R.color.white));

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
