package com.agnitio.calendar.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.agnitio.calendar.R;
import com.agnitio.calendar.interfaces.CalModOnDataSelectListener;

import java.util.Arrays;
import java.util.List;

public class CalModDialogAdapter extends RecyclerView.Adapter<CalModDialogAdapter.ViewHolder> {
    private Context mContext;
    private List<String> list;
    private CalModOnDataSelectListener dataSelectListener;


    public CalModDialogAdapter(Context context, List<String> dialogItems, CalModOnDataSelectListener dataTransferInterface) {
        mContext = context;
        list = dialogItems;
        dataSelectListener= dataTransferInterface;
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
            vh.optionitem.setText(list.get(position));
            vh.optionitem.setOnClickListener(view -> {dataSelectListener.onSelect(list.get(position),true);});
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView optionitem;

        public ViewHolder(View itemView) {
            super(itemView);
            optionitem = itemView.findViewById(R.id.dialog_option_text_view);
        }
    }
}
