package com.hendraaagil.voteapp;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyTask extends AsyncTask<Void, Void, String> {
    private String url;
    private JSONObject json;
    private Context context;

    public MyTask(String url, JSONObject json, Context context) {
        this.url = url;
        this.json = json;
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            URL url = new URL(this.url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");


            OutputStream os = connection.getOutputStream();
            byte[] input = this.json.toString().getBytes("utf=8");
            os.write(input, 0, input.length);

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            System.out.println(br.readLine());
            System.out.println("Berhasil");

            Toast.makeText(context, "Berhasil", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            System.out.println("Gagal");
            e.printStackTrace();
        }

        return "String";
    }
}
