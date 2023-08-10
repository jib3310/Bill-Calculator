package com.example.indass;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Percentage extends AppCompatActivity {
    private List<Double> percentageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_percentage);

        String bill = this.getIntent().getStringExtra("bill");
        String people = this.getIntent().getStringExtra("people");

        ScrollView sv = findViewById(R.id.sv1);
        LinearLayout l1 = findViewById(R.id.l1);

        int Ipeople = Integer.parseInt(people);
        double Ibill = Double.parseDouble(bill);

        //create edit text based on people
        for (int i = 0; i < Ipeople; i++) {
            EditText inputPercentage = new EditText(this);
            TextView personTitle = new TextView(this);

            personTitle.setPadding(5, 5, 5, 0);
            personTitle.setText("Person " + (i + 1) + " : ");

            inputPercentage.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            inputPercentage.setId(i);

            inputPercentage.setPadding(5, 0, 5, 5);

            l1.addView(personTitle);
            l1.addView(inputPercentage);

        }


        Button cal = new Button(this);
//        l1.addView(cal,Ipeople+Ipeople+1);
        l1.addView(cal);
        cal.setText("Calculate");

        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // add input into a arraylist
                percentageList = new ArrayList<>();
                for (int i = 0; i < Ipeople; i++) {
//            int ID = getResources().getIdentifier(Integer.toString(i),"id",getPackageName());
                    EditText ratio = findViewById(i);
                    percentageList.add(Double.parseDouble(ratio.getText().toString()));
                }

                //clear view
                l1.removeAllViews();


                //show result
                TextView receiptTitle = new TextView(Percentage.this);

                receiptTitle.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                receiptTitle.setId(R.id.textView4);
                receiptTitle.setText("Receipt");
                receiptTitle.setTextSize(34);
                receiptTitle.setTypeface(null, Typeface.BOLD);
                receiptTitle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                receiptTitle.setGravity(Gravity.CENTER);
                receiptTitle.setPadding(0, 0, 0, 10);

                l1.addView(receiptTitle);

                String storeForHistory = "";
                //show result
                for (int i = 0; i < Ipeople; i++) {
                    TextView showeach = new TextView(Percentage.this);
                    //attribute
                    showeach.setTextSize(16);
                    showeach.setGravity(Gravity.CENTER);
                    showeach.setPadding(0, 5, 0, 0);

                    double eachPayAmount = (Ibill * (percentageList.get(i) / 100));
                    String SeachPayAmount = String.format("%.2f", eachPayAmount);
                    showeach.setText(" Person " + (i + 1) + " need to pay RM " + SeachPayAmount);
                    l1.addView(showeach, (i + 1));

                    storeForHistory += "" + showeach.getText().toString() + "\n"; //store for history


                }
//                Toast.makeText(Percentage.this,storeForHistory, Toast.LENGTH_LONG).show();

                //button save history
                Button btSave = new Button(Percentage.this);
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
                l1.addView(btSave);


                String finalStoreForHistory = storeForHistory;
                btSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Percentage.this);
                        builder.setTitle(" References for this bill");

                        final EditText input = new EditText(Percentage.this);
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
                                MyDBManager dbManager = new MyDBManager(Percentage.this);
                                dbManager.open();

                                //Insert data
                                long newRowID = dbManager.insertData(userInput, Ibill, Ipeople, finalStoreForHistory);

                                dbManager.close();

//                                Intent intent = new Intent(Ratio.this, MainActivity.class);
//
//                                startActivity(intent);

                                finish();

                                Toast.makeText(Percentage.this, "Save Completed!", Toast.LENGTH_SHORT).show();

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
        });
    }
}