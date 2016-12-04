package com.epicodus.gamechest;

import android.util.Log;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

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
        String url = urlBuilder.build().toString();
        Log.d(TAG, url);

        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }
}
