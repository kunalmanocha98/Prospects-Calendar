package com.agnitio.calendar.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.agnitio.calendar.R;
import com.agnitio.calendar.activities.CalModActivityAddEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CalModCustomDialog extends Dialog implements View.OnClickListener {
    RelativeLayout btn_next_date, btn_previous_date;
    TextView btn_add, btn_cancel, txt_date;
    Date date;
    DialognxtListener listener;

    public CalModCustomDialog(@NonNull Context context, int resid, Date date, DialognxtListener dialognxtListener) {
        super(context, resid);
        this.date = date;
        this.listener = dialognxtListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.cal_mod_dialog_custom_calendar);
        btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(this);
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(this);
        btn_next_date = findViewById(R.id.btn_next_dialog);
        btn_next_date.setOnClickListener(this);
        btn_previous_date = findViewById(R.id.btn_previous_dialog);
        btn_previous_date.setOnClickListener(this);
        txt_date = findViewById(R.id.txt_date_dialog);
        setdate();
    }

    private void setdate() {
        String day = (String) DateFormat.format("dd", date);
//        String dayNumberSuffix = getDayNumberSuffix(Integer.parseInt(day));
        SimpleDateFormat dateFormat = new SimpleDateFormat(" dd MMMM yyyy");
        String currentdate = dateFormat.format(date);
        txt_date.setText(currentdate);
    }


    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btn_add) {
            Intent intent = new Intent(getContext(), CalModActivityAddEvent.class);
            getContext().startActivity(intent);
            dismiss();
        } else if (view.getId() == R.id.btn_cancel) {
            dismiss();

        } else if (view.getId() == R.id.btn_next_dialog) {
            listener.onnextclick(this, date);

        } else if (view.getId() == R.id.btn_previous_dialog) {
            listener.onpreviousclick(this, date);

        } else {

        }
    }

    public interface DialognxtListener {
        void onnextclick(CalModCustomDialog dialog, Date date);

        void onpreviousclick(CalModCustomDialog dialog, Date date);
    }
}
