package com.epicodus.gamechest.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.gamechest.R;
import com.epicodus.gamechest.models.Game;
import com.epicodus.gamechest.ui.GameDetailActivity;
import com.epicodus.gamechest.ui.GameDetailFragment;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

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

    public class GameViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private static final int MAX_WIDTH = 100;
        private static final int MAX_HEIGHT = 100;
        @Bind(R.id.gameImageView) ImageView mGameImageView;
        @Bind(R.id.gameNameTextView) TextView mGameNameTextView;
        @Bind(R.id.gameReleaseDateTextView) TextView mGameReleaseDateTextView;
        @Bind(R.id.gamePlatformsTextView) TextView mGamePlatforms;

        private Context mContext;
        private int mOrientation;

        public GameViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mContext = itemView.getContext();

            mOrientation = itemView.getResources().getConfiguration().orientation;

            if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                createDetailFragment(0);
            }

            itemView.setOnClickListener(this);
        }

        public void createDetailFragment(int position) {
            GameDetailFragment detailFragment = GameDetailFragment.newInstance(mGames, position);

            FragmentTransaction ft = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.gameDetailContainer, detailFragment);
            ft.commit();
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

        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                createDetailFragment(itemPosition);
            } else {
                Intent intent = new Intent(mContext, GameDetailActivity.class);
                intent.putExtra("position", itemPosition);
                intent.putExtra("games", Parcels.wrap(mGames));
                mContext.startActivity(intent);
            }
        }

    }
}
