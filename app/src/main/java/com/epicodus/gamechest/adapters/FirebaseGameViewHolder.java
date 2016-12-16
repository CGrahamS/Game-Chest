package com.epicodus.gamechest.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.epicodus.gamechest.Constants;
import com.epicodus.gamechest.R;
import com.epicodus.gamechest.models.Game;
import com.epicodus.gamechest.ui.GameDetailActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class FirebaseGameViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public static final String TAG = FirebaseGameViewHolder.class.getSimpleName();
    View mView;
    Context mContext;
    public LinearLayout mGameDetailsLayout;

    public FirebaseGameViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
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
    public void onClick(View v) {
        final ArrayList<Game> games = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_USERS);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    games.add(snapshot.getValue(Game.class));
                }

                int itemPosition = getLayoutPosition();
                Intent intent = new Intent(mContext, GameDetailActivity.class);
                intent.putExtra("position", itemPosition);
                intent.putExtra("games", Parcels.wrap(games));
                mContext.startActivity(intent);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
