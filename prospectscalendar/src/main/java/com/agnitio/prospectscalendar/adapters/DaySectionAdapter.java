package com.agnitio.prospectscalendar.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.agnitio.prospectscalendar.R;
import com.agnitio.prospectscalendar.models.DayResult;

import java.util.List;

public class DaySectionAdapter extends RecyclerView.Adapter<DaySectionAdapter.ViewHolder> {
    private Context mContext;
    private List<DayResult> mDayResults;
    private String themecolor;

    public DaySectionAdapter(Context context, List<DayResult> dayResults, String themeColor) {
        mContext = context;
        mDayResults = dayResults;
        this.themecolor=themeColor;
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

//        Drawable[] drawables=mDate.getCompoundDrawables();
//        drawables[0].setColorFilter(Color.parseColor(themecolor), PorterDuff.Mode.SRC_ATOP);

        mDate.getBackground().setColorFilter(Color.parseColor(themecolor), PorterDuff.Mode.SRC_ATOP);

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
