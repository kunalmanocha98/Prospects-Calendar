package com.pratiksymz.android.prospectscalendarnew.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pratiksymz.android.prospectscalendarnew.R;
import com.pratiksymz.android.prospectscalendarnew.models.DayItem;

import java.util.List;

public class DayItemAdapter extends RecyclerView.Adapter<DayItemAdapter.ViewHolder> {
    private Context mContext;
    private List<DayItem> mDayItems;

    public DayItemAdapter(Context context, List<DayItem> dayItems) {
        mContext = context;
        mDayItems = dayItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.layout_day_item, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        // Get the current item
        DayItem dayItem = mDayItems.get(position);

        viewHolder.category.setText(dayItem.getCategory());

        Drawable gradient;
        String color = null;
        switch (dayItem.getCategory()) {
            case "food":
                gradient = mContext.getDrawable(R.drawable.category_food_gradient);
                break;

            case "social_life":
                gradient = mContext.getDrawable(R.drawable.category_social_gradient);
                break;

            case "self_dev":
                gradient = mContext.getDrawable(R.drawable.category_self_gradient);
                break;

            case "transport":
                gradient = mContext.getDrawable(R.drawable.category_transport_gradient);
                break;

            case "beauty":
                gradient = mContext.getDrawable(R.drawable.category_beauty_gradient);
                color = "#EC407A";
                break;

            case "education":
                gradient = mContext.getDrawable(R.drawable.category_education_gradient);
                color = "#FF6F00";
                break;

            case "gift":
                gradient = mContext.getDrawable(R.drawable.category_gift_gradient);
                color = "#311B92";
                break;

            default:
                gradient = mContext.getDrawable(R.drawable.category_other_gradient);
                color = "#263238";
                break;
        }

        viewHolder.categoryGradient.setImageDrawable(gradient);

        viewHolder.paymentMethod.setText(dayItem.getPaymentMethod());
        viewHolder.paymentMethod.setTextColor(Color.parseColor(color));

        viewHolder.amount.setText("₹"+String.valueOf(dayItem.getAmount()));
    }

    @Override
    public int getItemCount() {
        return mDayItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView category, paymentMethod, amount;
        private ImageView categoryGradient;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            category = itemView.findViewById(R.id.day_item_category);
            paymentMethod = itemView.findViewById(R.id.day_item_payment_method);
            amount = itemView.findViewById(R.id.day_item_amount);
            categoryGradient = itemView.findViewById(R.id.day_item_category_gradient);
        }
    }
}
