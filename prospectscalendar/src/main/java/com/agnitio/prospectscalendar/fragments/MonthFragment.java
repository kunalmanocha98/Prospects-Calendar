package com.agnitio.prospectscalendar.fragments;


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


import com.agnitio.prospectscalendar.R;
import com.agnitio.prospectscalendar.adapters.MonthSectionAdapter;
import com.agnitio.prospectscalendar.interfaces.Constants;
import com.agnitio.prospectscalendar.models.MonthApi;
import com.agnitio.prospectscalendar.retrofit.ApiService;
import com.agnitio.prospectscalendar.retrofit.ApiUtils;

import retrofit2.Call;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class MonthFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    ApiService apiService;
    SwipeRefreshLayout swipeRefreshLayout;
    View view;

    public MonthFragment() {
        // Required empty public constructor
    }
    public static MonthFragment newInstance(Bundle bundle) {
        MonthFragment monthFragment=new MonthFragment();
        monthFragment.setArguments(bundle);
        return monthFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_month, container, false);
        swipeRefreshLayout = view.findViewById(R.id.swiperefreshlayout_month_section);
        swipeRefreshLayout.setOnRefreshListener(this);
        getdata();

        return view;
    }

    private void getdata() {
        if (getArguments() != null) {
            if (getArguments().getString(Constants.PageParams.BUNDLE_PARAMS_THEME_TYPE).
                    equals(Constants.PageParams.THEME_TYPE_APP)) {
            }

        }
        swipeRefreshLayout.setRefreshing(true);
        apiService = ApiUtils.getApiService();
        apiService.monthresults().enqueue(new retrofit2.Callback<MonthApi>() {
            @Override
            public void onResponse(Call<MonthApi> call, Response<MonthApi> response) {
                if (response.isSuccessful()) {
                    if (response.body().getMessage().equals("success")) {
                        swipeRefreshLayout.setRefreshing(false);
                        RecyclerView recyclerView = view.findViewById(R.id.rv_month_section);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        MonthSectionAdapter adapter = new MonthSectionAdapter(getActivity(), response.body().getMonthresults(), month -> {
                            DaysofMonthFragment daysofMonthFragment = new DaysofMonthFragment();
                            loadfragment(daysofMonthFragment, month);
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
            public void onFailure(Call<MonthApi> call, Throwable t) {
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
}
