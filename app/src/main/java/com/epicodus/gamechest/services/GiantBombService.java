package com.epicodus.gamechest.services;

import android.util.Log;

import com.epicodus.gamechest.Constants;
import com.epicodus.gamechest.models.Game;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by CGrahamS on 12/4/16.
 */
public class GiantBombService {
    public static final String TAG = GiantBombService.class.getSimpleName();

    public static void searchForGame(String game, Callback callback) {

        OkHttpClient client = new OkHttpClient.Builder().build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.GIANT_BOMB_BASE_URL).newBuilder();

        urlBuilder
                .addPathSegments("search/")
                .addQueryParameter("api_key", Constants.GIANT_BOMB_API_KEY)
                .addQueryParameter("format", "json")
                .addQueryParameter("query", game)
                .addQueryParameter("resources", "game");
        String gameSearchUrl = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(gameSearchUrl)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public static void browseGames(String filterField1, String filterValue1, Callback callback) {

        OkHttpClient client = new OkHttpClient.Builder().build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.GIANT_BOMB_BASE_URL).newBuilder();

        urlBuilder
                .addPathSegments("games/")
                .addQueryParameter("api_key", Constants.GIANT_BOMB_API_KEY)
                .addQueryParameter("format", "json")
                .addQueryParameter("filter", filterField1 + ":" + filterValue1)
                .addQueryParameter("resources", "game");
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<Game> processResults(Response response) {
        ArrayList<Game> games = new ArrayList<>();
        String gameRating = new String();
        String releaseDate = new String();

        try {
            String jsonData = response.body().string();
            if (response.isSuccessful()) {
                JSONObject giantBombJSON = new JSONObject(jsonData);
                JSONArray gamesJSON = giantBombJSON.getJSONArray("results");
                for (int i = 0; i < gamesJSON.length(); i++) {
                    JSONObject gameJSON = gamesJSON.getJSONObject(i);
                    String name = gameJSON.getString("name");
                    String imageUrl = gameJSON.getJSONObject("image").getString("small_url");
                    releaseDate = gameJSON.optString("original_release_date", "expected_release_day");
                    if (releaseDate == "null") {
                        releaseDate = "N/A";
                    }
                    ArrayList<String> platforms = new ArrayList<>();
                    JSONArray platformsJSON = gameJSON.getJSONArray("platforms");
                    for (int j = 0; j < platformsJSON.length(); j++) {
                        String platform = platformsJSON.getJSONObject(j).getString("abbreviation");
                        platforms.add(platform);
                    }
                    JSONArray ratingsJSON = gameJSON.optJSONArray("original_game_rating");
                    if (ratingsJSON != null) {
                        gameRating = ratingsJSON.getJSONObject(0).optString("name", "N/A");
                    } else {
                        gameRating = "N/A";
                    }
                    String siteDetailUrl = gameJSON.getString("site_detail_url");
                    String deck = gameJSON.optString("deck", "Not Provided");
                    int id = gameJSON.getInt("id");
                    Game game = new Game(name, imageUrl, releaseDate, platforms, gameRating, siteDetailUrl, deck, id);
                    games.add(game);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return games;
    }
}
