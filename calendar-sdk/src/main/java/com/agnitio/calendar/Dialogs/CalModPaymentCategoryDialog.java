package com.agnitio.calendar.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.agnitio.calendar.R;
import com.agnitio.calendar.adapters.CalModCategoryDialogAdapter;
import com.agnitio.calendar.interfaces.CalModConstants;
import com.agnitio.calendar.interfaces.CalModOnDataSelectListener;
import com.agnitio.calendar.models.CalModCategoryList;

import java.util.List;

public class CalModPaymentCategoryDialog extends Dialog implements CalModOnDataSelectListener {
    Context context;
    String title;
    TextView txt_header_title;
    List<CalModCategoryList> categorylist;

    public CalModPaymentCategoryDialog(@NonNull Context context, int myDialog, String title, List<CalModCategoryList> categoryList) {
        super(context, myDialog);
        this.context = context;
        this.title = title;
        this.categorylist = categoryList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cal_mod_layout_dialog_select_option);
        RecyclerView optionsRecyclerView = findViewById(R.id.options_list_items);
        txt_header_title = findViewById(R.id.txt_dialog_header);
        txt_header_title.setText(title);


        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        optionsRecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(optionsRecyclerView.getContext(), layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(context.getResources().getDrawable(R.drawable.cal_mod_divider_item_decoration, null));
        optionsRecyclerView.addItemDecoration(dividerItemDecoration);
        CalModCategoryDialogAdapter dialogAdapter = new CalModCategoryDialogAdapter(context, categorylist, this);
        dialogAdapter.notifyDataSetChanged();
        optionsRecyclerView.setAdapter(dialogAdapter);

    }

    @Override
    public void onSelect(int position, String name) {
        CalModConstants.DialogConstants.CATEGORY_POSITION = position;
        dismiss();
    }
}
