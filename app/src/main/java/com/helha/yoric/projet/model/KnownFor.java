
package com.helha.yoric.projet.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class KnownFor implements Serializable {

    @SerializedName("adult")
    private Boolean mAdult;
    @SerializedName("backdrop_path")
    private String mBackdropPath;
    @SerializedName("first_air_date")
    private String mFirstAirDate;
    @SerializedName("genre_ids")
    private List<Long> mGenreIds;
    @SerializedName("id")
    private Long mId;
    @SerializedName("media_type")
    private String mMediaType;
    @SerializedName("name")
    private String mName;
    @SerializedName("origin_country")
    private List<String> mOriginCountry;
    @SerializedName("original_language")
    private String mOriginalLanguage;
    @SerializedName("original_name")
    private String mOriginalName;
    @SerializedName("original_title")
    private String mOriginalTitle;
    @SerializedName("overview")
    private String mOverview;
    @SerializedName("popularity")
    private Double mPopularity;
    @SerializedName("poster_path")
    private String mPosterPath;
    @SerializedName("release_date")
    private String mReleaseDate;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("video")
    private Boolean mVideo;
    @SerializedName("vote_average")
    private Double mVoteAverage;
    @SerializedName("vote_count")
    private Long mVoteCount;

    public Boolean getAdult() {
        return mAdult;
    }

    public void setAdult(Boolean adult) {
        mAdult = adult;
    }

    public String getBackdropPath() {
        return mBackdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        mBackdropPath = backdropPath;
    }

    public String getFirstAirDate() {
        return mFirstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        mFirstAirDate = firstAirDate;
    }

    public List<Long> getGenreIds() {
        return mGenreIds;
    }

    public void setGenreIds(List<Long> genreIds) {
        mGenreIds = genreIds;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getMediaType() {
        return mMediaType;
    }

    public void setMediaType(String mediaType) {
        mMediaType = mediaType;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public List<String> getOriginCountry() {
        return mOriginCountry;
    }

    public void setOriginCountry(List<String> originCountry) {
        mOriginCountry = originCountry;
    }

    public String getOriginalLanguage() {
        return mOriginalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        mOriginalLanguage = originalLanguage;
    }

    public String getOriginalName() {
        return mOriginalName;
    }

    public void setOriginalName(String originalName) {
        mOriginalName = originalName;
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        mOriginalTitle = originalTitle;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        mOverview = overview;
    }

    public Double getPopularity() {
        return mPopularity;
    }

    public void setPopularity(Double popularity) {
        mPopularity = popularity;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public void setPosterPath(String posterPath) {
        mPosterPath = posterPath;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Boolean getVideo() {
        return mVideo;
    }

    public void setVideo(Boolean video) {
        mVideo = video;
    }

    public Double getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        mVoteAverage = voteAverage;
    }

    public Long getVoteCount() {
        return mVoteCount;
    }

    public void setVoteCount(Long voteCount) {
        mVoteCount = voteCount;
    }


    public KnownFor(Serie serie){
        setBackdropPath(serie.getBackdropPath());
        setFirstAirDate(serie.getFirstAirDate());
        setGenreIds(serie.getGenreIds());
        setId(serie.getId());
        setName(serie.getName());
        setOriginCountry(serie.getOriginCountry());
        setOriginalLanguage(serie.getOriginalLanguage());
        setOriginalName(serie.getOriginalName());
        setOverview(serie.getOverview());
        setPopularity(serie.getPopularity());
        setPosterPath(serie.getPosterPath());
        setVoteAverage(serie.getVoteAverage());
        setVoteCount(serie.getVoteCount());
        setMediaType("tv");
    }
    public KnownFor(Film film){
        setAdult(film.getAdult());
        setBackdropPath(film.getBackdropPath());
        setGenreIds(film.getGenreIds());
        setId(film.getId());
        setOriginalLanguage(film.getOriginalLanguage());
        setOriginalTitle(film.getOriginalTitle());
        setOverview(film.getOverview());
        setPopularity(film.getPopularity());
        setPosterPath(film.getPosterPath());
        setReleaseDate(film.getReleaseDate());
        setTitle(film.getTitle());
        setVideo(film.getVideo());
        setVoteAverage(film.getVoteAverage());
        setVoteCount(film.getVoteCount());
        setMediaType("movie");
    }

    public boolean equals(Object o){
        if(o instanceof KnownFor){
            KnownFor k = (KnownFor)o;
            return k.getId().equals(mId);
        }
        return false;
    }
}
