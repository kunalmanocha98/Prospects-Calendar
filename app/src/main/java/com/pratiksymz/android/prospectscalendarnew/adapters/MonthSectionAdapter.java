package com.pratiksymz.android.prospectscalendarnew.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pratiksymz.android.prospectscalendarnew.R;
import com.pratiksymz.android.prospectscalendarnew.models.Monthresults;

import java.util.List;

public class MonthSectionAdapter extends RecyclerView.Adapter<MonthSectionAdapter.ViewHolder>{
    Context context;
    List<Monthresults> list;
    MonthSectionAdapter.monthitemclicklistener monthitemclicklistener;

    public  MonthSectionAdapter(Context c, List<Monthresults> list, monthitemclicklistener monthitemclicklistener){
        this.context=c;
        this.list=list;
        this.monthitemclicklistener=monthitemclicklistener;

    }

    @NonNull
    @Override
    public MonthSectionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_month_section, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MonthSectionAdapter.ViewHolder vh, int position) {
        vh.month_item.setText(list.get(position).getMonth());
        vh.month_item_total.setText("₹"+list.get(position).getMonthData().getTotal());
        vh.monthitemcontainer.setOnClickListener(view -> monthitemclicklistener.onmonthitemclick(list.get(position).getMonth()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView month_item,month_item_total;
        ConstraintLayout monthitemcontainer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            month_item=itemView.findViewById(R.id.month_item);
            month_item_total=itemView.findViewById(R.id.month_item_total);
            monthitemcontainer=itemView.findViewById(R.id.month_item_container);
        }
    }
    public interface monthitemclicklistener{
       void onmonthitemclick(String month);
    }
}
