package com.example.indass;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class Amount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amount);


        String bill = this.getIntent().getStringExtra("bill");
        String people = this.getIntent().getStringExtra("people");

        ScrollView sv = findViewById(R.id.sv1);
        LinearLayout l1 = findViewById(R.id.l1);

        int Ipeople = Integer.parseInt(people);
        double Ibill = Double.parseDouble(bill);

        //button
        Button bt = new Button(Amount.this);
        bt.setText("Show Receipt");
        bt.setVisibility(View.GONE);


        //result
        double amount = Ibill;
        TextView leftAmount = new TextView(this);
        leftAmount.setText("Amount left : RM " + amount);
        leftAmount.setPadding(5, 5, 5, 5);
        l1.addView(leftAmount, 1);

        //create edit text based on people
        for (int i = 0; i < Ipeople; i++) {
            EditText inputAmount = new EditText(this);
            TextView personTitle = new TextView(this);

            personTitle.setPadding(5, 5, 5, 0);
            personTitle.setText("Person " + (i + 1) + " : ");

            inputAmount.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            inputAmount.setId(i);

            inputAmount.setPadding(5, 0, 5, 5);

            l1.addView(personTitle);
            l1.addView(inputAmount);
        }

        //add button
        l1.addView(bt);


        //get amount
        for (int i = 0; i < Ipeople; i++) {
            EditText inputedAmount = findViewById(i);
            inputedAmount.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {

                    //create a list, update the list on every times input value
                    //then sum up the list and minus with the amount.
                    double[] amounts = new double[Ipeople];

                    double sum = 0.0;
                    for (int j = 0; j < Ipeople; j++) {

                        EditText aa = findViewById(j);
                        String amountText = aa.getText().toString();

                        if (!amountText.isEmpty()) {
                            amounts[j] = Double.parseDouble(amountText);
                            sum += amounts[j];
                        }
                    }
                    //bt function
                    bt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            //remove all views
                            l1.removeAllViews();

                            TextView receiptTitle = new TextView(Amount.this);

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
                                TextView showeach = new TextView(Amount.this);
                                showeach.setText(" Person " + (i + 1) + " need to pay RM " + String.format("%.2f", amounts[i]));
                                //attribute
                                showeach.setTextSize(16);
                                showeach.setGravity(Gravity.CENTER);
                                showeach.setPadding(0, 5, 0, 0);

                                l1.addView(showeach, (i + 1));

                                storeForHistory += showeach.getText().toString() + "\n"; //store for history

                            }
                            //button save history
                            Button btSave = new Button(Amount.this);
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
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Amount.this);
                                    builder.setTitle("References for this bill");

                                    final EditText input = new EditText(Amount.this);
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
                                            MyDBManager dbManager = new MyDBManager(Amount.this);
                                            dbManager.open();

                                            //Insert data
                                            long newRowID = dbManager.insertData(userInput, Ibill, Ipeople, finalStoreForHistory);

                                            dbManager.close();
//                                Intent intent = new Intent(Ratio.this, MainActivity.class);
//
//                                startActivity(intent);

                                            finish();

                                            Toast.makeText(Amount.this, "Save Completed!", Toast.LENGTH_SHORT).show();
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


                    double left = amount - sum;
                    leftAmount.setText("Amount left: RM " + String.format("%.2f", left));

                    // once the amount is zero
                    if (left == 0.0) {
                        //button visible
                        bt.setVisibility(View.VISIBLE);

                    } else {
                        //button not visible
                        leftAmount.setText("Amount left: RM " + String.format("%.2f", left) + "\n(Please enter proper amount to show receipt)");
                        bt.setVisibility(View.GONE);
                    }

                }
            });

        }


    }
}