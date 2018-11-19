package com.agnitio.calendar.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.nfc.cardemulation.CardEmulation;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.widget.EditText;
import android.widget.TextView;
import com.agnitio.calendar.R;
import com.agnitio.calendar.interfaces.CalModConstants;

public class CalModPaymentDescriptionDialog  extends Dialog{
    TextView txt_title;
    String title;
    EditText edt_descrription;
    CardView btn_add;

    public CalModPaymentDescriptionDialog(@NonNull Context context, int themeResId,String title) {
        super(context, themeResId);
        this.title=title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cal_mod_dialog_custom_description);
        txt_title=findViewById(R.id.txt_dialog_header);
        edt_descrription=findViewById(R.id.edt_description);
        btn_add=findViewById(R.id.btn_add_description);

        txt_title.setText(title);


        if (!CalModConstants.DialogConstants.PAYMENT_DESCRIPTION.equals("")){
            edt_descrription.setText(CalModConstants.DialogConstants.PAYMENT_DESCRIPTION);
        }

        btn_add.setOnClickListener(view -> {
            CalModConstants.DialogConstants.PAYMENT_DESCRIPTION=edt_descrription.getText().toString();
            dismiss();});
    }
}
