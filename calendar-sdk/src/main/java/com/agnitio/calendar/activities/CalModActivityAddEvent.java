package com.agnitio.calendar.activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.agnitio.calendar.Dialogs.CalModAmountDialog;
import com.agnitio.calendar.Dialogs.CalModPaymentCategoryDialog;
import com.agnitio.calendar.Dialogs.CalModPaymentMethodDialog;
import com.agnitio.calendar.Dialogs.CalModPaymentSubCategoryDialog;
import com.agnitio.calendar.R;
import com.agnitio.calendar.models.AddEVentActivityData;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CalModActivityAddEvent extends AppCompatActivity {
    CardView btn_add_image_receipt, btn_submit_details;
    ImageView imageView;
    Switch mswitch;
    RelativeLayout singledatewrapper, multipledatewrapper;
    TextView btn_date_single, btn_time_single, txt_start_date, txt_end_date;
    RelativeLayout btn_start_date, btn_end_date;
    FloatingActionButton add_details;
    TextView payment_amount, payment_category, payment_sub_category, payment_method, payment_description;
    AlertDialog alertDialog;
    int REQUEST_OPEN_GALLERY_ID = 101;
    int REQUEST_IMAGE_CAPTURE_ID = 102;
    Uri mExpenseReceiptUri;
    AddEVentActivityData temp_data;
    CalModAmountDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cal_mod_activity_editor);
//        initialization
        imageView = findViewById(R.id.imgv_receipt);
        btn_add_image_receipt = findViewById(R.id.btn_add_receipt);
        mswitch = findViewById(R.id.mswitch);
        singledatewrapper = findViewById(R.id.singledate_wrapper);
        multipledatewrapper = findViewById(R.id.multiple_date_wrapper);
        btn_date_single = findViewById(R.id.txt_date_single);
        btn_time_single = findViewById(R.id.txt_time_single);
        btn_start_date = findViewById(R.id.btn_start_date);
        btn_end_date = findViewById(R.id.btn_end_date);
        txt_start_date = findViewById(R.id.txt_start_date);
        txt_end_date = findViewById(R.id.txt_end_date);
        add_details = findViewById(R.id.fab_add_deatils);
        payment_amount = findViewById(R.id.txt_payment_amount);
        payment_category = findViewById(R.id.txt_payment_category);
        payment_sub_category = findViewById(R.id.txt_payment_sub_category);
        payment_method = findViewById(R.id.txt_payment_method);
        payment_description = findViewById(R.id.txt_payment_description);
        btn_submit_details = findViewById(R.id.btn_submit);
        temp_data=new AddEVentActivityData();

//        necessary before loading

        multipledatewrapper.setVisibility(View.GONE);
        singledatewrapper.setVisibility(View.VISIBLE);
        add_details.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#e85b36")));
        add_details.setImageDrawable(getDrawable(R.drawable.cal_mod_ic_editor_add_receipt));

//        click listenser
        add_details.setOnClickListener(view -> {
            showamountdialog();});
        btn_add_image_receipt.setOnClickListener((View view) -> {showimageselectiondialog();});
        mswitch.setOnCheckedChangeListener((compoundButton, ischecked) -> {
            if (ischecked){
                multipledatewrapper.setVisibility(View.VISIBLE);
                singledatewrapper.setVisibility(View.GONE);
            }else{
                multipledatewrapper.setVisibility(View.GONE);
                singledatewrapper.setVisibility(View.VISIBLE);
            }
        });

    }

    private void showamountdialog() {
        dialog = new CalModAmountDialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.show();
        dialog.setOnDismissListener(dialogInterface -> { showcategorydialog();});
    }

    private void showcategorydialog() {
        CalModPaymentCategoryDialog dialog=new CalModPaymentCategoryDialog(this,R.style.MyDialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.show();
        dialog.setOnDismissListener(dialogInterface -> { showsubcategorydialog();});
    }


    private void showsubcategorydialog() {
        CalModPaymentSubCategoryDialog dialog=new CalModPaymentSubCategoryDialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.show();
        dialog.setOnDismissListener(dialogInterface -> { showpaymentmethoddialog();});
    }

    private void showpaymentmethoddialog() {
        CalModPaymentMethodDialog dialog=new CalModPaymentMethodDialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.show();
    }

    private void showimageselectiondialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View alertView = layoutInflater.inflate(R.layout.cal_mod_layout_dialog_image_option, findViewById(R.id.image_alert_root));
        alertBuilder.setView(alertView);
        alertDialog = alertBuilder.create();
        alertDialog.show();
        ImageButton openGalleryButton = alertView.findViewById(R.id.button_select_from_gallery);
        openGalleryButton.setOnClickListener(view1 -> openGallery());

        ImageButton launchCameraButton = alertView.findViewById(R.id.button_launch_camera);
        launchCameraButton.setOnClickListener(view2 -> launchCamera());
    }

    private void openGallery() {
        alertDialog.dismiss();
        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        );

        startActivityForResult(openGalleryIntent, REQUEST_OPEN_GALLERY_ID);
    }

    private void launchCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE_ID);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_OPEN_GALLERY_ID && resultCode == RESULT_OK && data != null) {
            mExpenseReceiptUri = data.getData();
            Bitmap receiptBitmap = null;

            try {
                receiptBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mExpenseReceiptUri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Compress the bitmap
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            receiptBitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();

            Bitmap receiptBitmapCompressed = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
           imageView.setImageBitmap(receiptBitmapCompressed);
        } else if (requestCode == REQUEST_IMAGE_CAPTURE_ID && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            // Compress the bitmap
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();

            Bitmap receiptBitmapCompressed = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            imageView.setImageBitmap(receiptBitmapCompressed);
            alertDialog.dismiss();

        }
    }
}



















































//        mExpenseReceipt=findViewById(R.id.image_view_receipt);
//        mFABAddReceipt=findViewById(R.id.fab_add_image);
//        mEditorDate=findViewById(R.id.editor_date_view);
//        mTextPickedTime=findViewById(R.id.view_picked_time);
//        mFABTimePicker=findViewById(R.id.fab_time_picker);
//        mButtonSelectPaymentMethod=findViewById(R.id.button_select_pay_meth);
//        mTextExpenseValue=findViewById(R.id.amount_text_value);
//        mTextDescription=findViewById(R.id.description_text);
//        mButtonSubmitData=findViewById(R.id.button_submit_data);
//        mButtonSelectCategory=findViewById(R.id.button_select_category);
//
//        // Set a listener for selecting the receipt image
//        mFABAddReceipt.setOnClickListener(view -> {
//            runOnUiThread(() -> {
//                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
//                LayoutInflater layoutInflater = LayoutInflater.from(this);
//                View alertView = layoutInflater.inflate(
//                        R.layout.cal_mod_layout_dialog_image_option,
//                        findViewById(R.id.image_alert_root)
//                );
//
//                alertBuilder.setView(alertView);
//
//                mAlertDialog = alertBuilder.create();
//
//                ImageButton openGalleryButton = alertView.findViewById(R.id.button_select_from_gallery);
//                openGalleryButton.setOnClickListener(view1 -> openGallery());
//
//                ImageButton launchCameraButton = alertView.findViewById(R.id.button_launch_camera);
//                launchCameraButton.setOnClickListener(view2 -> launchCamera());
//            });
//
//            mAlertDialog.show();
//        });
//
//// ------------------------------------ DATE DATA --------------------------------------------------
//        // Set the current date for the editor
//        String date = getIntent().getStringExtra("current_date");
//        Log.d(LOG_TAG, "Today's Date: " + date);
//        mEditorDate.setText(date);
//
//// ------------------------------------ TIME DATA --------------------------------------------------
//        // Set the current time
//        Calendar currentCalendar = Calendar.getInstance();
//        int hour = currentCalendar.get(Calendar.HOUR_OF_DAY);
//        int minute = currentCalendar.get(Calendar.MINUTE);
//
//        String currentTime = hour + ":" + minute;
//        mTextPickedTime.setText(currentTime);
//
//        // Keep updating te time until the user changes it, then stop updating
//        Thread timeUpdateThread = new Thread() {
//            @Override
//            public void run() {
//                try {
//                    while (!isInterrupted()) {
//                        Thread.sleep(1000);
//                        runOnUiThread(() -> {
//                            Calendar updatedCalendar = Calendar.getInstance();
//                            int hour = updatedCalendar.get(Calendar.HOUR_OF_DAY);
//                            int minute = updatedCalendar.get(Calendar.MINUTE);
//
//                            String updatedTime = hour + ":" + minute;
//                            mTextPickedTime.setText(updatedTime);
//                        });
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//
//        timeUpdateThread.start();
//
//        // Add listener for the time picker to select a custom time
//        mFABTimePicker.setOnClickListener(view -> {
//            runOnUiThread(() -> {
//                TimePickerDialog mTimePicker = new TimePickerDialog(this,
//                        (timePicker, selectedHour, selectedMinute) -> {
//                            timeUpdateThread.interrupt();
//                            String formattedHour = String.valueOf(selectedHour),
//                                    formattedMinute = String.valueOf(selectedMinute);
//
//                            if (selectedHour < 10 && selectedHour >= 0) {
//                                formattedHour = "0" + formattedHour;
//                            }
//
//                            if (selectedMinute >= 0 && selectedMinute < 10) {
//                                formattedMinute = "0" + formattedMinute;
//                            }
//
//                            String selectedTime = formattedHour + ":" + formattedMinute;
//                            mTextPickedTime.setText(selectedTime);
//                        },
//                        hour, minute, true);
//                mTimePicker.show();
//            });
//        });
//
//// -------------------------------------------------------------------------------------------------
//
//        mButtonSelectPaymentMethod.setOnClickListener(view -> {
//            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
//            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View alertView = inflater.inflate(
//                    R.layout.cal_mod_layout_dialog_select_option,
//                    findViewById(R.id.options_dialog_root)
//            );
//
//            // Find the Recycler View
//            RecyclerView optionsRecyclerView = alertView.findViewById(R.id.options_list_items);
//            optionsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//            List<String> paymentMethods = Arrays.asList(getResources().getStringArray(R.array.payment_methods));
//
//            // Create the adapter
//            CalModDialogAdapter dialogAdapter = new CalModDialogAdapter(
//                    this, paymentMethods, this
//            );
//            dialogAdapter.notifyDataSetChanged();
//
//            // Set the adapter
//            optionsRecyclerView.setAdapter(dialogAdapter);
//            alertBuilder.setView(alertView);
//
//            mAlertDialog = alertBuilder.create();
//            mAlertDialog.show();
//        });
//
//        mButtonSelectCategory.setOnClickListener((View view) -> {
//            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
//            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View alertView = inflater.inflate(
//                    R.layout.cal_mod_layout_dialog_select_option,
//                    findViewById(R.id.options_dialog_root)
//            );
//
//            // Find the Recycler View
//            RecyclerView optionsRecyclerView = alertView.findViewById(R.id.options_list_items);
//            optionsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//            final List<String> categories = Arrays.asList(getResources().getStringArray(R.array.expense_categories));
//
//            // Create the adapter
//            CalModDialogAdapter dialogAdapter = new CalModDialogAdapter(
//                    this, categories, this
//            );
//            dialogAdapter.notifyDataSetChanged();
//
//            // Set the adapter
//            optionsRecyclerView.setAdapter(dialogAdapter);
//            alertBuilder.setView(alertView);
//
//            mAlertDialog = alertBuilder.create();
//            mAlertDialog.show();
//        });
//}
//

//
//    @Override
//    public void sendData(String selectedOption, boolean isAPaymentMethod) {
//        if (isAPaymentMethod) {
//            mExpensePaymentMethod = selectedOption;
//            if (mAlertDialog.isShowing()) mAlertDialog.dismiss();
//
////            mButtonSelectPaymentMethod.setText(selectedOption);
//        } else {
//            mExpenseCategory = selectedOption;
//            if (mAlertDialog.isShowing()) mAlertDialog.dismiss();
//
////            mButtonSelectCategory.setText(selectedOption);
//        }
//    }
//
