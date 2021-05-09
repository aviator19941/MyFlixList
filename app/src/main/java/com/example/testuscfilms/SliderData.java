package com.example.testuscfilms;

public class SliderData {

    // image url is used to
    // store the url of image
    private int id;
    private String imgUrl;
    private String posterPath;
    private String title;
    private String category;
    private float rating;
    private boolean addToWatchlist;
    private String releaseDate;

    public SliderData() {
    }

    public SliderData(int id, String imgUrl, String title, String category) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.title = title;
        this.addToWatchlist = false;
        this.category = category;
    }

    public SliderData(int id, String imgUrl, String posterPath, String title, String category, float rating, boolean addToWatchlist, String releaseDate) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.title = title;
        this.category = category;
        this.rating = rating;
        this.addToWatchlist = addToWatchlist;
        this.releaseDate = releaseDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isAddToWatchlist() {
        return addToWatchlist;
    }

    public void setAddToWatchlist(boolean addToWatchlist) {
        this.addToWatchlist = addToWatchlist;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}

