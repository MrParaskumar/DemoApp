package com.example.demoapp;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Databasehelper databasehelper;
    Button adddata;
    ListView listview;
    Adapterclass adapterclass;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    EditText newname, newemail, newnumber;
    Button submit, cancel;
    ArrayList<Modalclass> modalclasses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adddata = findViewById(R.id.adddata);
        listview = findViewById(R.id.listview);
        databasehelper = new Databasehelper(MainActivity.this);

        ShowSQLiteDBdata();

        adddata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.activity_adddata);
                dialog.setCancelable(false);
                submit = (Button) dialog.findViewById(R.id.submit);
                newname = (EditText) dialog.findViewById(R.id.newname);
                newemail = (EditText) dialog.findViewById(R.id.newemail);
                newnumber = (EditText) dialog.findViewById(R.id.newnumber);
                cancel = (Button) dialog.findViewById(R.id.cancel);

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = newname.getText().toString();
                        String email = newemail.getText().toString();
                        String number = newnumber.getText().toString();

                        if (!isVallidName(name)) {
                            newname.setError("Enter Only Name");
                        } else if (!isValidEmail(email)) {
                            newemail.setError("Enter Valid Email");
                        } else if (!isValidNumber(number)) {
                            newnumber.setError("Enter Mobile Number");
                        } else {
                            boolean adddata = databasehelper.insertdata(name, number, email, null);

                            if (adddata) {
                                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(MainActivity.this, "Enter Value", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    private void ShowSQLiteDBdata() {
        sqLiteDatabase = databasehelper.getWritableDatabase();
        cursor = sqLiteDatabase.rawQuery("SELECT * FROM Profile", null);

        if (cursor.moveToFirst()) {
            do {

                modalclasses.add(new Modalclass(
                        cursor.getString(cursor.getColumnIndex(Databasehelper.Id)),
                        cursor.getString(cursor.getColumnIndex(Databasehelper.col1)),
                        cursor.getString(cursor.getColumnIndex(Databasehelper.col3)),
                        cursor.getString(cursor.getColumnIndex(Databasehelper.col2)), ""));

            } while (cursor.moveToNext());
        }

        adapterclass = new Adapterclass(modalclasses, this);
        listview.setAdapter(adapterclass);
        adapterclass.notifyDataSetChanged();
        cursor.close();
    }

    private boolean isVallidName(String name) {
        String USER_NAME = "[a-zA-Z]+";
        if (name != null && name.matches(USER_NAME)) {
            return true;
        }
        return false;
    }

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{3,})$";
        if (email != null && email.matches(EMAIL_PATTERN)) {
            return true;
        }
        return false;
    }

    private boolean isValidNumber(String num) {
        if (num != null && num.length() == 10) {
            return true;
        }
        return false;
    }
}
