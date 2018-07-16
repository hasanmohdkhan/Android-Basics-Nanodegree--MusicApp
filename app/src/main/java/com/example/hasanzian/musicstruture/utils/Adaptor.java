package com.example.hasanzian.musicstruture.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hasanzian.musicstruture.R;
import com.example.hasanzian.musicstruture.model.MusicModel;

import java.util.List;

/**
 * Adaptor class for setting objects and views
 */

public class Adaptor extends ArrayAdapter<MusicModel> {

    public Adaptor(@NonNull Context context, List<MusicModel> objects) {
        super(context,0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        // initialing views here
        TextView song = listItemView.findViewById(R.id.song_name);
        TextView artist = listItemView.findViewById(R.id.artist_name);
        TextView album = listItemView.findViewById(R.id.album_name);
        ImageView art = listItemView.findViewById(R.id.album_art);

        // getting current position of objects in list and setting respectively
        MusicModel current = getItem(position);

        assert current != null;
        //setting Album art
        if (current.getCover() != null) {
            Bitmap artwork = BitmapFactory.decodeFile(current.getCover());
            Glide.with(getContext()).load(artwork).into(art);
        } else {
            Glide.with(getContext()).load(R.drawable.music).into(art);
        }
        //setting song name,artist name,album name
        song.setText(current.getSongName());
        artist.setText(current.getArtist());
        album.setText(current.getAlbum());
        // returning list
        return listItemView;
    }
}
