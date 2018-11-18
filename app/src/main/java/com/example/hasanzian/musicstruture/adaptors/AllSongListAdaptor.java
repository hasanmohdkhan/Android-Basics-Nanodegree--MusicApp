package com.example.hasanzian.musicstruture.adaptors;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hasanzian.musicstruture.R;
import com.example.hasanzian.musicstruture.model.MusicModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllSongListAdaptor extends RecyclerView.Adapter<AllSongListAdaptor.MyViewHolder> {

    private List<MusicModel> mList;
    private Context mContext;

    public AllSongListAdaptor(List<MusicModel> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        mContext = parent.getContext();

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.albumName.setText(mList.get(position).getAlbum());
        holder.songName.setText(mList.get(position).getSongName());
        holder.artistName.setText(mList.get(position).getArtist());

        //setting Album art
        if (mList.get(position).getCover() != null) {
            Bitmap artwork = BitmapFactory.decodeFile(mList.get(position).getCover());
            Glide.with(mContext).load(artwork).into(holder.albumArt);
        } else {
            Glide.with(mContext).load(R.drawable.music).into(holder.albumArt);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.song_name)
        TextView songName;
        @BindView(R.id.artist_name)
        TextView artistName;
        @BindView(R.id.album_name)
        TextView albumName;
        @BindView(R.id.album_art)
        ImageView albumArt;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


    }
}
