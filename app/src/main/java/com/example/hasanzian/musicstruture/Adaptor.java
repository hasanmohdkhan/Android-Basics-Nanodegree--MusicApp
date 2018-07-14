package com.example.hasanzian.musicstruture;

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

import java.util.List;

/**
 * Created by hasanZian on 12-07-2018.
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

        MusicModel current = getItem(position);
        TextView song = listItemView.findViewById(R.id.song_name);
        TextView artist = listItemView.findViewById(R.id.artist_name);
        TextView album = listItemView.findViewById(R.id.album_name);
        ImageView art = listItemView.findViewById(R.id.album_art);

        assert current != null;
        //setting Album art
        if (current.getmCover() != null) {
            Bitmap artwork = BitmapFactory.decodeStream(current.getmCover());
            art.setImageBitmap(artwork);
        }
        else {
            Glide.with(getContext()).load(R.drawable.ic_launcher_foreground).into(art);
        }


        song.setText(current.getmSongName());
        artist.setText(current.getmArtist());
        album.setText(current.getmAlbum());

        return listItemView;
    }
}
