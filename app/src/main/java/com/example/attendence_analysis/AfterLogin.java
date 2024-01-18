package com.example.attendence_analysis;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AfterLogin extends AppCompatActivity {
    TextView text1, text2, text3;
    databasehelper db_helper2;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);
        db_helper2 = new databasehelper(this);

        // Corrected initialization of TextView and Button
        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        text3 = findViewById(R.id.text3);
        int id = getIntent().getIntExtra("reg_no", 0);
        displaySpecificData(id);

    }

    private void displaySpecificData(int id) {
        Cursor cursor = db_helper2.getSpecificData(id);

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    // Assuming you have two columns, adjust column indices accordingly
                    @SuppressLint("Range") int taken = cursor.getInt(cursor.getColumnIndex("classes_taken"));
                    @SuppressLint("Range") int attended = cursor.getInt(cursor.getColumnIndex("classes_attended"));

                    // Display or process the retrieved data as needed
                    // Log.d("YourTag", "Column1: " + column1Value + ", Column2: " + column2Value);
                    Toast.makeText(getApplicationContext(), "Data", Toast.LENGTH_SHORT).show();
                    String att = "attended:" + String.valueOf(attended); // Convert int to String
                    text3.setText(att);
                    float percent = (float) attended / taken * 100;
                    String percentage = "Percentage:" + String.valueOf(percent);
                    text2.setText(String.valueOf(percentage));
                } else {
                    // Handle the case where no data is found
                    Toast.makeText(getApplicationContext(), "No result found", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                // Log the exception message
                Toast.makeText(getApplicationContext(), "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            } finally {
                // Close the cursor in the finally block to ensure it gets closed
                cursor.close();
            }
        } else {
            // Log the case where the cursor is null
            Toast.makeText(getApplicationContext(), "Cursor is null", Toast.LENGTH_SHORT).show();
        }
    }
}





