package com.hendraaagil.voteapp;

public class ExampleCard {
    private String candidateId, imageUrl, ketua, wakil;
    private int number;

    public ExampleCard(String candidateId, String imageUrl, String ketua, String wakil, int number) {
        this.candidateId = candidateId;
        this.imageUrl = imageUrl;
        this.ketua = ketua;
        this.wakil = wakil;
        this.number = number;
    }

    public String getCandidateId() {
        return candidateId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getKetua() {
        return ketua;
    }

    public String getWakil() {
        return wakil;
    }

    public int getNumber() {
        return number;
    }
}
