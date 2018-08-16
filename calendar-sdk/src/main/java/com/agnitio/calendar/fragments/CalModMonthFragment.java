package com.agnitio.calendar.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.agnitio.calendar.R;
import com.agnitio.calendar.adapters.CalModMonthSectionAdapter;
import com.agnitio.calendar.interfaces.CalModConstants;
import com.agnitio.calendar.models.CalModMonthApi;
import com.agnitio.calendar.retrofit.CalModApiService;
import com.agnitio.calendar.retrofit.CalModApiUtils;

import retrofit2.Call;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalModMonthFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    CalModApiService calModApiService;
    SwipeRefreshLayout swipeRefreshLayout;
    View view;

    public CalModMonthFragment() {
        // Required empty public constructor
    }
    public static CalModMonthFragment newInstance(Bundle bundle) {
        CalModMonthFragment calModMonthFragment =new CalModMonthFragment();
        calModMonthFragment.setArguments(bundle);
        return calModMonthFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.cal_mod_fragment_month, container, false);
        swipeRefreshLayout = view.findViewById(R.id.swiperefreshlayout_month_section);
        swipeRefreshLayout.setOnRefreshListener(this);
        getdata();

        return view;
    }

    private void getdata() {
        if (getArguments() != null) {
            if (getArguments().getString(CalModConstants.PageParams.BUNDLE_PARAMS_THEME_TYPE).
                    equals(CalModConstants.PageParams.THEME_TYPE_APP)) {
            }

        }
        swipeRefreshLayout.setRefreshing(true);
        calModApiService = CalModApiUtils.getApiService();
        calModApiService.monthresults().enqueue(new retrofit2.Callback<CalModMonthApi>() {
            @Override
            public void onResponse(Call<CalModMonthApi> call, Response<CalModMonthApi> response) {
                if (response.isSuccessful()) {
                    if (response.body().getMessage().equals("success")) {
                        swipeRefreshLayout.setRefreshing(false);
                        RecyclerView recyclerView = view.findViewById(R.id.rv_month_section);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        CalModMonthSectionAdapter adapter = new CalModMonthSectionAdapter(getActivity(), response.body().getMonthresults(), month -> {
                            CalModDaysofMonthFragment calModDaysofMonthFragment = new CalModDaysofMonthFragment();
                            loadfragment(calModDaysofMonthFragment, month);
                        });
                        recyclerView.setAdapter(adapter);
                        Log.e("response", "is successful");

                    }
                } else {
                    swipeRefreshLayout.setRefreshing(false);
                    Log.e("response", "is unsuccessful");
                }
            }

            @Override
            public void onFailure(Call<CalModMonthApi> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Log.e("response", "is failed due to -" + t.getLocalizedMessage());
            }
        });
    }

    private void loadfragment(Fragment fragment, String month) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("month", month);
        fragment.setArguments(bundle);
        transaction.replace(R.id.layout_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onRefresh() {
        getdata();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("resumed----->","month");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("paused----->","month");
    }
}
