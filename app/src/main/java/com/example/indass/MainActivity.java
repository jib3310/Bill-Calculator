package com.example.indass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        Button bEqual = findViewById(R.id.Equal);
        Button bPercentage = findViewById(R.id.Percentage);
        Button bRatio = findViewById(R.id.Ratio);
        Button bAmount = findViewById(R.id.Amount);
        Button bHistory = findViewById(R.id.History);

        EditText inputPerson = findViewById(R.id.people);
        EditText inputBill = findViewById(R.id.bill);


        bEqual.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String inputPerson1 = inputPerson.getText().toString();
                String inputBill1 = inputBill.getText().toString();

                if (!inputPerson1.isEmpty() && !inputBill1.isEmpty()) {
                    try {
                        int numberOfPeople = Integer.parseInt(inputPerson1);
                        double billAmount = Double.parseDouble(inputBill1);
                        // Perform further calculations or actions with the inputs

                        Intent intent = new Intent(MainActivity.this, Equal.class);
                        intent.putExtra("people", inputPerson.getText().toString());
                        intent.putExtra("bill", inputBill.getText().toString());

                        startActivity(intent);

                    } catch (NumberFormatException e) {
                        Toast.makeText(MainActivity.this, "Invalid input format. Please enter valid numbers.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Please enter both number of people and bill amount.", Toast.LENGTH_SHORT).show();
                }


            }
        });


        bPercentage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String inputPerson1 = inputPerson.getText().toString();
                String inputBill1 = inputBill.getText().toString();

                if (!inputPerson1.isEmpty() && !inputBill1.isEmpty()) {
                    try {
                        int numberOfPeople = Integer.parseInt(inputPerson1);
                        double billAmount = Double.parseDouble(inputBill1);
                        // Perform further calculations or actions with the inputs

                        Intent intent = new Intent(MainActivity.this, Percentage.class);
                        intent.putExtra("people", inputPerson.getText().toString());
                        intent.putExtra("bill", inputBill.getText().toString());

                        startActivity(intent);

                    } catch (NumberFormatException e) {
                        Toast.makeText(MainActivity.this, "Invalid input format. Please enter valid numbers.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Please enter both number of people and bill amount.", Toast.LENGTH_SHORT).show();
                }


            }
        });

        bRatio.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String inputPerson1 = inputPerson.getText().toString();
                String inputBill1 = inputBill.getText().toString();

                if (!inputPerson1.isEmpty() && !inputBill1.isEmpty()) {
                    try {
                        int numberOfPeople = Integer.parseInt(inputPerson1);
                        double billAmount = Double.parseDouble(inputBill1);
                        // Perform further calculations or actions with the inputs

                        Intent intent = new Intent(MainActivity.this, Ratio.class);
                        intent.putExtra("people", inputPerson.getText().toString());
                        intent.putExtra("bill", inputBill.getText().toString());

                        startActivity(intent);

                    } catch (NumberFormatException e) {
                        Toast.makeText(MainActivity.this, "Invalid input format. Please enter valid numbers.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Please enter both number of people and bill amount.", Toast.LENGTH_SHORT).show();
                }


            }
        });


        bAmount.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String inputPerson1 = inputPerson.getText().toString();
                String inputBill1 = inputBill.getText().toString();

                if (!inputPerson1.isEmpty() && !inputBill1.isEmpty()) {
                    try {
                        int numberOfPeople = Integer.parseInt(inputPerson1);
                        double billAmount = Double.parseDouble(inputBill1);
                        // Perform further calculations or actions with the inputs

                        Intent intent = new Intent(MainActivity.this, Amount.class);
                        intent.putExtra("people", inputPerson.getText().toString());
                        intent.putExtra("bill", inputBill.getText().toString());

                        startActivity(intent);

                    } catch (NumberFormatException e) {
                        Toast.makeText(MainActivity.this, "Invalid input format. Please enter valid numbers.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Please enter both number of people and bill amount.", Toast.LENGTH_SHORT).show();
                }


            }
        });

        bHistory.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, History.class);
                startActivity(intent);
            }

        });


    }

}