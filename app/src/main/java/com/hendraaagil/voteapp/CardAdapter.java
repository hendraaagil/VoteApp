package com.hendraaagil.voteapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {
    private Context context;
    private ArrayList<ExampleCard> exampleCards;
    private OnBtnDetailClick detailClick;

    public interface OnBtnDetailClick {
        void onBtnClick(int position);
    }

    public void setOnDetailClick(OnBtnDetailClick detailClick) {
        this.detailClick = detailClick;
    }

    public CardAdapter(Context context, ArrayList<ExampleCard> exampleCards) {
        this.context = context;
        this.exampleCards = exampleCards;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.example_card, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        ExampleCard card = exampleCards.get(position);

        String imageUrl = card.getImageUrl();
        String ketua = card.getKetua();
        String wakil = card.getWakil();
        int nomor = card.getNumber();

        holder.txtVwNomor.setText(String.valueOf(nomor));
        holder.txtVwKetua.setText(ketua);
        holder.txtVwWakil.setText(wakil);
        // holder.imageView.setImageBitmap(getBitmapFromURL(imageUrl));
    }

    @Override
    public int getItemCount() {
        return exampleCards.size();
    }

    // Convert url to bitmap error, need AsyncTask
    public static Bitmap getBitmapFromURL(String imgUrl) {
        try {
            URL url = new URL(imgUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream is = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView txtVwNomor, txtVwKetua, txtVwWakil;
        public Button btnDetail;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            txtVwNomor = itemView.findViewById(R.id.txtVwNomor);
            txtVwKetua = itemView.findViewById(R.id.txtVwKetua);
            txtVwWakil = itemView.findViewById(R.id.txtVwWakil);
            btnDetail = itemView.findViewById(R.id.btnDetail);

            btnDetail.setOnClickListener(v -> {
                if (detailClick != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        detailClick.onBtnClick(position);
                    }
                }
            });
        }
    }
}
