package com.example.hasanzian.musicstruture;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    final public static Uri sArtworkUri = Uri
            .parse("content://media/external/audio/albumart");
    final String MEDIA_PATH = "/sdcard/audio.mp3";
    MediaMetadataRetriever retriever;
    ArrayList<MusicModel> library;
    Adaptor adaptor;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        library = new ArrayList<MusicModel>();
//        retriever = new MediaMetadataRetriever();
        listView = findViewById(R.id.list);
        getSongList();
        //getAlbum();
        Collections.sort(library, new Comparator<MusicModel>() {
            public int compare(MusicModel a, MusicModel b) {
                return a.getmSongName().compareTo(b.getmSongName());
            }
        });

        adaptor = new Adaptor(this, library);

        listView.setAdapter(adaptor);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), NowPlayingActivity.class);
                MusicModel current = adaptor.getItem(position);
                String song = current.getmSongName();
                String album = current.getmAlbum();
                //Uri art = current.getmCover();
                i.putExtra("S", song);
                i.putExtra("A", album);
                // i.putExtra("Art", art);
                startActivity(i);

            }
        });

    }

    public void getSongList() {
        //retrieve song info
        ContentResolver musicResolver = getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null,
                null, null, null);
        Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");

        if (musicCursor != null && musicCursor.moveToFirst()) {
            int id = musicCursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int title = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int artist = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int album = musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
            // getting album id here
            int album_id = musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
            // using album id and base uri to get exact location of album art
            Uri uri = ContentUris.withAppendedId(sArtworkUri, album_id);
            Log.d("Uri :", uri.toString());
            // this log return following uri
            // D/Uri :: content://media/external/audio/albumart/13

            InputStream in = null;
            try {
                in = musicResolver.openInputStream(uri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            do {
                long ID = musicCursor.getLong(id);
                String titleName = musicCursor.getString(title);
                String artistName = musicCursor.getString(artist);
                String albumName = musicCursor.getString(album);
                library.add(new MusicModel(ID, titleName, artistName, albumName, in));
            } while (musicCursor.moveToNext());

        }

    }

//    public void getAlbum() {
////        String[] projection = { MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM,
////                MediaStore.Audio.Albums.ALBUM_ART};
////        ContentResolver resolver = getContentResolver();
////        Cursor cursor = resolver.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, projection, null,
////                null, MediaStore.Audio.Albums._ID+" desc");
////        if(cursor.moveToFirst()){
////            int _id;
////            String album ;
////            String albumArt ;
////            String songName;
////            String artist;
////            int numOfSongs;
////            int _idColumn = cursor.getColumnIndex(MediaStore.Audio.Albums._ID);
////            int albumColumn = cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM);
////            int albumArtColumn = cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
////            int titleColum = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
////            int artistColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
////            int numOfSongsColumn = cursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS);
//        final Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//        final String[] cursor_cols = {MediaStore.Audio.Media._ID,
//                MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.ALBUM,
//                MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DATA,
//                MediaStore.Audio.Media.ALBUM_ID,
//                MediaStore.Audio.Media.DURATION};
//        final String where = MediaStore.Audio.Media.IS_MUSIC + "=1";
//        final Cursor cursor = getContentResolver().query(uri,
//                cursor_cols, null, null, null);
//        while (cursor.moveToNext()) {
//            long _id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
//            String artist = cursor.getString(cursor
//                    .getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
//            String album = cursor.getString(cursor
//                    .getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
//            String track = cursor.getString(cursor
//                    .getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
//            String data = cursor.getString(cursor
//                    .getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
//            Long albumId = cursor.getLong(cursor
//                    .getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID));
//            int duration = cursor.getInt(cursor
//                    .getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
//
//            Uri sArtworkUri = Uri
//                    .parse("content://media/external/audio/albumart");
//            Uri albumArtUri = ContentUris.withAppendedId(sArtworkUri, albumId);
//
//            Log.d("Uri: ", albumArtUri.toString());
//
//            do {
////                _id = cursor.getInt(_idColumn);
////                album = cursor.getString(albumColumn);
////                albumArt = cursor.getString(albumArtColumn);
////                Log.d("Album: " , " "+album);
////                Log.d("Art: ",": " +albumArt);
//
//                library.add(new MusicModel(_id, track, artist, album, albumArtUri));
//
//            } while (cursor.moveToNext());
//        }
//    }
}
//cursor.close();




