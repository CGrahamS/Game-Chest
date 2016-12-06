package com.epicodus.gamechest.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.gamechest.R;
import com.epicodus.gamechest.models.Game;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by CGrahamS on 12/4/16.
 */
public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.GameViewHolder> {
    private ArrayList<Game> mGames = new ArrayList<>();
    private Context mContext;

    public GameListAdapter(Context context, ArrayList<Game> games) {
        mContext = context;
        mGames = games;
    }

    @Override
    public GameListAdapter.GameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_list_item, parent, false);
        GameViewHolder viewHolder = new GameViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GameListAdapter.GameViewHolder holder, int position) {
        holder.bindGame(mGames.get(position));
    }

    @Override
    public int getItemCount() {
        return mGames.size();
    }

    public class GameViewHolder extends RecyclerView.ViewHolder {
        private static final int MAX_WIDTH = 120;
        private static final int MAX_HEIGHT = 120;
        @Bind(R.id.gameImageView) ImageView mGameImageView;
        @Bind(R.id.gameNameTextView) TextView mGameNameTextView;
        @Bind(R.id.gameReleaseDateTextView) TextView mGameReleaseDateTextView;
        @Bind(R.id.gamePlatformsTextView) TextView mGamePlatforms;

        private Context mContext;

        public GameViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }

        public void bindGame(Game game) {
            mGameNameTextView.setText(game.getName());
            mGameReleaseDateTextView.setText("Released: " + game.getReleaseDate());
            mGamePlatforms.setText("Platforms: " + android.text.TextUtils.join(", ", game.getPlatforms()));
            Picasso.with(mContext)
                    .load(game.getImage())
                    .resize(MAX_WIDTH, MAX_HEIGHT)
                    .centerInside()
                    .into(mGameImageView);
        }

    }
}
