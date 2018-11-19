package com.agnitio.calendar.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.agnitio.calendar.R;
import com.agnitio.calendar.interfaces.CalModConstants;

public class CalModAmountDialog extends Dialog {
CardView btn_add;
EditText edt_amount;
TextView txt_title;
String title;

    public CalModAmountDialog(Context context, int myDialog, String title) {
        super(context,myDialog);
        this.title=title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cal_mod_dialog_custom_amount);
        edt_amount=findViewById(R.id.edt_amount);
        btn_add=findViewById(R.id.btn_add_amount);
        txt_title=findViewById(R.id.txt_dialog_header);
        txt_title.setText(title);
        edt_amount.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));

        if (!CalModConstants.DialogConstants.PAYMENT_AMOUNT.equals("")){
            edt_amount.setText(CalModConstants.DialogConstants.PAYMENT_AMOUNT);
        }

        btn_add.setOnClickListener(view -> {
            CalModConstants.DialogConstants.PAYMENT_AMOUNT=edt_amount.getText().toString();
            dismiss();
        });
    }
}
