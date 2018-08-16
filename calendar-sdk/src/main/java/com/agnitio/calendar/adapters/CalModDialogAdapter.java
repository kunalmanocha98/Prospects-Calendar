package com.agnitio.calendar.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.agnitio.calendar.R;
import com.agnitio.calendar.interfaces.CalModDialogDataTransferInterface;

import java.util.Arrays;
import java.util.List;

public class CalModDialogAdapter extends RecyclerView.Adapter<CalModDialogAdapter.ViewHolder> {
    private Context mContext;
    private List<String> mDialogItems;
    private List<String> mPaymentMethods;
    private CalModDialogDataTransferInterface mDataTransferInterface;

    /**
     * Dialog Adapter Constructor
     *
     * @param context
     * @param dialogItems
     */
    public CalModDialogAdapter(Context context, List<String> dialogItems,
                               CalModDialogDataTransferInterface dataTransferInterface) {
        mContext = context;
        mDialogItems = dialogItems;
        mPaymentMethods = Arrays.asList(context.getResources().getStringArray(R.array.payment_methods));
        mDataTransferInterface = dataTransferInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View itemView = layoutInflater.inflate(R.layout.cal_mod_layout_dialog_option_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(context, itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        // Get the data item based on position
        String option = mDialogItems.get(position);

        TextView optionView = viewHolder.optionTextView;
        optionView.setText(option);

        optionView.setOnClickListener(view -> {
            boolean isAPaymentMethod = mPaymentMethods.contains(option);
            mDataTransferInterface.sendData(option, isAPaymentMethod);
        });
    }

    @Override
    public int getItemCount() {
        return mDialogItems.size();
    }

    /**
     * Inner ViewHolder class
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private Context context;
        private TextView optionTextView;

        public ViewHolder(Context context, View itemView) {
            super(itemView);

            this.context = context;
            optionTextView = itemView.findViewById(R.id.dialog_option_text_view);
        }
    }
}
