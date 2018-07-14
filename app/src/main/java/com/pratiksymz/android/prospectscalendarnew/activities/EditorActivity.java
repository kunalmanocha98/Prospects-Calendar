package com.pratiksymz.android.prospectscalendarnew.activities;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.pratiksymz.android.prospectscalendarnew.R;
import com.pratiksymz.android.prospectscalendarnew.adapters.DialogAdapter;
import com.pratiksymz.android.prospectscalendarnew.interfaces.DataTransferInterface;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditorActivity extends AppCompatActivity implements DataTransferInterface {
    @BindView(R.id.image_view_receipt)
    ImageView mExpenseReceipt;

    @BindView(R.id.fab_add_image)
    FloatingActionButton mFABAddReceipt;

    @BindView(R.id.editor_date_view)
    TextView mEditorDate;

    @BindView(R.id.view_picked_time)
    TextView mTextPickedTime;

    @BindView(R.id.fab_time_picker)
    FloatingActionButton mFABTimePicker;

    @BindView(R.id.button_select_pay_meth)
    Button mButtonSelectPaymentMethod;

    @BindView(R.id.button_select_category)
    Button mButtonSelectCategory;

    @BindView(R.id.amount_text_value)
    EditText mTextExpenseValue;

    @BindView(R.id.description_text)
    EditText mTextDescription;

    @BindView(R.id.button_submit_data)
    Button mButtonSubmitData;

    /**
     * Log Tag
     */
    private static final String LOG_TAG = EditorActivity.class.getSimpleName();

    // --------------------------------------- FIELDS ----------------------------------------------
    /**
     * Field to store the selected payment method
     */
    private String mExpensePaymentMethod = null;

    /**
     * Field to store the selected expense category
     */
    private String mExpenseCategory = null;

    /**
     * Field to store the amount spent
     */
    private int mExpenseAmount = -1;

    /**
     * Field to store relevant information to the expense
     */
    private String mExpenseContents = null;

    /**
     * Field to store the expense receipt
     */
    private Uri mExpenseReceiptUri = null;

    /**
     * Field to store the receipt download url
     */
    private Uri mExpenseReceiptDownloadUrl = null;

    // -------------------------------- FORMATS & REQUEST ID's -------------------------------------
    /**
     * Request ID to open gallery
     */
    private static final int REQUEST_OPEN_GALLERY_ID = 100;

    /**
     * Request ID to open camera
     */
    private static final int REQUEST_IMAGE_CAPTURE_ID = 101;

    /**
     * Format to display and save the current date
     */
    private static final String DATE_FORMAT = "dd MMM, yy";

    /**
     * Format to display the current time
     */
    private static final String TIME_FORMAT = "HH:mm";

    // -------------------------------- COMPONENTS & CLASSES ---------------------------------------
    /**
     * AlertDialog Builder to build the custom alert dialog layout
     */
    private AlertDialog.Builder mAlertDialogBuilder;

    /**
     * AlertDialog to display the items/options to be selected
     */
    private AlertDialog mAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        ButterKnife.bind(this);

        // Set a listener for selecting the receipt image
        mFABAddReceipt.setOnClickListener(view -> {
            runOnUiThread(() -> {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                LayoutInflater layoutInflater = LayoutInflater.from(this);
                View alertView = layoutInflater.inflate(
                        R.layout.layout_dialog_image_option,
                        findViewById(R.id.image_alert_root)
                );

                alertBuilder.setView(alertView);

                mAlertDialog = alertBuilder.create();

                ImageButton openGalleryButton = alertView.findViewById(R.id.button_select_from_gallery);
                openGalleryButton.setOnClickListener(view1 -> openGallery());

                ImageButton launchCameraButton = alertView.findViewById(R.id.button_launch_camera);
                launchCameraButton.setOnClickListener(view2 -> launchCamera());
            });

            mAlertDialog.show();
        });

// ------------------------------------ DATE DATA --------------------------------------------------
        // Set the current date for the editor
        String date = getIntent().getStringExtra("current_date");
        Log.d(LOG_TAG, "Today's Date: " + date);
        mEditorDate.setText(date);

// ------------------------------------ TIME DATA --------------------------------------------------
        // Set the current time
        Calendar currentCalendar = Calendar.getInstance();
        int hour = currentCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = currentCalendar.get(Calendar.MINUTE);

        String currentTime = hour + ":" + minute;
        mTextPickedTime.setText(currentTime);

        // Keep updating te time until the user changes it, then stop updating
        Thread timeUpdateThread = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(() -> {
                            Calendar updatedCalendar = Calendar.getInstance();
                            int hour = updatedCalendar.get(Calendar.HOUR_OF_DAY);
                            int minute = updatedCalendar.get(Calendar.MINUTE);

                            String updatedTime = hour + ":" + minute;
                            mTextPickedTime.setText(updatedTime);
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        timeUpdateThread.start();

        // Add listener for the time picker to select a custom time
        mFABTimePicker.setOnClickListener(view -> {
            runOnUiThread(() -> {
                TimePickerDialog mTimePicker = new TimePickerDialog(this,
                        (timePicker, selectedHour, selectedMinute) -> {
                            timeUpdateThread.interrupt();
                            String formattedHour = String.valueOf(selectedHour),
                                    formattedMinute = String.valueOf(selectedMinute);

                            if (selectedHour < 10 && selectedHour >= 0) {
                                formattedHour = "0" + formattedHour;
                            }

                            if (selectedMinute >= 0 && selectedMinute < 10) {
                                formattedMinute = "0" + formattedMinute;
                            }

                            String selectedTime = formattedHour + ":" + formattedMinute;
                            mTextPickedTime.setText(selectedTime);
                        },
                        hour, minute, true);
                mTimePicker.show();
            });
        });

// -------------------------------------------------------------------------------------------------

        mButtonSelectPaymentMethod.setOnClickListener(view -> {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View alertView = inflater.inflate(
                    R.layout.layout_dialog_select_option,
                    findViewById(R.id.options_dialog_root)
            );

            // Find the Recycler View
            RecyclerView optionsRecyclerView = alertView.findViewById(R.id.options_list_items);
            optionsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            List<String> paymentMethods = Arrays.asList(getResources().getStringArray(R.array.payment_methods));

            // Create the adapter
            DialogAdapter dialogAdapter = new DialogAdapter(
                    this, paymentMethods, this
            );
            dialogAdapter.notifyDataSetChanged();

            // Set the adapter
            optionsRecyclerView.setAdapter(dialogAdapter);
            alertBuilder.setView(alertView);

            mAlertDialog = alertBuilder.create();
            mAlertDialog.show();
        });

        mButtonSelectCategory.setOnClickListener((View view) -> {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View alertView = inflater.inflate(
                    R.layout.layout_dialog_select_option,
                    findViewById(R.id.options_dialog_root)
            );

            // Find the Recycler View
            RecyclerView optionsRecyclerView = alertView.findViewById(R.id.options_list_items);
            optionsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            final List<String> categories = Arrays.asList(getResources().getStringArray(R.array.expense_categories));

            // Create the adapter
            DialogAdapter dialogAdapter = new DialogAdapter(
                    this, categories, this
            );
            dialogAdapter.notifyDataSetChanged();

            // Set the adapter
            optionsRecyclerView.setAdapter(dialogAdapter);
            alertBuilder.setView(alertView);

            mAlertDialog = alertBuilder.create();
            mAlertDialog.show();
        });
    }

    private void openGallery() {
        mAlertDialog.dismiss();
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
    public void sendData(String selectedOption, boolean isAPaymentMethod) {
        if (isAPaymentMethod) {
            mExpensePaymentMethod = selectedOption;
            if (mAlertDialog.isShowing()) mAlertDialog.dismiss();

            mButtonSelectPaymentMethod.setText(selectedOption);
        } else {
            mExpenseCategory = selectedOption;
            if (mAlertDialog.isShowing()) mAlertDialog.dismiss();

            mButtonSelectCategory.setText(selectedOption);
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
            mExpenseReceipt.setImageBitmap(receiptBitmapCompressed);
        } else if (requestCode == REQUEST_IMAGE_CAPTURE_ID && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            // Compress the bitmap
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();

            Bitmap receiptBitmapCompressed = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            mExpenseReceipt.setImageBitmap(receiptBitmapCompressed);
            mAlertDialog.dismiss();

        }
    }
}
