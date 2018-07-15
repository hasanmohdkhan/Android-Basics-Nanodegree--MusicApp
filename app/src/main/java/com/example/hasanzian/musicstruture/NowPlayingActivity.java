package com.example.hasanzian.musicstruture;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class NowPlayingActivity extends AppCompatActivity {

    TextView songNowplaying, albumNP;
    ImageView coverNP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);
        songNowplaying = findViewById(R.id.song_name_n_p);
        albumNP = findViewById(R.id.album_n_p);
        coverNP = findViewById(R.id.cover_now_playing);

        Bundle songData = getIntent().getExtras();

        if (songData == null) {
            return;
        }

        String song = songData.getString("S");
        String artist = songData.getString("A");
        String art_cover = songData.getString("Art");
        songNowplaying.setText(song);
        albumNP.setText(artist);

        if (art_cover != null) {
            Bitmap artwork = BitmapFactory.decodeFile(art_cover);
            Glide.with(getApplicationContext()).load(artwork).into(coverNP);
        } else {
            Glide.with(getApplicationContext()).load(R.drawable.ic_launcher_background).into(coverNP);
        }



    }
}
