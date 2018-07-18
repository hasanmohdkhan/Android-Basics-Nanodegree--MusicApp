package com.example.hasanzian.musicstruture;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hasanzian.musicstruture.model.MusicModel;
import com.example.hasanzian.musicstruture.utils.Adaptor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<MusicModel> library;
    private Adaptor adaptor;
    private ListView listView;

    private static String getCoverArtPath(long albumId, Context context) {

        Cursor albumCursor = context.getContentResolver().query(
                MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Audio.Albums.ALBUM_ART},
                MediaStore.Audio.Albums._ID + " = ?",
                new String[]{Long.toString(albumId)},
                null
        );
        boolean queryResult = false;
        if (albumCursor != null) {
            queryResult = albumCursor.moveToFirst();
        }
        String result = null;
        if (queryResult) {
            result = albumCursor.getString(0);
        }
        albumCursor.close();
        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        library = new ArrayList<MusicModel>();
        listView = findViewById(R.id.list);
        //checking for permission if not granted then show a toast
        permissionCheck();
        //if permission is granted then add music to list
        if (isPermissionGranted()) {
            addMusic();
        }
        //listener for list item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent nowPlayingIntent = new Intent(getApplicationContext(), NowPlayingActivity.class);
                MusicModel current = adaptor.getItem(position);
                assert current != null;
                // getting current position and setting respective object
                String song = current.getSongName();
                String artist = current.getArtist();
                String art = current.getCover();
                String path = current.getID();

                //transferring current click song,album art,
                // artist name to Now playing activity
                nowPlayingIntent.putExtra(getString(R.string.song_key), song);
                nowPlayingIntent.putExtra(getString(R.string.artist_key), artist);
                nowPlayingIntent.putExtra(getString(R.string.album_art_key), art);
                nowPlayingIntent.putExtra(getString(R.string.location_of_song_key), path);

                Pair<View, String>[] pairs = new Pair[3];
                pairs[0] = new Pair<View, String>(findViewById(R.id.album_art), getString(R.string.transition_album_name));
                pairs[1] = new Pair<View, String>(findViewById(R.id.song_name), getString(R.string.transition_song_name));
                pairs[2] = new Pair<View, String>(findViewById(R.id.artist_name), getString(R.string.transition_artist_name));
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, pairs);
                startActivity(nowPlayingIntent, options.toBundle());

            }
        });


    }

    private void permissionCheck() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), R.string.permission_required_str, Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), R.string.permission_granted_str, Toast.LENGTH_SHORT).show();
                    addMusic();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.permission_deny_str, Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }

    public boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }

    // adding music to list
    private void addMusic() {
        getSongList();
        Collections.sort(library, new Comparator<MusicModel>() {
            public int compare(MusicModel a, MusicModel b) {
                return a.getSongName().compareTo(b.getSongName());
            }
        });
        adaptor = new Adaptor(getApplicationContext(), library);
        listView.setAdapter(adaptor);
    }

    public void getSongList() {
        //retrieve song info
        ContentResolver musicResolver = getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null,
                null, null, null);

        if (musicCursor != null && musicCursor.moveToFirst()) {
            int fullpath = musicCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            int title = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int artist = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int album = musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
            // getting album full path here
            int album_id = musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);

            do {
                String Path = musicCursor.getString(fullpath);
                long album__id = musicCursor.getInt(album_id);
                //calling getCoverArtPath for Album art
                String path = getCoverArtPath(album__id, getApplicationContext());
                String titleName = musicCursor.getString(title);
                String artistName = musicCursor.getString(artist);
                String albumName = musicCursor.getString(album);
                // setting data to list
                library.add(new MusicModel(Path, titleName, artistName, albumName, path));
            } while (musicCursor.moveToNext());

        }
        musicCursor.close();
    }


}





