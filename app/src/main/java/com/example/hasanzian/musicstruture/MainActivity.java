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
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    ArrayList<MusicModel> library;
    Adaptor adaptor;
    ListView listView;

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

        permissionChack();

        if (isPermissionGranted()) {
            addMusic();
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), NowPlayingActivity.class);
                MusicModel current = adaptor.getItem(position);
                String song = current.getmSongName();
                String album = current.getmAlbum();
                String art = current.getmCover();
                String path = current.getmID();
                i.putExtra("S", song);
                i.putExtra("A", album);
                i.putExtra("Art", art);
                i.putExtra("Path", path);
                startActivity(i);

            }
        });


    }

    private void permissionChack() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "Permission Required", Toast.LENGTH_SHORT).show();
            return;
        }
        //addMusic();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Granted", Toast.LENGTH_SHORT).show();
                    addMusic();
                } else {
                    Toast.makeText(getApplicationContext(), "Deny", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }

    public boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
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
                return a.getmSongName().compareTo(b.getmSongName());
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
            // getting album fullpath here
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





