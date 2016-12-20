package com.epicodus.gamechest.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.epicodus.gamechest.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.gameSearchButton)
    Button mGameSearchButton;

    @Bind(R.id.gameBrowseButton)
    Button mGameBrowseButton;

    @Bind(R.id.favoritedGamesButton)
    Button mFavoritedGamesButton;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Welcome to Game Chest!");

        Typeface headerFont = Typeface.createFromAsset(getAssets(), "fonts/gothic.ttf");
//        mMainHeaderTextView.setTypeface(headerFont);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    getSupportActionBar().setTitle("Welcome " + user.getDisplayName() + "!");
                    mFavoritedGamesButton.setVisibility(View.VISIBLE);
                } else {
                    mFavoritedGamesButton.setVisibility(View.GONE);
                }
            }
        };

        mGameSearchButton.setOnClickListener(this);
        mGameBrowseButton.setOnClickListener(this);
        mFavoritedGamesButton.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem logoutItem = menu.findItem(R.id.action_logout);
        MenuItem loginItem = menu.findItem(R.id.action_login);

        if (user != null) {
            logoutItem.setVisible(true);
            loginItem.setVisible(false);
        } else {
            logoutItem.setVisible(false);
            loginItem.setVisible(true);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        if (id == R.id.action_login) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v == mGameSearchButton) {
            Intent intent = new Intent(MainActivity.this, GameSearchListActivity.class);
            startActivity(intent);
        }
        if (v == mGameBrowseButton) {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        }
        if (v == mFavoritedGamesButton) {
            Intent intent = new Intent(MainActivity.this, FavoriteGamesListActivity.class);
            startActivity(intent);
        }
    }

    //TODO potential source of bugs (does not navigate user to another activity)
    private void logout() {
        FirebaseAuth.getInstance().signOut();
        getSupportActionBar().setTitle("Welcome to Game Chest!");
        Toast.makeText(MainActivity.this, "Goodbye, " + user.getDisplayName() + "!",
                Toast.LENGTH_SHORT).show();
    }
}
