package com.example.demoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {

    EditText editTextEmail;
    EditText editTextPassword;
    Button buttonLogin;
    Databasehelper sqliteHelper;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sqliteHelper = new Databasehelper(LoginActivity.this);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);

        sharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);

        boolean check = sharedPreferences.getBoolean("login", false);

        if (check) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {

            buttonLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String Email = editTextEmail.getText().toString();
                    String Password = editTextPassword.getText().toString();
                    Cursor currentUser = sqliteHelper.getalldata();

                    if (!isValidEmail(Email)) {
                        editTextEmail.setError("Enter Valid Email");
                    } else if (!isValidPasswoed(Password)) {
                        editTextPassword.setError("Enter Valid Password");
                    } else {

                        if (currentUser.moveToFirst()) {
                            do {
                                String checkemail = currentUser.getString(3);
                                String checkpassword = currentUser.getString(4);

                                if (Email.equals(checkemail) && Password.equals(checkpassword)) {
                                    editor=sharedPreferences.edit();
                                    editor.putBoolean("login",true);
                                    editor.apply();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Snackbar.make(buttonLogin, "Please try again", Snackbar.LENGTH_LONG).show();
                                }
                            } while (currentUser.moveToNext());
                        }
                    }
                }
            });
        }
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
}
