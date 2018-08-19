package com.agnitio.calendar.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;

import com.agnitio.calendar.R;
import com.agnitio.calendar.Utils.CalModItemDecoration;
import com.agnitio.calendar.adapters.CalModDayItemAdapter;
import com.agnitio.calendar.adapters.CalModDialogAdapter;
import com.agnitio.calendar.interfaces.CalModOnDataSelectListener;

import java.util.Arrays;
import java.util.List;

public class CalModPaymentCategoryDialog extends Dialog implements CalModOnDataSelectListener {
    Context context;

    public CalModPaymentCategoryDialog(@NonNull Context context, int myDialog) {
        super(context,myDialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cal_mod_layout_dialog_select_option);
        RecyclerView optionsRecyclerView = findViewById(R.id.options_list_items);
        LinearLayoutManager layoutManager=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        optionsRecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(optionsRecyclerView.getContext(),layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(context.getResources().getDrawable(R.drawable.cal_mod_divider_item_decoration,null));
        optionsRecyclerView.addItemDecoration(dividerItemDecoration);


        final List<String> categories = Arrays.asList(context.getResources().getStringArray(R.array.expense_categories));
        CalModDialogAdapter dialogAdapter = new CalModDialogAdapter(context, categories, this);
        dialogAdapter.notifyDataSetChanged();
        optionsRecyclerView.setAdapter(dialogAdapter);

    }

    @Override
    public void onSelect(String data, boolean isChecked) {
        dismiss();
    }
}
