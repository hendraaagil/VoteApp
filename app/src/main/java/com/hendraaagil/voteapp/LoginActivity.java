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
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {
    TextView txtVwDaftar;
    TextInputLayout txtLayoutUsername, txtLayoutPassword;
    TextInputEditText txtUsername, txtPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtVwDaftar = findViewById(R.id.txtVwDaftar);
        txtLayoutUsername = findViewById(R.id.layoutTxtUsernameLogin);
        txtLayoutPassword = findViewById(R.id.layoutTxtPasswordLogin);
        txtUsername = findViewById(R.id.txtUsernameLogin);
        txtPassword = findViewById(R.id.txtPasswordLogin);
        btnLogin = findViewById(R.id.btnLoginAct);

        txtVwDaftar.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, SignupActivity.class)));

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
                    txtLayoutPassword.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnLogin.setOnClickListener(v -> {
            if (validate()) {
                try {
                    JSONObject object = new JSONObject();
                    object.put("username", txtUsername.getText().toString());
                    object.put("password", txtPassword.getText().toString());

                    new MyTask("http://vote-server-side.herokuapp.com/login", object).execute();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean validate() {
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
        return true;
    }

    public class MyTask extends AsyncTask<Void, Void, Void> {
        private String url;
        private JSONObject json;

        public MyTask(String url, JSONObject json) {
            this.url = url;
            this.json = json;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url = new URL(this.url);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

                OutputStream os = connection.getOutputStream();
                byte[] input = this.json.toString().getBytes("utf=8");
                os.write(input, 0, input.length);

                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));

                JSONTokener jsonTokener = new JSONTokener(br.readLine());
                JSONObject jsonObject = new JSONObject(jsonTokener);

                String userId = jsonObject.get("user").toString();

                System.out.println(userId);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this, "Login Berhasil", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(LoginActivity.this, VoteActivity.class);
                        intent.putExtra("userId", userId);
                        startActivity(intent);
                    }
                });
            } catch (IOException | JSONException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this, "Username dan Password Tidak Cocok", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            return null;
        }
    }
}