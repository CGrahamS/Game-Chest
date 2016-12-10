package com.epicodus.gamechest.ui;


import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.epicodus.gamechest.Constants;
import com.epicodus.gamechest.R;
import com.epicodus.gamechest.models.Game;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GameDetailFragment extends Fragment implements View.OnClickListener {
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

    @Bind(R.id.saveToFavoritesButton)
    Button mSaveToFavoritesButton;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_detail, container, false);
        ButterKnife.bind(this, view);

        Picasso.with(view.getContext()).load(mGame.getImage()).into(mGameDetailImageView);

        mGameDetailHeaderTextView.setText(mGame.getName());
        mGameDetailReleaseTextView.setText("Released: " + mGame.getReleaseDate());
        mGameDetailRatingTextView.setText("Original Rating: " + mGame.getContentRating());
        mGameDetailPlatformsTextView.setText("Platforms: " + android.text.TextUtils.join(", ", mGame.getPlatforms()));
        mGameDetailDeck.setText(mGame.getDeck());
        mGameDetailDeck.setMovementMethod(new ScrollingMovementMethod());

        mGameDetailUrl.setOnClickListener(this);

        mSaveToFavoritesButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == mGameDetailUrl) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(mGame.getSiteDetailUrl()));
            startActivity(webIntent);
        }
        if (v == mSaveToFavoritesButton) {
            DatabaseReference gameRef = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.FIREBASE_CHILD_GAMES);
            gameRef.child(mGame.getName()).setValue(mGame);
            Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
        }
    }

}
