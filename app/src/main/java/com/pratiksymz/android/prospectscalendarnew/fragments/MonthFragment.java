package com.pratiksymz.android.prospectscalendarnew.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pratiksymz.android.prospectscalendarnew.R;
import com.pratiksymz.android.prospectscalendarnew.adapters.MonthSectionAdapter;
import com.pratiksymz.android.prospectscalendarnew.models.MonthApi;
import com.pratiksymz.android.prospectscalendarnew.retrofit.ApiService;
import com.pratiksymz.android.prospectscalendarnew.retrofit.ApiUtils;

import javax.security.auth.callback.Callback;

import retrofit2.Call;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class MonthFragment extends Fragment {
ApiService apiService;

    public MonthFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View v= inflater.inflate(R.layout.fragment_month, container, false);
       apiService= ApiUtils.getApiService();
       apiService.monthresults().enqueue(new retrofit2.Callback<MonthApi>() {
           @Override
           public void onResponse(Call<MonthApi> call, Response<MonthApi> response) {
               if (response.isSuccessful()){
                   if (response.body().getMessage().equals("success")){
                       RecyclerView recyclerView=v.findViewById(R.id.rv_month_section);
                       recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                       MonthSectionAdapter adapter=new MonthSectionAdapter(getActivity(),response.body().getMonthresults(), month -> {
                           DaysofMonthFragment  daysofMonthFragment=new DaysofMonthFragment();
                           loadfragment(daysofMonthFragment,month);
                       });
                       recyclerView.setAdapter(adapter);
                       Log.e("response","is successful");

                   }
               }else {
                   Log.e("response","is unsuccessful");

               }
           }

           @Override
           public void onFailure(Call<MonthApi> call, Throwable t) {
               Log.e("response", "is failed due to -"+t.getLocalizedMessage());

           }
       });
       return v;
    }

    private void loadfragment(Fragment fragment,String month) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Bundle bundle=new Bundle();
        bundle.putString("month",month);
        fragment.setArguments(bundle);
        transaction.replace(R.id.layout_container,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
