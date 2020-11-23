package com.example.jsouptest;

import android.content.Context;
import android.content.Intent;
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
    public static final String VideoLink= "";

    public Adapter(ArrayList<AnimeModel> mDataset, Context context) {
        this.mDataset = mDataset;
        this.context= context;
    }

    private class AnimeViewholder extends RecyclerView.ViewHolder{
        ShapeableImageView imageView;
        MaterialButton streambut;
        MaterialTextView episode_textView;



        public AnimeViewholder(@NonNull View itemView) {
            super(itemView);
            imageView= itemView.findViewById(R.id.imageView);
            streambut= itemView.findViewById(R.id.stream);
            episode_textView= itemView.findViewById(R.id.episode_no);
        }
        void bind(final int position){
            //textView.setText(mDataset.get(position).getEpisode());
            episode_textView.setText((mDataset.get(position).getTitle()+ "\n\n"+ mDataset.get(position).getEpisode()));
            Picasso.get().load(mDataset.get(position).getImg_url()).resize(720,1080).into(imageView);

            streambut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, Player.class);
                    intent.putExtra(VideoLink,mDataset.get(position).getVidLink());
                    view.getContext().startActivity(intent);
                }
            });
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
