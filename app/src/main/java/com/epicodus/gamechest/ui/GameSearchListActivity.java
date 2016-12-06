package com.epicodus.gamechest.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.epicodus.gamechest.adapters.GameListAdapter;
import com.epicodus.gamechest.models.Game;
import com.epicodus.gamechest.services.GiantBombService;
import com.epicodus.gamechest.R;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class GameSearchListActivity extends AppCompatActivity {
    public static final String TAG = GameSearchListActivity.class.getSimpleName();
    @Bind(R.id.gameSearchTextView) TextView mGameSearchTextView;
    @Bind(R.id.gameRecyclerView) RecyclerView mGameRecyclerView;
    private GameListAdapter mAdapter;
    public ArrayList<Game> mGames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_search_list);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String game = intent.getStringExtra("game");
        mGameSearchTextView.setText("Results for: " + '"' + game + '"');

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
                mGames = giantBombService.processResults(response);

                GameSearchListActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        String[] gameNames = new String[mGames.size()];
                        for (int i = 0; i < gameNames.length; i++) {
                            gameNames[i] = mGames.get(i).getName();
                        }
                        for (Game game : mGames) {
                            Log.d(TAG, "Name: " + game.getName());
                            Log.d(TAG, "Deck: " + game.getDeck());
                            Log.d(TAG, "ID: " + game.getId());
                            Log.d(TAG, "Platforms: " + game.getPlatforms());
                        }

                        mAdapter = new GameListAdapter(getApplicationContext(), mGames);
                        mGameRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager =
                                new LinearLayoutManager(GameSearchListActivity.this);
                        mGameRecyclerView.setLayoutManager(layoutManager);
                        mGameRecyclerView.setHasFixedSize(true);
                    }
                });
            }
        });
    }
}
