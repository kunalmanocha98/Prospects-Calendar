package com.agnitio.calendar.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.agnitio.calendar.R;
import com.agnitio.calendar.adapters.CalModDialogAdapter;
import com.agnitio.calendar.interfaces.CalModOnDataSelectListener;

import java.util.Arrays;
import java.util.List;

public class CalModPaymentMethodDialog extends Dialog implements CalModOnDataSelectListener {
    Context context;

    public CalModPaymentMethodDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cal_mod_layout_dialog_select_option);
        RecyclerView optionsRecyclerView = findViewById(R.id.options_list_items);
        optionsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(optionsRecyclerView.getContext(),LinearLayoutManager.VERTICAL);
        dividerItemDecoration.setDrawable(context.getResources().getDrawable(R.drawable.cal_mod_divider_item_decoration,null));
        optionsRecyclerView.addItemDecoration(dividerItemDecoration);
        final List<String> categories = Arrays.asList(context.getResources().getStringArray(R.array.expense_methods));
        CalModDialogAdapter dialogAdapter = new CalModDialogAdapter(context, categories, this);
        dialogAdapter.notifyDataSetChanged();
        optionsRecyclerView.setAdapter(dialogAdapter);
    }

    @Override
    public void onSelect(String data, boolean ischecked) {
        dismiss();
    }
}
