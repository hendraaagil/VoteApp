package com.hendraaagil.voteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
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
import java.util.ArrayList;

public class VoteActivity extends AppCompatActivity implements CardAdapter.OnBtnDetailClick {
    public JSONObject user;
    public JSONArray candidates;
    private TextView txtVwHello;
    public RecyclerView recyclerView;
    public CardAdapter cardAdapter;
    public ArrayList<ExampleCard> exampleCards = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);

        Intent intent = getIntent();
        String userId = intent.getStringExtra("userId");

        new MyTask("http://vote-server-side.herokuapp.com/users/" + userId).execute();
        new MySecondTask("http://vote-server-side.herokuapp.com/candidates").execute();

        txtVwHello = findViewById(R.id.txtVwHello);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onBtnClick(int position) {
        ExampleCard card = exampleCards.get(position);

        System.out.println(card.getCandidateId());
    }

    @SuppressLint("StaticFieldLeak")
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
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                user = new JSONObject(response.toString());
            } catch (IOException | JSONException e) {
                runOnUiThread(() -> Toast.makeText(VoteActivity.this, "Gagal", Toast.LENGTH_SHORT).show());
            }

            return null;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String s) {
            try {
                txtVwHello.setText("Halo, " + user.get("fullName").toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
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
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                candidates = new JSONArray(response.toString());
            } catch (IOException | JSONException e) {
                runOnUiThread(() -> Toast.makeText(VoteActivity.this, "Gagal", Toast.LENGTH_SHORT).show());
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                for (int i = 0; i < candidates.length(); i++) {
                    JSONObject candidate = candidates.getJSONObject(i);

                    String id = candidate.getString("_id");
                    String imageUrl = candidate.getString("photoLink");
                    String ketua = candidate.getString("leader");
                    String wakil = candidate.getString("coLeader");
                    int nomor = candidate.getInt("number");

                    exampleCards.add(new ExampleCard(id, imageUrl, ketua, wakil, nomor));
                }

                cardAdapter = new CardAdapter(VoteActivity.this, exampleCards);
                recyclerView.setAdapter(cardAdapter);
                cardAdapter.setOnDetailClick(VoteActivity.this);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}