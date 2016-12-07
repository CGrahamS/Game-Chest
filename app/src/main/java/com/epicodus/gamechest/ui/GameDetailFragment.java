package com.epicodus.gamechest.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.gamechest.R;
import com.epicodus.gamechest.models.Game;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GameDetailFragment extends Fragment {
    @Bind(R.id.gameDetailImageView)
    ImageView mGameDetailImageView;

    @Bind(R.id.gameDetailHeaderTextView)
    TextView mGameDetailHeaderTextView;

    @Bind(R.id.gameDetailReleaseTextView)
    TextView mGameDetailReleaseTextView;

    @Bind(R.id.gameDetailRatingTextView)
    TextView mGameDetailRatingTextView;

    @Bind(R.id.gameDetailPlatformsTextView)
    TextView mGameDetailPlatformsTextView;

    @Bind(R.id.gameDetailDeck)
    TextView mGameDetailDeck;

    @Bind(R.id.gameDetailUrl)
    TextView mGameDetailUrl;

    private Game mGame;

    public static GameDetailFragment newInstance(Game game) {
        GameDetailFragment gameDetailFragment = new GameDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("game", Parcels.wrap(game));
        gameDetailFragment.setArguments(args);
        return gameDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGame = Parcels.unwrap(getArguments().getParcelable("game"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_detail, container, false);
        ButterKnife.bind(this, view);

        Picasso.with(view.getContext()).load(mGame.getImage()).into(mGameDetailImageView);

        mGameDetailHeaderTextView.setText(mGame.getName());
        mGameDetailReleaseTextView.setText("Released: " + mGame.getReleaseDate());
        mGameDetailRatingTextView.setText("Original Rating: " + mGame.getContentRating());
        mGameDetailPlatformsTextView.setText("Platforms: " + android.text.TextUtils.join(", ", mGame.getPlatforms()));
        mGameDetailDeck.setText(mGame.getDeck());
        return view;
    }

}
