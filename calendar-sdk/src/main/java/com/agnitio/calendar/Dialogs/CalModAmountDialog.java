package com.agnitio.calendar.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.agnitio.calendar.R;

public class CalModAmountDialog extends Dialog {
Button btn_add;
EditText edt_amount;

    public CalModAmountDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cal_mod_dialog_custom_amount);
        edt_amount=findViewById(R.id.edt_amount);
        btn_add=findViewById(R.id.btn_amount_add);
        btn_add.setOnClickListener(view -> {
            dismiss();
        });
    }
}
