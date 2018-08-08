package com.agnitio.prospectscalendar.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.agnitio.prospectscalendar.R;
import com.agnitio.prospectscalendar.models.CalModMonthresults;

import java.util.List;

public class CalModMonthSectionAdapter extends RecyclerView.Adapter<CalModMonthSectionAdapter.ViewHolder>{
    Context context;
    List<CalModMonthresults> list;
    CalModMonthSectionAdapter.monthitemclicklistener monthitemclicklistener;

    public CalModMonthSectionAdapter(Context c, List<CalModMonthresults> list, monthitemclicklistener monthitemclicklistener){
        this.context=c;
        this.list=list;
        this.monthitemclicklistener=monthitemclicklistener;

    }

    @NonNull
    @Override
    public CalModMonthSectionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.cal_mod_layout_month_section, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CalModMonthSectionAdapter.ViewHolder vh, int position) {
        vh.txt_monthname.setText(list.get(position).getMonth());
        vh.txt_monthamount.setText("₹"+list.get(position).getCalModMonthData().getTotal());
        if (position%2==0){
            vh.monthstatus.setImageResource(R.drawable.cal_mod_downarrow);
        }
        vh.month_item.setOnClickListener(view ->{
            monthitemclicklistener.onmonthitemclick(list.get(position).getMonth());
        });

//        vh.btn_details.setOnClickListener(view -> {
//            monthitemclicklistener.onmonthitemclick(list.get(position).getMonth());
//    });
//        vh.month_item.setText(list.get(position).getMonth());
//        vh.month_item_total.setText("₹"+list.get(position).getCalModMonthData().getTotal());
//        vh.monthitemcontainer.setOnClickListener(view -> monthitemclicklistener.onmonthitemclick(list.get(position).getMonth()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_monthname,txt_monthamount;
        ImageView monthstatus;
        CardView month_item;
//        Button btn_details;
//        TextView month_item,month_item_total;
//        ConstraintLayout monthitemcontainer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_monthname=itemView.findViewById(R.id.txt_month_month_segment);
            txt_monthamount=itemView.findViewById(R.id.txt_month_amount);
            monthstatus=itemView.findViewById(R.id.image_view_monthstatus);
            month_item=itemView.findViewById(R.id.month_item_card_container);
//            btn_details=itemView.findViewById(R.id.btn_details);
//            month_item=itemView.findViewById(R.id.month_item);
//            month_item_total=itemView.findViewById(R.id.month_item_total);
//            monthitemcontainer=itemView.findViewById(R.id.month_item_container);
        }
    }
    public interface monthitemclicklistener{
       void onmonthitemclick(String month);
    }
}
