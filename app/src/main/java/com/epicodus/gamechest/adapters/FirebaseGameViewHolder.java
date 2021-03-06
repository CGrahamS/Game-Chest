package com.epicodus.gamechest.adapters;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.epicodus.gamechest.R;
import com.epicodus.gamechest.models.Game;
import com.epicodus.gamechest.util.ItemTouchHelperViewHolder;
import com.squareup.picasso.Picasso;

public class FirebaseGameViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
    public static final String TAG = FirebaseGameViewHolder.class.getSimpleName();
    View mView;
    Context mContext;
    public LinearLayout mGameDetailsLayout;

    public FirebaseGameViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
    }

    public void bindGame(Game game) {
        ImageView gameImageView = (ImageView) mView.findViewById(R.id.gameImageView);
        TextView gameNameTextView = (TextView) mView.findViewById(R.id.gameNameTextView);
        TextView gameReleaseDateTextView = (TextView) mView.findViewById(R.id.gameReleaseDateTextView);
        TextView gamePlatformsTextView = (TextView) mView.findViewById(R.id.gamePlatformsTextView);
        mGameDetailsLayout = (LinearLayout) mView.findViewById(R.id.gameDetailsLayout);

        Picasso.with(mContext)
                .load(game.getImage())
                .into(gameImageView);

        gameNameTextView.setText(game.getName());
        gameReleaseDateTextView.setText(game.getReleaseDate());
        gamePlatformsTextView.setText("Platforms: " + android.text.TextUtils.join(", ", game.getPlatforms()));
    }

    @Override
    public void onItemSelected() {
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(mContext,
                R.animator.drag_scale_on);
        set.setTarget(itemView);
        set.start();
    }

    @Override
    public void onItemClear() {
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(mContext,
                R.animator.drag_scale_off);
        set.setTarget(itemView);
        set.start();
    }
}
