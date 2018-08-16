package com.agnitio.calendar.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.agnitio.calendar.R;
import com.agnitio.calendar.models.CalModDayItem;

import java.util.List;

public class CalModDayItemAdapter extends RecyclerView.Adapter<CalModDayItemAdapter.ViewHolder> {
    private Context mContext;
    private List<CalModDayItem> mCalModDayItems;

    public CalModDayItemAdapter(Context context, List<CalModDayItem> calModDayItems) {
        mContext = context;
        mCalModDayItems = calModDayItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.cal_mod_layout_day_item, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        // Get the current item
        viewHolder.content.setText(mCalModDayItems.get(position).getContents());
        viewHolder.content_rate.setText("₹"+Integer.toString(mCalModDayItems.get(position).getAmount()));
        viewHolder.category.setText(mCalModDayItems.get(position).getCategory());
//        viewHolder.category.setBackgroundColor(Color.parseColor(mCalModDayItems.get(position).getColorcode()));
        viewHolder.payment_method.setText(mCalModDayItems.get(position).getPaymentMethod());
//
//        CalModDayItem dayItem = mCalModDayItems.get(position);
//
//        viewHolder.category.setText(dayItem.getCategory());
//
//        Drawable gradient;
//        String color = null;
//        switch (dayItem.getCategory()) {
//            case "Food":
//                gradient = mContext.getDrawable(R.drawable.cal_mod_category_food_gradient);
//                color = "#EC407A";
//                break;
//
//            case "Social_life":
//                gradient = mContext.getDrawable(R.drawable.cal_mod_category_social_gradient);
//                color = "#EC407A";
//                break;
//
//            case "Self_Dev":
//                gradient = mContext.getDrawable(R.drawable.cal_mod_category_self_gradient);
//                color = "#EC407A";
//                break;
//
//            case "Transport":
//                gradient = mContext.getDrawable(R.drawable.cal_mod_category_transport_gradient);
//                color = "#EC407A";
//                break;
//
//            case "Beauty":
//                gradient = mContext.getDrawable(R.drawable.cal_mod_category_beauty_gradient);
//                color = "#EC407A";
//                break;
//
//            case "Education":
//                gradient = mContext.getDrawable(R.drawable.cal_mod_category_education_gradient);
//                color = "#FF6F00";
//                break;
//
//            case "Gift":
//                gradient = mContext.getDrawable(R.drawable.cal_mod_category_gift_gradient);
//                color = "#311B92";
//                break;
//
//            default:
//                gradient = mContext.getDrawable(R.drawable.cal_mod_category_other_gradient);
//                color = "#263238";
//                break;
//        }
//
//        viewHolder.categoryGradient.setBackground(gradient);
//
//        viewHolder.paymentMethod.setText(dayItem.getPaymentMethod());
//        viewHolder.paymentMethod.setTextColor(Color.parseColor(color));
//
//        viewHolder.amount.setText("₹"+String.valueOf(dayItem.getAmount()));
    }

    @Override
    public int getItemCount() {
        return mCalModDayItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView content, content_rate, category, payment_method;

//        private TextView content,category, paymentMethod, amount,;
//        private View categoryGradient;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.txt_contents);
            content_rate = itemView.findViewById(R.id.txt_content_rate);
            payment_method = itemView.findViewById(R.id.txt_payment_method);
            category = itemView.findViewById(R.id.txt_category);
//            category = itemView.findViewById(R.id.day_item_category);
//            paymentMethod = itemView.findViewById(R.id.day_item_payment_method);
//            amount = itemView.findViewById(R.id.day_item_amount);
//            categoryGradient = itemView.findViewById(R.id.day_item_category_gradient);
        }
    }
}
