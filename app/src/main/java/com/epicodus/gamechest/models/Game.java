package com.epicodus.gamechest.models;

/**
 * Created by CGrahamS on 12/4/16.
 */
public class Game {
    private String mName;
    private String mImage;
    private String mReleaseDate;
    private String mPlatforms;
    private String mContentRating;
    private String mSiteDetailUrl;
    private String mDeck;
    private int mId;

    public Game(String name, String image, String releaseDate, String platforms, String contentRating, String siteDetailUrl, String deck, int id) {
        this.mName = name;
        this.mImage = image;
        this.mReleaseDate = releaseDate;
        this.mPlatforms = platforms;
        this.mContentRating = contentRating;
        this.mSiteDetailUrl = siteDetailUrl;
        this.mDeck = deck;
        this.mId = id;
    }

//    Overloaded constuctor for testing
    public Game(String name, String image, String releaseDate) {
        this.mName = name;
        this.mImage = image;
        this.mReleaseDate = releaseDate;
    }

    public String getName() {
        return mName;
    }

    public String getImage() {
        return mImage;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public String getPlatforms() {
        return mPlatforms;
    }

    public String getContentRating() {
        return mContentRating;
    }

    public String getSiteDetailUrl() {
        return mSiteDetailUrl;
    }

    public String getDeck() {
        return mDeck;
    }

    public int getId() {
        return mId;
    }

}
