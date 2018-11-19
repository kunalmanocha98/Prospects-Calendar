package com.agnitio.calendar.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.agnitio.calendar.Dialogs.CalModAmountDialog;
import com.agnitio.calendar.Dialogs.CalModPaymentCategoryDialog;
import com.agnitio.calendar.Dialogs.CalModPaymentDescriptionDialog;
import com.agnitio.calendar.Dialogs.CalModPaymentMethodDialog;
import com.agnitio.calendar.Dialogs.CalModPaymentSubCategoryDialog;
import com.agnitio.calendar.R;
import com.agnitio.calendar.interfaces.CalModConstants;
import com.agnitio.calendar.models.AddEVentActivityData;
import com.agnitio.calendar.models.CalModCategoryListApi;
import com.agnitio.calendar.models.CalModCategoryandMethodResults;
import com.agnitio.calendar.retrofit.CalModApiService;
import com.agnitio.calendar.retrofit.CalModApiUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    CalModCategoryandMethodResults results;
    DatePickerDialog startdatePicker, enddatePicker;
    DatePickerDialog singledatepicker;
    TimePickerDialog singletimepicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cal_mod_activity_editor);
//      id  initialization
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


//        getdialogdata
        getdialogdata();


//      temporary data for dialogs
        temp_data = new AddEVentActivityData();

//      necesary inputs
        multipledatewrapper.setVisibility(View.GONE);
        singledatewrapper.setVisibility(View.VISIBLE);
        add_details.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#e85b36")));
        add_details.setImageDrawable(getDrawable(R.drawable.cal_mod_ic_editor_add_receipt));

//        multiple date picker
        startdatePicker = new DatePickerDialog(this, R.style.MyDatepicker, startdateSetListener, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        enddatePicker = new DatePickerDialog(this, R.style.MyDatepicker, enddateSetListener, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));


//        single date and time picker

        singledatepicker =new DatePickerDialog(this,R.style.MyDatepicker,singledateSetListener,Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        singletimepicker=new TimePickerDialog(this,R.style.MyDatepicker,singleTimeSetListener,Calendar.getInstance().get(Calendar.HOUR_OF_DAY),Calendar.getInstance().get(Calendar.MINUTE),false);
    }

    @Override
    protected void onResume() {
        super.onResume();

//        click listenser
        add_details.setOnClickListener(view -> {
            showamountdialog(true);
        });
        btn_add_image_receipt.setOnClickListener((View view) -> {
            showimageselectiondialog();
        });
        mswitch.setOnCheckedChangeListener((compoundButton, ischecked) -> {
            if (ischecked) {
                multipledatewrapper.setVisibility(View.VISIBLE);
                singledatewrapper.setVisibility(View.GONE);
            } else {
                multipledatewrapper.setVisibility(View.GONE);
                singledatewrapper.setVisibility(View.VISIBLE);
            }
        });

        payment_amount.setOnClickListener(view -> {
            showamountdialog(false);
        });
        payment_category.setOnClickListener(view -> {
            showcategorydialog(false);
        });
        payment_sub_category.setOnClickListener(view -> {
            showcategorydialog(false);
        });
        payment_method.setOnClickListener(view -> {
            showpaymentmethoddialog(false);
        });
        payment_description.setOnClickListener(view -> {
            showpaymentdescriptiondialog(false);
        });

        btn_start_date.setOnClickListener(view -> {startdatePicker.show();});
        btn_end_date.setOnClickListener(view -> {enddatePicker.show();});
        btn_date_single.setOnClickListener(view -> {singledatepicker.show();});
        btn_time_single.setOnClickListener(view -> {singletimepicker.show();});

    }

    private void showamountdialog(boolean b) {
        String title = "Enter amount";
        dialog = new CalModAmountDialog(this, R.style.MyDialog, title);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.show();
        dialog.setOnDismissListener(dialogInterface -> {
            payment_amount.setText("₹ " + CalModConstants.DialogConstants.PAYMENT_AMOUNT);
            if (b) {
                showcategorydialog(true);
            }

        });
    }

    private void showcategorydialog(boolean b) {
        CalModPaymentCategoryDialog dialog = new CalModPaymentCategoryDialog(this, R.style.MyDialog, "Select a category", results.getCategoryList());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.show();
        dialog.setOnDismissListener(dialogInterface -> {
            payment_category.setText(results.categoryList.get(CalModConstants.DialogConstants.CATEGORY_POSITION).getCategoryname());
            showsubcategorydialog(b);
        });
    }


    private void showsubcategorydialog(boolean b) {
        String title = "Select a subcategory";
        CalModPaymentSubCategoryDialog dialog = new CalModPaymentSubCategoryDialog(this, R.style.MyDialog, title, results.getCategoryList().get(CalModConstants.DialogConstants.CATEGORY_POSITION).getSubcategorylist());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.show();
        dialog.setOnDismissListener(dialogInterface -> {
            payment_sub_category.setText(results.categoryList.get(CalModConstants.DialogConstants.CATEGORY_POSITION).getSubcategorylist().get(CalModConstants.DialogConstants.SUB_CATEGORY_POSITION).getItemname());
            if (b) {
                showpaymentmethoddialog(true);
            }
        });
    }

    private void showpaymentmethoddialog(boolean b) {
        String title = "Select a Payment Method";
        CalModPaymentMethodDialog dialog = new CalModPaymentMethodDialog(this, R.style.MyDialog, title, results.getItemList());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.show();
        dialog.setOnDismissListener(dialogInterface -> {
            payment_method.setText(results.itemList.get(CalModConstants.DialogConstants.PAYMENT_METHOD_POSITION).getItemname());
            if (b) {
                showpaymentdescriptiondialog(true);
            }
        });
    }

    private void showpaymentdescriptiondialog(boolean b) {
        String title = "Add a description";
        CalModPaymentDescriptionDialog dialog = new CalModPaymentDescriptionDialog(this, R.style.MyDialog, title);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.show();
        dialog.setOnDismissListener(dialogInterface -> {
            if (!CalModConstants.DialogConstants.PAYMENT_DESCRIPTION.equals("")) {
                payment_description.setText("View");
            }
            if (b){
                add_details.setImageDrawable(getDrawable(R.drawable.cal_mod_ic_editor_time_edit));
            }
        });
    }


    private void showimageselectiondialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        View alertView = LayoutInflater.from(this).inflate(R.layout.cal_mod_layout_dialog_image_option, findViewById(R.id.image_alert_root));
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
        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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

    private void getdialogdata() {
        CalModApiService calModApiService = CalModApiUtils.getApiService();
        calModApiService.categoryresults().enqueue(new Callback<CalModCategoryListApi>() {
            @Override
            public void onResponse(Call<CalModCategoryListApi> call, Response<CalModCategoryListApi> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCode() == 101) {
                        results = response.body().getCalModCategoryandMethodResults();
                    }
                }
            }

            @Override
            public void onFailure(Call<CalModCategoryListApi> call, Throwable t) {
                Log.e("error in category list", t.getLocalizedMessage());
            }
        });


    }


    DatePickerDialog.OnDateSetListener startdateSetListener = (datePicker, year, month, day) -> {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
        cal.set(Calendar.MONTH, datePicker.getMonth());
        cal.set(Calendar.YEAR, datePicker.getYear());
        String singledate= CalModConstants.DateTimeFormatter.converttodateformat("EEE, dd MMM yy",cal.getTime());
        txt_start_date.setText(singledate);
    };


    DatePickerDialog.OnDateSetListener enddateSetListener = (datePicker, year, month, day) -> {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
        cal.set(Calendar.MONTH, datePicker.getMonth());
        cal.set(Calendar.YEAR, datePicker.getYear());
        String enddate= CalModConstants.DateTimeFormatter.converttodateformat("EEE, dd MMM yy",cal.getTime());
        txt_end_date.setText(enddate);
    };





    DatePickerDialog.OnDateSetListener singledateSetListener = (datePicker, year, month, day) -> {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
        cal.set(Calendar.MONTH, datePicker.getMonth());
        cal.set(Calendar.YEAR, datePicker.getYear());
        String startdate= CalModConstants.DateTimeFormatter.converttodateformat("EEE, dd MMM yy",cal.getTime());
        btn_date_single.setText(startdate);
    };

    TimePickerDialog.OnTimeSetListener singleTimeSetListener= (timePicker, hourofday, minute) -> {
        Calendar datetime = Calendar.getInstance();
        datetime.set(Calendar.HOUR_OF_DAY, hourofday);
        datetime.set(Calendar.MINUTE, minute);
        String singletime=CalModConstants.DateTimeFormatter.converttotimeformat("hh:mm a",datetime.getTime());
        btn_time_single.setText(singletime);
    };
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
//            CalModCategoryDialogAdapter dialogAdapter = new CalModCategoryDialogAdapter(
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
//            CalModCategoryDialogAdapter dialogAdapter = new CalModCategoryDialogAdapter(
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
