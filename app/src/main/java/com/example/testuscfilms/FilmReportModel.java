package com.example.testuscfilms;

import java.util.List;

public class FilmReportModel {

    private int id;
    private String category;
    private String title;
    private String tagline;
    private String releaseDate;
    private float rating;
    private int runtime;
    private String genres;
    private String spokenLanguages;
    private String overview;
    private String videoKey;
    private String posterPath;
    private String backdropPath;
    private List<CastModel> castModels;
    private List<ReviewModel> reviewModels;
    private List<SliderData> recommendedItems;
    private List<FilmReportModel> searchList;

    public FilmReportModel() {
    }

    public FilmReportModel(int id, String category, String title, String tagline, String releaseDate, float rating, int runtime, String genres, String spokenLanguages, String overview, String videoKey, String posterPath, String backdropPath, List<CastModel> castModels, List<ReviewModel> reviewModels, List<SliderData> recommendedItems, List<FilmReportModel> searchList) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.tagline = tagline;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.runtime = runtime;
        this.genres = genres;
        this.spokenLanguages = spokenLanguages;
        this.overview = overview;
        this.videoKey = videoKey;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.castModels = castModels;
        this.reviewModels = reviewModels;
        this.recommendedItems = recommendedItems;
        this.searchList = searchList;
    }

    @Override
    public String toString() {
        return "FilmReportModel{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", title='" + title + '\'' +
                ", tagline='" + tagline + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", rating=" + rating +
                ", runtime=" + runtime +
                ", genres='" + genres + '\'' +
                ", spokenLanguages='" + spokenLanguages + '\'' +
                ", overview='" + overview + '\'' +
                ", videoKey='" + videoKey + '\'' +
                ", posterPath='" + posterPath + '\'' +
                ", backdropPath='" + backdropPath + '\'' +
                ", castModels=" + castModels +
                ", reviewModels=" + reviewModels +
                ", recommendedItems=" + recommendedItems +
                ", searchList=" + searchList +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getSpokenLanguages() {
        return spokenLanguages;
    }

    public void setSpokenLanguages(String spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getVideoKey() {
        return videoKey;
    }

    public void setVideoKey(String videoKey) {
        this.videoKey = videoKey;
    }

    public List<CastModel> getCastModels() {
        return castModels;
    }

    public void setCastModels(List<CastModel> castModels) {
        this.castModels = castModels;
    }

    public List<ReviewModel> getReviewModels() {
        return reviewModels;
    }

    public void setReviewModels(List<ReviewModel> reviewModels) {
        this.reviewModels = reviewModels;
    }

    public List<SliderData> getRecommendedItems() {
        return recommendedItems;
    }

    public void setRecommendedItems(List<SliderData> recommendedItems) {
        this.recommendedItems = recommendedItems;
    }

    public List<FilmReportModel> getSearchList() {
        return searchList;
    }

    public void setSearchList(List<FilmReportModel> searchList) {
        this.searchList = searchList;
    }
}
