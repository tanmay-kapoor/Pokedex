package edu.northeastern.pokedex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import edu.northeastern.pokedex.assignment7.models.Pokemon;
import edu.northeastern.pokedex.assignment7.pokemonSearch.ItemClickListener;
import edu.northeastern.pokedex.assignment7.pokemonSearch.RecyclerAdapter;
import edu.northeastern.pokedex.assignment7.pokemonSearch.RecyclerHolder;
import edu.northeastern.pokedex.models.Sticker;
import edu.northeastern.pokedex.models.User;

public class StickerStatsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    List<Sticker> stickerList;
    private DatabaseReference userStickerRef;
    private Map<Integer, Integer> stickerDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker_stats);
        stickerList = new ArrayList<>();
        stickerList.add(new Sticker("Test", 1, null));
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        userStickerRef = mDatabase.child("stickers").child(user.getUid());
        getStickerDetails();
        createRecyclerView();
    }

    private void getStickerDetails() {
        userStickerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dsp : snapshot.getChildren()) {
                        int sticker = Integer.parseInt(dsp.getKey());
                        int count = Integer.parseInt(dsp.getValue().toString());

                        stickerList.add(new Sticker("Sticker", count, getDrawable(sticker)));
                        recyclerView.getAdapter().notifyItemInserted(stickerList.size()-1);
                    }
                    // display each sticker with count

                } else {
                    // no stickers for this user
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void createRecyclerView() {
        RecyclerView.LayoutManager recyclerLayoutManger = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.stickerList);
        recyclerView.setHasFixedSize(true);
        StickerRecyclerAdapter recyclerAdapter = new StickerRecyclerAdapter(stickerList);

        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(recyclerLayoutManger);
    }

    class StickerRecyclerHolder extends RecyclerView.ViewHolder {
        public Sticker sticker;
        TextView name;
        TextView count;

        ImageView image;

        public StickerRecyclerHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.sticker_name);
            count = itemView.findViewById(R.id.sticker_count);
            image = itemView.findViewById(R.id.sticker_image);
        }
    }

    class StickerRecyclerAdapter extends RecyclerView.Adapter<StickerRecyclerHolder> {
        private final List<Sticker> stickerList;
        private ItemClickListener listener;

        public StickerRecyclerAdapter(List<Sticker> stickerList) {
            this.stickerList = stickerList;
            Log.i("JELLO", this.stickerList.toString());
        }

        public void setOnItemClickListener(ItemClickListener listener) {
            this.listener = listener;
        }

        @NonNull
        @Override
        public StickerRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sticker_stats_item, parent, false);
            return new StickerRecyclerHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull StickerRecyclerHolder holder, int position) {
            Sticker sticker = stickerList.get(position);
            holder.name.setText(sticker.getName());
            holder.image.setImageDrawable(sticker.getImage());
            holder.count.setText("Used " + String.valueOf(sticker.getCount()) + " times");
        }


        @Override
        public int getItemCount() {
            return stickerList.size();
        }
    }
}