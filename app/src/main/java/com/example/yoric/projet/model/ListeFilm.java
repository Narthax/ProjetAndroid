package com.example.yoric.projet.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ListeFilm {

    @SerializedName("unit")
    private Integer unit;
    @SerializedName("show_id")
    private Integer showId;
    @SerializedName("show_title")
    private String showTitle;
    @SerializedName("release_year")
    private String releaseYear;
    @SerializedName("rating")
    private String rating;
    @SerializedName("category")
    private String category;
    @SerializedName("show_cast")
    private String showCast;
    @SerializedName("director")
    private String director;
    @SerializedName("summary")
    private String summary;
    @SerializedName("poster")
    private String poster;
    @SerializedName("mediatype")
    private Integer mediatype;
    @SerializedName("runtime")
    private String runtime;

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public Integer getShowId() {
        return showId;
    }

    public void setShowId(Integer showId) {
        this.showId = showId;
    }

    public String getShowTitle() {
        return showTitle;
    }

    public void setShowTitle(String showTitle) {
        this.showTitle = showTitle;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getShowCast() {
        return showCast;
    }

    public void setShowCast(String showCast) {
        this.showCast = showCast;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Integer getMediatype() {
        return mediatype;
    }

    public void setMediatype(Integer mediatype) {
        this.mediatype = mediatype;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

}

