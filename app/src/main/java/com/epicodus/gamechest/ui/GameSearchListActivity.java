package com.epicodus.gamechest.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.epicodus.gamechest.Constants;
import com.epicodus.gamechest.adapters.GameListAdapter;
import com.epicodus.gamechest.models.Game;
import com.epicodus.gamechest.services.GiantBombService;
import com.epicodus.gamechest.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.prefs.PreferenceChangeEvent;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

//FIXME add click listener so that clicking a list item takes you to that list item in the pager adapter

public class GameSearchListActivity extends AppCompatActivity {
    public static final String TAG = GameSearchListActivity.class.getSimpleName();

    private SharedPreferences mGameSearchSharedPreference;
    private SharedPreferences.Editor mGameSearchPreferenceEditor;
    private String mRecentGame;


    @Bind(R.id.gameListTextView)
    TextView mGameListTextView;

    @Bind(R.id.gameRecyclerView)
    RecyclerView mGameRecyclerView;


    private GameListAdapter mAdapter;
    public ArrayList<Game> mGames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_search_list);
        ButterKnife.bind(this);

        mGameSearchSharedPreference = PreferenceManager.getDefaultSharedPreferences(this);
        mRecentGame = mGameSearchSharedPreference.getString(Constants.PREFERENCES_GAME_KEY, null);
        if (mRecentGame != null) {
            mGameListTextView.setText("Results for: " + '"' + mRecentGame + '"');
            getGames(mRecentGame);
        } else {
            mGameListTextView.setText("Search for a Game!");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        ButterKnife.bind(this);

        mGameSearchSharedPreference = PreferenceManager.getDefaultSharedPreferences(this);
        mGameSearchPreferenceEditor = mGameSearchSharedPreference.edit();

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                addToSharedPreferences(query);
                getGames(query);
                mGameListTextView.setText("Results for: " + '"' + query + '"');
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
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

    private void addToSharedPreferences(String game) {
        mGameSearchPreferenceEditor.putString(Constants.PREFERENCES_GAME_KEY, game).apply();
    }
}
