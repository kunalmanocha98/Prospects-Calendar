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
import com.pratiksymz.android.prospectscalendarnew.model.DayItem;
import com.pratiksymz.android.prospectscalendarnew.model.DaySection;

import java.util.ArrayList;
import java.util.List;

public class DaySectionAdapter extends RecyclerView.Adapter<DaySectionAdapter.ViewHolder> {
    private Context mContext;
    private List<DaySection> mDaySections;

    public DaySectionAdapter(Context context, List<DaySection> daySections) {
        mContext = context;
        mDaySections = daySections;
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
        DaySection daySection = mDaySections.get(position);

        TextView mDate =  viewHolder.mDayDate;
        TextView mDateTotal = viewHolder.mDayExpenseTotal;

        mDate.setText(daySection.getSectionDate());
        mDateTotal.setText(String.valueOf(daySection.getSectionExpenseTotal()));

        RecyclerView dayExpensesView = viewHolder.mDayExpensesView;
        dayExpensesView.setLayoutManager(new LinearLayoutManager(mContext));

        List<DayItem> dayItems = new ArrayList<>();
        dayItems.add(new DayItem(3563156, "Cash", "Beauty", 56));
        dayItems.add(new DayItem(8563745, "Online Payment", "Education", 9800));

        DayItemAdapter adapter = new DayItemAdapter(mContext, dayItems);
        dayExpensesView.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return mDaySections.size();
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
