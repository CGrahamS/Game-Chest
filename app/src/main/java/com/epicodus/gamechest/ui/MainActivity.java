package com.epicodus.gamechest.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.epicodus.gamechest.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.gameSearchButton) Button mGameSearchButton;
    @Bind(R.id.gameSearchEditText) EditText mGameSearchEditText;
    @Bind(R.id.gameBrowseButton) Button mGameBrowseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mGameSearchButton.setOnClickListener(this);
        mGameBrowseButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mGameSearchButton) {
            String game = mGameSearchEditText.getText().toString();
            Intent intent = new Intent(MainActivity.this, GameSearchListActivity.class);
            intent.putExtra("game", game);
            startActivity(intent);
        }
        if (v == mGameBrowseButton) {
            String game = mGameSearchEditText.getText().toString();
            Intent intent = new Intent(MainActivity.this, GameBrowseActivity.class);
            startActivity(intent);
        }
    }

}
