package com.example.attendence_analysis;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    databasehelper db_helper;
    EditText editText, editText2;
    Button btn, btn2;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db_helper = new databasehelper(this);
        btn2 = findViewById(R.id.button2);
        btn = findViewById(R.id.button);
        editText = findViewById(R.id.editTextText);
        editText2 = findViewById(R.id.editTextText2);
        txt = findViewById(R.id.textview);
        addData();
        viewAllData();
        logins();
    }

    public void logins() {
        btn2 = findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = db_helper.getAllData();
                if (res.getCount() == 0) {
                    //show message
                    return;
                }
                while (res.moveToNext()) {
                    String password = res.getString(1);
                    String inp_password = editText2.getText().toString();
                    String id = res.getString(0);
                    String id_input = editText.getText().toString();
                    if (password.equals(inp_password) && id.equals(id_input)) {
                        Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Login Fail", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    public void viewAllData() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = db_helper.getAllData();
                if (res.getCount() == 0) {
                    //show message
                    showMessage("Error", "Empty");
                    return;
                }
                StringBuilder buffer = new StringBuilder();
                while (res.moveToNext()) {
                    buffer.append("Id : " + res.getString(0) + "\n");
                    buffer.append("Pass : " + res.getString(1) + "\n");
                    txt.setText(res.getString(0));
                }

                showMessage("Data", buffer.toString());


            }
        });

    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    public void addData() {
        boolean is_Inserted = db_helper.insertData("welcome1", 221002);
        if (is_Inserted)
            Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
    }

}