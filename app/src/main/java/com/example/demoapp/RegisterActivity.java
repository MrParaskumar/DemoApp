package com.example.demoapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText editTextUserName;
    EditText editTextEmail;
    EditText editTextPassword;
    EditText editTextUserNumber;
    EditText editTextconformPassword1;
    Button buttonRegister;
    Databasehelper sqliteHelper;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextUserName = findViewById(R.id.editTextUserName1);
        editTextUserNumber = findViewById(R.id.editTextUserNumber1);
        editTextEmail = findViewById(R.id.editTextEmail1);
        editTextPassword = findViewById(R.id.editTextPassword1);
        editTextconformPassword1 = findViewById(R.id.editTextconformPassword1);
        buttonRegister = findViewById(R.id.buttonRegister1);
        sqliteHelper = new Databasehelper(getApplicationContext());

        sharedPreferences = getSharedPreferences("Login",MODE_PRIVATE);

        boolean check=sharedPreferences.getBoolean("login",false);

        if (check){
            Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }else {

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextUserName.getText().toString();
                String email = editTextEmail.getText().toString();
                String numbers = editTextUserNumber.getText().toString();
                String password = editTextPassword.getText().toString();
                String copassword = editTextconformPassword1.getText().toString();

                if (!isVallidName(name)) {
                    editTextUserName.setError("Enter Only Name");
                } else if (!isValidNumber(numbers)) {
                    editTextUserNumber.setError("Enter Mobile Number");
                } else if (!isValidEmail(email)) {
                    editTextEmail.setError("Enter Valid Email");
                } else if (!isValidPasswoed(password)) {
                    editTextPassword.setError("Enter 6 Password");
                } else if (!isValidConpassword(copassword)) {
                    editTextconformPassword1.setError("Enter Valid Conforlpassword");
                } else {
                    boolean adddata = sqliteHelper.insertdata(name, numbers, email, password);

                    if (adddata) {
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Enter Value", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        }
    }

    private boolean isVallidName(String name) {
        String USER_NAME = "[a-zA-Z]+";
        if (name != null && name.matches(USER_NAME)) {
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

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{3,})$";
        if (email != null && email.matches(EMAIL_PATTERN)) {
            return true;
        }
        return false;
    }

    private boolean isValidPasswoed(String password) {
        if (password != null && password.length() == 6) {
            return true;
        }
        return false;
    }

    private boolean isValidConpassword(String conpassword) {
        String password = editTextPassword.getText().toString();
        if (conpassword != null && conpassword.equals(password)) {
            return true;
        }
        return false;
    }
}
