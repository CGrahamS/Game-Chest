package com.epicodus.gamechest.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;

import com.epicodus.gamechest.Constants;
import com.epicodus.gamechest.R;
import com.epicodus.gamechest.models.Game;
import com.epicodus.gamechest.ui.GameDetailActivity;
import com.epicodus.gamechest.ui.GameDetailFragment;
import com.epicodus.gamechest.util.ItemTouchHelperAdapter;
import com.epicodus.gamechest.util.OnStartDragListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class FirebaseGameListAdapter extends FirebaseRecyclerAdapter<Game, FirebaseGameViewHolder> implements ItemTouchHelperAdapter {
    public static final String TAG = FirebaseGameListAdapter.class.getSimpleName();
    
    private DatabaseReference mRef;
    private Context mContext;
    private OnStartDragListener mOnStartDragListener;
    private ChildEventListener mChildEventListener;
    private ArrayList<Game> mGames = new ArrayList<>();
    private int mOrientation;

    public FirebaseGameListAdapter(Class<Game> modelClass, int modelLayout,
                                   Class<FirebaseGameViewHolder> viewHolderClass,
                                   Query ref, OnStartDragListener onStartDragListener, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        mRef = ref.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;

        mChildEventListener = mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mGames.add(dataSnapshot.getValue(Game.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void populateViewHolder(final FirebaseGameViewHolder viewHolder, Game model, int position) {
        viewHolder.bindGame(model);

        mOrientation = viewHolder.itemView.getResources().getConfiguration().orientation;

        if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            createDetailFragment(0);
        }

        viewHolder.mGameDetailsLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mOnStartDragListener.onStartDrag(viewHolder);
                }
                return false;
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int itemPosition = viewHolder.getLayoutPosition();
                if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                    createDetailFragment(itemPosition);
                } else {
                    Intent intent = new Intent(mContext, GameDetailActivity.class);
                    intent.putExtra(Constants.EXTRA_KEY_POSITION, viewHolder.getAdapterPosition());
                    intent.putExtra(Constants.EXTRA_KEY_GAMES, Parcels.wrap(mGames));
                    mContext.startActivity(intent);
                }
            }
        });

    }

    public void createDetailFragment(int position) {
        GameDetailFragment detailFragment = GameDetailFragment.newInstance(mGames, position);

        FragmentTransaction ft = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.gameDetailContainer, detailFragment);
        ft.commit();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mGames, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return false;
    }

    @Override
    public void onItemDismiss(int position) {
        mGames.remove(position);
        getRef(position).removeValue();
    }

    @Override
    public void cleanup() {
        super.cleanup();
        setIndexInFirebase();
        mRef.removeEventListener(mChildEventListener);
    }

    private void setIndexInFirebase() {
        for (Game game : mGames) {
            int index = mGames.indexOf(game);
            DatabaseReference ref = getRef(index);
            game.setIndex(Integer.toString(index));
            ref.setValue(game);
        }
    }
}
