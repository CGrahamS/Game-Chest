package com.epicodus.gamechest.ui;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.epicodus.gamechest.Constants;
import com.epicodus.gamechest.R;
import com.epicodus.gamechest.adapters.GameListAdapter;
import com.epicodus.gamechest.models.Game;
import com.epicodus.gamechest.services.GiantBombService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class GameSearchListFragment extends Fragment {
    @Bind(R.id.gameListTextView)
    TextView mGameListTextView;

    @Bind(R.id.gameRecyclerView)
    RecyclerView mGameRecyclerView;

    private SharedPreferences mGameSearchSharedPreference;
    private SharedPreferences.Editor mGameSearchPreferenceEditor;
    private String mRecentGame;
    private GameListAdapter mAdapter;
    public ArrayList<Game> mGames = new ArrayList<>();
    private ProgressDialog mSearchProgressDialog;

    public GameSearchListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGameSearchSharedPreference = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mGameSearchPreferenceEditor = mGameSearchSharedPreference.edit();

        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_search_list, container, false);
        ButterKnife.bind(this, view);

        mRecentGame = mGameSearchSharedPreference.getString(Constants.PREFERENCES_GAME_KEY, null);

        if (mRecentGame != null) {
            mGameListTextView.setText("Results for: " + '"' + mRecentGame + '"');
            createSearchProgressDialog();
            getGames(mRecentGame);
        } else {
            createSearchProgressDialog();
            mGameListTextView.setText("Search for a Game!");
        }

        // Inflate the layout for this fragment
        return view;
    }

    private void addToSharedPreferences(String game) {
        mGameSearchPreferenceEditor.putString(Constants.PREFERENCES_GAME_KEY, game).apply();
    }

    private void createSearchProgressDialog() {
        mSearchProgressDialog = new ProgressDialog(getActivity());
        mSearchProgressDialog.setTitle("Loading...");
        mSearchProgressDialog.setMessage("Searching for game...");
        mSearchProgressDialog.setCancelable(false);
    }

    private void getGames(String game) {
        mSearchProgressDialog.show();
        final GiantBombService giantBombService = new GiantBombService();
        giantBombService.searchForGame(game, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mSearchProgressDialog.dismiss();
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mGames = giantBombService.processResults(response);

                if (mGames.size() == 0) {

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mGameListTextView.setText("Sorry! \n We can't find that game.");
                        }
                    });
                    mSearchProgressDialog.dismiss();
                }

                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        mAdapter = new GameListAdapter(getActivity(), mGames);
                        mGameRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager =
                                new LinearLayoutManager(getActivity());
                        mGameRecyclerView.setLayoutManager(layoutManager);
                        mGameRecyclerView.setHasFixedSize(true);
                    }
                });
                mSearchProgressDialog.dismiss();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search, menu);
        ButterKnife.bind(getActivity());

        mGameSearchSharedPreference = PreferenceManager.getDefaultSharedPreferences(getActivity());
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}
