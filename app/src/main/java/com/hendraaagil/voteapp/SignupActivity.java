package com.hendraaagil.voteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SignupActivity extends AppCompatActivity {
    TextView txtVwLogin;
    TextInputLayout txtLayoutFullName, txtLayoutUsername, txtLayoutPassword, txtLayoutConfirm;
    TextInputEditText txtFullName, txtUsername, txtPassword, txtConfirm;
    Button btnDaftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        txtVwLogin = findViewById(R.id.txtVwLogin);
        txtLayoutFullName = findViewById(R.id.layoutTxtFullNameSignup);
        txtLayoutUsername = findViewById(R.id.layoutTxtUsernameSignup);
        txtLayoutPassword = findViewById(R.id.layoutTxtPasswordSignup);
        txtLayoutConfirm = findViewById(R.id.layoutTxtConfirmSignup);
        txtFullName = findViewById(R.id.txtFullNameSignup);
        txtUsername = findViewById(R.id.txtUsernameSignup);
        txtPassword = findViewById(R.id.txtPasswordSignup);
        txtConfirm = findViewById(R.id.txtConfirmSignup);
        btnDaftar = findViewById(R.id.btnDaftarAct);

        txtVwLogin.setOnClickListener(v -> startActivity(new Intent(SignupActivity.this, LoginActivity.class)));

        txtFullName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!txtFullName.getText().toString().isEmpty()) {
                    txtLayoutFullName.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txtUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!txtUsername.getText().toString().isEmpty()) {
                    txtLayoutUsername.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!txtPassword.getText().toString().isEmpty()) {
                    txtLayoutPassword.setErrorEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txtConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txtConfirm.getText().toString().equals(txtPassword.getText().toString())) {
                    txtLayoutConfirm.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnDaftar.setOnClickListener(v -> {
            if (validate()) {
                new MyTask().execute();
            }
        });
    }

    private boolean validate() {
        if (txtFullName.getText().toString().isEmpty()) {
            txtLayoutFullName.setErrorEnabled(true);
            txtLayoutFullName.setError("Full Name is required!");
            return false;
        }
        if (txtUsername.getText().toString().isEmpty()) {
            txtLayoutUsername.setErrorEnabled(true);
            txtLayoutUsername.setError("Username is required!");
            return false;
        }
        if (txtPassword.getText().toString().isEmpty()) {
            txtLayoutPassword.setErrorEnabled(true);
            txtLayoutPassword.setError("Password is required!");
            return false;
        }
        if (!txtConfirm.getText().toString().equals(txtPassword.getText().toString())) {
            txtLayoutConfirm.setErrorEnabled(true);
            txtLayoutConfirm.setError("Password must be match!");
            return false;
        }
        return true;
    }

    public class MyTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url = new URL("http://vote-server-side.herokuapp.com/signup");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("POST");
                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

                JSONObject object = new JSONObject();
                object.put("fullName", txtUsername.getText().toString());
                object.put("username", txtUsername.getText().toString());
                object.put("password", txtPassword.getText().toString());

                OutputStream os = connection.getOutputStream();
                byte[] input = object.toString().getBytes("utf=8");
                os.write(input, 0, input.length);

                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
                System.out.println(br.readLine());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SignupActivity.this, "Daftar Berhasil", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SignupActivity.this, "Username sudah terdaftar", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (JSONException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SignupActivity.this, "JSON", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            return null;
        }
    }
}