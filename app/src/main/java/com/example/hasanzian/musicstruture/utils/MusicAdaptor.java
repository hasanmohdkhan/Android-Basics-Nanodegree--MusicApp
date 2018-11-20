package com.example.hasanzian.musicstruture.utils;

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

public class MusicAdaptor extends RecyclerView.Adapter<MusicAdaptor.myViewHolder> {

    private List<MusicModel> mList;
    private Context mContext;

    public MusicAdaptor(List<MusicModel> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        mContext = parent.getContext();

        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        holder.currentSong.setText(mList.get(position).getSongName());
        holder.currentAlbum.setText(mList.get(position).getAlbum());
        holder.currentArtist.setText(mList.get(position).getArtist());

        //setting Album art
        if (mList.get(position).getCover() != null) {
            Bitmap artwork = BitmapFactory.decodeFile(mList.get(position).getCover());
            Glide.with(mContext).load(artwork).into(holder.currentAlbumArt);
        } else {
            Glide.with(mContext).load(R.drawable.music).into(holder.currentAlbumArt);
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.song_name)
        TextView currentSong;
        @BindView(R.id.album_name)
        TextView currentAlbum;
        @BindView(R.id.artist_name)
        TextView currentArtist;
        @BindView(R.id.album_art)
        ImageView currentAlbumArt;

        myViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
