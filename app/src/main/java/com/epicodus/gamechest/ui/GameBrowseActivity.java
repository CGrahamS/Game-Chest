package com.epicodus.gamechest.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.epicodus.gamechest.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GameBrowseActivity extends AppCompatActivity {
    @Bind(R.id.platformSpinner)
    Spinner mPlatformSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_browse);
        ButterKnife.bind(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.platforms_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPlatformSpinner.setAdapter(adapter);
    }
}
