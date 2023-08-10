package com.example.indass;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;

public class Equal extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_equal);

        TextView result = findViewById(R.id.result);
        LinearLayout l = findViewById(R.id.l1);

        String bill = this.getIntent().getStringExtra("bill");
        String people = this.getIntent().getStringExtra("people");

        int Ipeople = Integer.parseInt(people);
        double Ibill = Double.parseDouble(bill);

        double amountPay = Ibill / Ipeople;

        String SamountPay = String.format("%.2f", amountPay);

        result.setText(" Each person have to pay RM " + SamountPay);
        result.setTextSize(16);
        result.setPadding(0, 5, 0, 0);

        String storeForHistory = "";
        storeForHistory = result.getText().toString(); //store for history

        //button save history
        Button btSave = new Button(Equal.this);
        btSave.setText("Save History");
        btSave.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        // Create LayoutParams with gravity set to CENTER
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.gravity = Gravity.CENTER;

        // Set the LayoutParams to the Button
        btSave.setLayoutParams(layoutParams);
        btSave.setPadding(5, 2, 5, 2);
        l.addView(btSave);


        String finalStoreForHistory = storeForHistory;
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Equal.this);
                builder.setTitle(" References for this bill");

                final EditText input = new EditText(Equal.this);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    String userInput;
                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH) + 1; // Month starts from 0, so add 1 to get the correct month
                    int day = calendar.get(Calendar.DAY_OF_MONTH);


                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userInput = input.getText().toString();

                        //create instance database
                        MyDBManager dbManager = new MyDBManager(Equal.this);
                        dbManager.open();

                        //Insert data
                        long newRowID = dbManager.insertData(userInput, Ibill, Ipeople, finalStoreForHistory);

                        dbManager.close();

//                        Intent intent = new Intent(Equal.this, MainActivity.class);
//
//                        startActivity(intent);

                        finish();
                        Toast.makeText(Equal.this, "Save Completed!", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });


    }
}