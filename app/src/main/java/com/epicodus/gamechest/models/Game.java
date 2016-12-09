package com.epicodus.gamechest.models;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by CGrahamS on 12/4/16.
 */
@Parcel
public class Game {
    private String mName;
    private String mImage;
    private String mReleaseDate;
    private ArrayList<String> mPlatforms;
    private String mContentRating;
    private String mSiteDetailUrl;
    private String mDeck;
    private int mId;

    public Game() {}

    public Game(String name, String image, String releaseDate, ArrayList<String> platforms, String contentRating, String siteDetailUrl, String deck, int id) {
        this.mName = name;
        this.mImage = image;
        this.mReleaseDate = releaseDate;
        this.mPlatforms = platforms;
        this.mContentRating = contentRating;
        this.mSiteDetailUrl = siteDetailUrl;
        this.mDeck = deck;
        this.mId = id;
    }

    public Game(String name, String image, String releaseDate, ArrayList<String> platforms,  String siteDetailUrl, String deck, int id) {
        this.mName = name;
        this.mImage = image;
        this.mReleaseDate = releaseDate;
        this.mPlatforms = platforms;
        this.mSiteDetailUrl = siteDetailUrl;
        this.mDeck = deck;
        this.mId = id;
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

    public ArrayList<String> getPlatforms() {
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
