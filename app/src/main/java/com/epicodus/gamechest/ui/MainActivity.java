package com.epicodus.gamechest.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.epicodus.gamechest.Constants;
import com.epicodus.gamechest.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.gameSearchButton) Button mGameSearchButton;
    @Bind(R.id.gameBrowseButton) Button mGameBrowseButton;
    @Bind(R.id.platformBrowseButton) Button mPlatformBrowseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mGameSearchButton.setOnClickListener(this);
        mGameBrowseButton.setOnClickListener(this);
        mPlatformBrowseButton.setOnClickListener(this);
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
    }
}
