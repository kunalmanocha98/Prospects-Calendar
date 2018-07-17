package com.pratiksymz.android.prospectscalendarnew.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pratiksymz.android.prospectscalendarnew.R;
import com.pratiksymz.android.prospectscalendarnew.models.DayResult;

import java.util.List;

public class DaySectionAdapter extends RecyclerView.Adapter<DaySectionAdapter.ViewHolder> {
    private Context mContext;
    private List<DayResult> mDayResults;

    public DaySectionAdapter(Context context, List<DayResult> dayResults) {
        mContext = context;
        mDayResults = dayResults;
    }

    @NonNull
    @Override
    public DaySectionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.layout_day_section, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DaySectionAdapter.ViewHolder viewHolder, int position) {
        // Get the current item
        DayResult dayResult = mDayResults.get(position);

        TextView mDate =  viewHolder.mDayDate;
        TextView mDateTotal = viewHolder.mDayExpenseTotal;

        mDate.setText(dayResult.getDate());
        mDateTotal.setText("₹"+String.valueOf(dayResult.getDateData().getDayTotal()));

        RecyclerView dayExpensesView = viewHolder.mDayExpensesView;
        dayExpensesView.setLayoutManager(new LinearLayoutManager(mContext));
        DayItemAdapter adapter = new DayItemAdapter(mContext, dayResult.getDateData().getDayItems());
        dayExpensesView.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return mDayResults.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mDayDate, mDayExpenseTotal;
        private RecyclerView mDayExpensesView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mDayDate = itemView.findViewById(R.id.day_item_date);
            mDayExpenseTotal = itemView.findViewById(R.id.day_item_total);
            mDayExpensesView = itemView.findViewById(R.id.day_item_expense_list);
        }
    }
}
