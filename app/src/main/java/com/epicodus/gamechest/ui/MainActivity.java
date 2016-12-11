package com.epicodus.gamechest.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

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

    @Bind(R.id.platformBrowseButton)
    Button mPlatformBrowseButton;

    @Bind(R.id.favoritedGamesButton)
    Button mFavoritedGamesButton;

    @Bind(R.id.signInButton)
    Button mSignInButton;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    if (user.getDisplayName() == null ) {
                        getSupportActionBar().setTitle("Welcome to Game Chest!");
                    } else {
                        getSupportActionBar().setTitle("Welcome " + user.getDisplayName() + "!");
                    }
                }
            }
        };

        mGameSearchButton.setOnClickListener(this);
        mGameBrowseButton.setOnClickListener(this);
        mPlatformBrowseButton.setOnClickListener(this);
        mFavoritedGamesButton.setOnClickListener(this);
        mSignInButton.setOnClickListener(this);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
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
            Intent intent = new Intent(MainActivity.this, GameBrowseActivity.class);
            startActivity(intent);
        }
        if (v == mPlatformBrowseButton) {
            Intent intent = new Intent(MainActivity.this, PlatformBrowseActivity.class);
            startActivity(intent);
        }
        if (v == mFavoritedGamesButton) {
            Intent intent = new Intent(MainActivity.this, FavoriteGamesListActivity.class);
            startActivity(intent);
        }
        if (v == mSignInButton) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    //TODO potential source of bugs (does not navigate user to another activity)
    private void logout() {
        FirebaseAuth.getInstance().signOut();
    }
}
