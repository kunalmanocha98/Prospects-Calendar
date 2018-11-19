package com.agnitio.calendar.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.agnitio.calendar.R;
import com.agnitio.calendar.interfaces.CalModOnDataSelectListener;
import com.agnitio.calendar.models.CalModCategoryList;
import com.agnitio.calendar.models.CalModListItem;
import com.bumptech.glide.Glide;

import java.util.Arrays;
import java.util.List;

public class CalModPaymentMethodDialogAdapter extends RecyclerView.Adapter<CalModPaymentMethodDialogAdapter.ViewHolder> {
    private Context mContext;
    private List<CalModListItem> methodlist;
    private CalModOnDataSelectListener listener;

    public CalModPaymentMethodDialogAdapter(Context context, List<CalModListItem> list, CalModOnDataSelectListener listener) {
        mContext = context;
        this.methodlist = list;
        this.listener =listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View itemView = layoutInflater.inflate(R.layout.cal_mod_layout_dialog_option_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder vh, int position) {
        vh.txt_option_item.setText(methodlist.get(position).getItemname());
        vh.root_option_item.setOnClickListener(view -> {listener.onSelect(position,methodlist.get(position).getItemname());});
        Glide.with(mContext)
                .load(methodlist.get(position).iconpath)
                .into(vh.imgv_option_item);
    }

    @Override
    public int getItemCount() {
        return methodlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_option_item;
        ImageView imgv_option_item;
        RelativeLayout root_option_item;
        public ViewHolder(View itemView) {
            super(itemView);
            txt_option_item = itemView.findViewById(R.id.dialog_option_text_view);
            imgv_option_item=itemView.findViewById(R.id.imgv_dialog_option);
            root_option_item=itemView.findViewById(R.id.dialog_item_root);
        }
    }
}
