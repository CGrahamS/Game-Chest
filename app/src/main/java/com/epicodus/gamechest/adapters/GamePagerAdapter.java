package com.epicodus.gamechest.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.epicodus.gamechest.models.Game;
import com.epicodus.gamechest.ui.GameDetailFragment;

import java.util.ArrayList;

/**
 * Created by CGrahamS on 12/6/16.
 */
public class GamePagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Game> mGames;

    public GamePagerAdapter(FragmentManager fm, ArrayList<Game> games) {
        super(fm);
        mGames = games;
    }

    @Override
    public Fragment getItem(int position) {
        return GameDetailFragment.newInstance(mGames, position);
    }

    @Override
    public int getCount() {
        return mGames.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mGames.get(position).getName();
    }
}
