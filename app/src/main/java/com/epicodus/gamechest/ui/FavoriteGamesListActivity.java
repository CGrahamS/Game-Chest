package com.epicodus.gamechest.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.TextView;

import com.epicodus.gamechest.Constants;
import com.epicodus.gamechest.R;
import com.epicodus.gamechest.adapters.FirebaseGameListAdapter;
import com.epicodus.gamechest.adapters.FirebaseGameViewHolder;
import com.epicodus.gamechest.models.Game;
import com.epicodus.gamechest.util.ItemTouchHelperAdapter;
import com.epicodus.gamechest.util.OnStartDragListener;
import com.epicodus.gamechest.util.SimpleItemTouchHelperCallback;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FavoriteGamesListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_search_list);
    }
}
