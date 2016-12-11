package com.epicodus.gamechest.models;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CGrahamS on 12/4/16.
 */
@Parcel
public class Game {
    private String name;
    private String image;
    private String releaseDate;
    private List<String> platforms = new ArrayList<>();
    private String contentRating;
    private String siteDetailUrl;
    private String deck;
    private int id;
    private String pushId;

    public Game() {}

    public Game(String name, String image, String releaseDate, ArrayList<String> platforms, String contentRating, String siteDetailUrl, String deck, int id) {
        this.name = name;
        this.image = image;
        this.releaseDate = releaseDate;
        this.platforms = platforms;
        this.contentRating = contentRating;
        this.siteDetailUrl = siteDetailUrl;
        this.deck = deck;
        this.id = id;
    }

    public Game(String name, String image, String releaseDate, ArrayList<String> platforms,  String siteDetailUrl, String deck, int id) {
        this.name = name;
        this.image = image;
        this.releaseDate = releaseDate;
        this.platforms = platforms;
        this.siteDetailUrl = siteDetailUrl;
        this.deck = deck;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public List<String> getPlatforms() {
        return platforms;
    }

    public String getContentRating() {
        return contentRating;
    }

    public String getSiteDetailUrl() {
        return siteDetailUrl;
    }

    public String getDeck() {
        return deck;
    }

    public int getId() {
        return id;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

}
