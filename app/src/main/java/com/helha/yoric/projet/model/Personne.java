
package com.helha.yoric.projet.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Personne {

    @SerializedName("adult")
    private Boolean mAdult;
    @SerializedName("id")
    private Long mId;
    @SerializedName("known_for")
    private List<KnownFor> mKnownFor;
    @SerializedName("name")
    private String mName;
    @SerializedName("popularity")
    private Double mPopularity;
    @SerializedName("profile_path")
    private String mProfilePath;

    public Boolean getAdult() {
        return mAdult;
    }

    public void setAdult(Boolean adult) {
        mAdult = adult;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public List<KnownFor> getKnownFor() {
        return mKnownFor;
    }

    public void setKnownFor(List<KnownFor> knownFor) {
        mKnownFor = knownFor;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Double getPopularity() {
        return mPopularity;
    }

    public void setPopularity(Double popularity) {
        mPopularity = popularity;
    }

    public String getProfilePath() {
        return mProfilePath;
    }

    public void setProfilePath(String profilePath) {
        mProfilePath = profilePath;
    }


    public Personne(Boolean mAdult, Long mId, List<KnownFor> mKnownFor, String mName, Double mPopularity, String mProfilePath) {
        this.mAdult = mAdult;
        this.mId = mId;
        this.mKnownFor = mKnownFor;
        this.mName = mName;
        this.mPopularity = mPopularity;
        this.mProfilePath = mProfilePath;
    }
}
