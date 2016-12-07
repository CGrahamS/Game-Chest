package com.epicodus.gamechest.ui;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.epicodus.gamechest.R;
import com.epicodus.gamechest.adapters.GamePagerAdapter;
import com.epicodus.gamechest.models.Game;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GameDetailActivity extends AppCompatActivity {
    @Bind(R.id.viewPager)
    ViewPager mViewPager;

    private GamePagerAdapter adapterViewPager;
    ArrayList<Game> mGames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);
        ButterKnife.bind(this);

        mGames = Parcels.unwrap(getIntent().getParcelableExtra("games"));
        int startingPosition = getIntent().getIntExtra("position", 0);

        adapterViewPager = new GamePagerAdapter(getSupportFragmentManager(), mGames);
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition);
    }
}
