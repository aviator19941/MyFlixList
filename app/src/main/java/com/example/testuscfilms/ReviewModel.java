package com.example.testuscfilms;

public class ReviewModel {
    private String author;
    private float rating;
    private String content;
    private String createdAt;

    public ReviewModel() {
    }

    public ReviewModel(String author, float rating, String content, String createdAt) {
        this.author = author;
        this.rating = rating;
        this.content = content;
        this.createdAt = createdAt;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
