package com.hendraaagil.voteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LandingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnDaftar = findViewById(R.id.btnDaftar);
        TextView txtVwDate = findViewById(R.id.txtVwDate);

        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        txtVwDate.setText(date);

        btnLogin.setOnClickListener(v -> {
            startActivity(new Intent(LandingActivity.this, LoginActivity.class));
        });

        btnDaftar.setOnClickListener(v -> {
            startActivity(new Intent(LandingActivity.this, SignupActivity.class));
        });
    }
}