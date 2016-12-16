package com.epicodus.gamechest.adapters;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;

import com.epicodus.gamechest.models.Game;
import com.epicodus.gamechest.util.ItemTouchHelperAdapter;
import com.epicodus.gamechest.util.OnStartDragListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class FirebaseGameListAdapter extends FirebaseRecyclerAdapter<Game, FirebaseGameViewHolder> implements ItemTouchHelperAdapter {
    public static final String TAG = FirebaseGameListAdapter.class.getSimpleName();
    
    private DatabaseReference mRef;
    private Context mContext;
    private OnStartDragListener mOnStartDragListener;


    public FirebaseGameListAdapter(Class<Game> modelClass, int modelLayout,
                                   Class<FirebaseGameViewHolder> viewHolderClass,
                                   Query ref, OnStartDragListener onStartDragListener, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        mRef = ref.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;
    }

    @Override
    protected void populateViewHolder(final FirebaseGameViewHolder viewHolder, Game model, int position) {
        viewHolder.bindGame(model);
        viewHolder.mGameDetailsLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mOnStartDragListener.onStartDrag(viewHolder);
                }
                return false;
            }
        });

    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        return false;
    }

    @Override
    public void onItemDismiss(int position) {

    }
}
