package com.hendraaagil.voteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class VoteActivity extends AppCompatActivity {
    public String userId;
    private JSONObject user;
    private JSONArray candidates;

    private TextView txtVwHello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");

        new MyTask("http://vote-server-side.herokuapp.com/users/" + userId).execute();
        new MySecondTask("http://vote-server-side.herokuapp.com/candidates").execute();

        txtVwHello = findViewById(R.id.txtVwHello);
    }

    public class MyTask extends AsyncTask<Void, Void, String> {
        private String url;

        public MyTask(String url) {
            this.url = url;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(this.url);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("GET");

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                user = new JSONObject(response.toString());
                System.out.println(user);
            } catch (IOException | JSONException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(VoteActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            return null;
        }
    }

    public class MySecondTask extends AsyncTask<Void, Void, String> {
        private String url;

        public MySecondTask(String url) {
            this.url = url;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(this.url);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("GET");

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                candidates = new JSONArray(response.toString());
                System.out.println(candidates);
            } catch (IOException | JSONException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(VoteActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            return null;
        }
    }
}