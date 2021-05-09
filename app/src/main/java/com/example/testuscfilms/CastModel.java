package com.example.testuscfilms;

public class CastModel {
    private String profilePath;
    private String castName;

    public CastModel() {
    }

    public CastModel(String profilePath, String castName) {
        this.profilePath = profilePath;
        this.castName = castName;
    }

    @Override
    public String toString() {
        return "CastModel{" +
                "profilePath='" + profilePath + '\'' +
                ", castName='" + castName + '\'' +
                '}';
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public String getCastName() {
        return castName;
    }

    public void setCastName(String castName) {
        this.castName = castName;
    }
}
