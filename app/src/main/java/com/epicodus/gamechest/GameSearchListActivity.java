package com.epicodus.gamechest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class GameSearchListActivity extends AppCompatActivity {
    public static final String TAG = GameSearchListActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_search_list);
        Intent intent = getIntent();
        String game = intent.getStringExtra("game");
        Log.d(TAG, game);

        getGames(game);
    }

    private void getGames(String game) {
        final GiantBombService giantBombService = new GiantBombService();
        giantBombService.searchForGame(game, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String jsonData = response.body().string();
                    Log.v(TAG, jsonData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
