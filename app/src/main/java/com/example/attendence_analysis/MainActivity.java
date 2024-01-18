package com.example.attendence_analysis;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.backup.BackupAgentHelper;
import android.app.backup.FileBackupHelper;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
//        logins();

        //Don't Touch Below Code
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String value = editText.getText().toString();
                    int param = Integer.parseInt(value);
                    getSelect(param);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });
        //Don't touch Above Code till comment

    }

    public void getSelect(int a) {

        Cursor res = db_helper.getSelected(a);

                if (res != null && res.moveToFirst()) {
                    // Assuming you have two columns, adjust column indices accordingly
                    @SuppressLint("Range") int id = res.getInt(res.getColumnIndex("reg_no"));
                    @SuppressLint("Range") String pass = res.getString(res.getColumnIndex("password"));

                    // Format the result and display it in the TextView

                    int id_inp = Integer.parseInt(editText.getText().toString());
                    String pass_inp = editText2.getText().toString();

                    if (pass.equals(pass_inp) && id == id_inp) {
                        Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                        try {
                            Thread.sleep(1500);
                            Intent mainpage = new Intent(MainActivity.this, AfterLogin.class);
                            mainpage.putExtra("reg_no", id);
                            startActivity(mainpage);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Login Fail", Toast.LENGTH_SHORT).show();
                    }
                    // Close the cursor when done
                    res.close();
                } else {
                    Toast.makeText(getApplicationContext(), "No result found", Toast.LENGTH_SHORT).show();
                }

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
        boolean is_Inserted = db_helper.insertData("welcome3", 221003, "AnandK", 10, 8);
        if (is_Inserted)
            Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
    }

}

