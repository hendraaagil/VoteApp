package com.hendraaagil.voteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
                    login();
                } catch (IOException e) {
                    e.printStackTrace();
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

    private void login() throws IOException, JSONException {
        // URL for request
        URL url = new URL("http://vote-server-side.herokuapp.com/login");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Request method & set property like content-type, etc.
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.connect();

        // Object for POST into URL
        JSONObject object = new JSONObject();
        object.put("username", txtUsername.getText().toString());
        object.put("password", txtPassword.getText().toString());

        // Sending request
        OutputStream os = new BufferedOutputStream(connection.getOutputStream());
        os.write(object.toString().getBytes());
        os.flush();

        InputStream is = connection.getInputStream();
        System.out.println(is.toString());

        os.close();
        is.close();
    }
}