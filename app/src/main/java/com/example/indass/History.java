package com.example.indass;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class History extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ScrollView sv = findViewById(R.id.sv1);
        LinearLayout l1 = findViewById(R.id.l1);

        MyDBManager dbManager = new MyDBManager(this);
        dbManager.open();

        // Retrieve all data from the table
        Cursor cursor = dbManager.getAllData();

        LinearLayout l = findViewById(R.id.l1);

        if (cursor != null && cursor.moveToLast()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(MyDBManager.COLUMN_ID));
                String references = cursor.getString(cursor.getColumnIndexOrThrow(MyDBManager.COLUMN_REFERENCES));
                double amount = cursor.getDouble(cursor.getColumnIndexOrThrow(MyDBManager.COLUMN_AMOUNT));
                int people = cursor.getInt(cursor.getColumnIndexOrThrow(MyDBManager.COLUMN_PEOPLE));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(MyDBManager.COLUMN_DATE));
                String time = cursor.getString(cursor.getColumnIndexOrThrow(MyDBManager.COLUMN_TIME));
                String receipt = cursor.getString(cursor.getColumnIndexOrThrow(MyDBManager.COLUMN_RECEIPT));

                // Create a new CardView for each row of data
                CardView cv = new CardView(this);
                LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                cardParams.setMargins(8, 8, 8, 8);
                cv.setLayoutParams(cardParams);
                cv.setCardBackgroundColor(getResources().getColor(android.R.color.white));
                cv.setCardElevation(getResources().getDimension(R.dimen.card_elevation));
                cv.setRadius(getResources().getDimension(R.dimen.card_corner_radius));

                // Add an OnClickListener to the CardView
                cv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showSaveImageDialog(cv);
                    }
                });

                //create textview
                TextView h = new TextView(this);
                h.setText(" References : " + references +
                        "\n Amount       : RM " + String.format("%.2f", amount) +
                        "\n People         : " + people +
                        "\n Date            : " + date +
                        "\n Time           : " + time +
                        "\n Receipt       :\n" + receipt +
                        "\n");

                h.setPadding(5, 5, 5, 5);
                // Add the TextView to the CardView
                cv.addView(h);

                // Add the CardView to the LinearLayout
                l.addView(cv);

            } while (cursor.moveToPrevious());

            cursor.close();
        }

        dbManager.close();
    }

    private void showSaveImageDialog(CardView cardView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Save as Image?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            // Save the content of the CardView as an image
            Bitmap bitmap = createBitmapFromView(cardView);
            // Implement your code here to save the bitmap as an image
            // Get the external storage directory
            File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

            // Create a unique filename based on the current timestamp
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            String fileName = "IMG_" + timeStamp + ".jpg";

            // Create the file object
            File imageFile = new File(storageDir, fileName);

            try {
                // Create a file output stream and compress the bitmap to JPEG format
                FileOutputStream fos = new FileOutputStream(imageFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);

                // Flush and close the output stream
                fos.flush();
                fos.close();

                // Show a message indicating successful saving
                Log.d("SaveImageActivity", "Image saved successfully: " + imageFile.getAbsolutePath());

                // Update the gallery so that it appears in the gallery app immediately
                MediaScannerConnection.scanFile(this, new String[]{imageFile.getAbsolutePath()}, new String[]{"image/jpeg"}, null);
                Toast.makeText(this, "Save Successfully", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                Toast.makeText(this, "Save Failed", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            //
            dialog.dismiss();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    private Bitmap createBitmapFromView(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }
}
