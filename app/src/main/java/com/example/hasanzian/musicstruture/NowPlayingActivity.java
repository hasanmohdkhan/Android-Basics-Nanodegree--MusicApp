package com.example.hasanzian.musicstruture;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;

public class NowPlayingActivity extends AppCompatActivity {

    TextView songNowplaying, albumNP;
    ImageView coverNP;
    MediaPlayer mp;
    SeekBar seekBar;
    int seek_value;
    ImageView play, rewind, fast_forward;
    String song, artist, art_cover, path;
    Boolean isPlaying = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);
        songNowplaying = findViewById(R.id.song_name_n_p);
        albumNP = findViewById(R.id.album_n_p);
        coverNP = findViewById(R.id.cover_now_playing);
        seekBar = findViewById(R.id.seek_bar);
        play = findViewById(R.id.play);
        rewind = findViewById(R.id.rewind);
        fast_forward = findViewById(R.id.fast_forward);
        mp = new MediaPlayer();

        //Extract Bundle here
        Bundle songData = getIntent().getExtras();
        if (songData == null) {
            return;
        }
        song = songData.getString("S");
        artist = songData.getString("A");
        art_cover = songData.getString("Art");
        path = songData.getString("Path");

        songNowplaying.setText(song);
        albumNP.setText(artist);

        if (art_cover != null) {
            Bitmap artwork = BitmapFactory.decodeFile(art_cover);
            Glide.with(getApplicationContext()).load(artwork).into(coverNP);
        } else {
            Glide.with(getApplicationContext()).load(R.drawable.ic_launcher_background).into(coverNP);
        }

        // Prepare media player
        try {
            mp.setDataSource(path);
            mp.prepare();
            //mp.start();
            seekBar.setMax(mp.getDuration());
            myThread t = new myThread();
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }


        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPlaying) {
                    mp.pause();
                    isPlaying = false;
                    play.setImageResource(R.drawable.pause_circle);
                } else {
                    mp.start();
                    isPlaying = true;
                    play.setImageResource(R.drawable.play_circle);
                }
            }
        });

        fast_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.seekTo(mp.getCurrentPosition() + 30000);
            }
        });

        rewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mp.getCurrentPosition() > 3000)
                    mp.seekTo(mp.getCurrentPosition() - 30000);
            }
        });


        if (mp.isPlaying()) {
            Toast.makeText(this, "Is Playing", Toast.LENGTH_SHORT).show();
            mp.release();
        } else {
            Toast.makeText(this, "Else block", Toast.LENGTH_SHORT).show();

        }


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                seek_value = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mp.seekTo(seek_value);
            }
        });
    }

    class myThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mp != null) {
                            seekBar.setProgress(mp.getCurrentPosition());
                        }
                    }
                });
            }
        }
    }
}
