package com.example.jsouptest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<AnimeModel> mDataset;
    Context context;

    public Adapter(ArrayList<AnimeModel> mDataset, Context context) {
        this.mDataset = mDataset;
        this.context= context;
    }

    private class AnimeViewholder extends RecyclerView.ViewHolder{
        ShapeableImageView imageView;
        MaterialTextView textView;


        public AnimeViewholder(@NonNull View itemView) {
            super(itemView);
            imageView= itemView.findViewById(R.id.imageView);
            textView= itemView.findViewById(R.id.animetitle);
        }
        void bind(int position){
            textView.setText(mDataset.get(position).getTitle());
            Picasso.get().load(mDataset.get(position).getImg_url()).into(imageView);
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AnimeViewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.anime_titles,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((AnimeViewholder)holder).bind(position);

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
