package com.example.hasanzian.musicstruture;

import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    MediaMetadataRetriever retriever;
    final String MEDIA_PATH = "/sdcard/audio.mp3";
    ArrayList<MusicModel> library;
    Adaptor adaptor;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        library = new ArrayList<MusicModel>();
        library.add(new MusicModel("Shape Of You","Ed Sheeran","Divide"));
        library.add(new MusicModel("Hello","Adele","Single"));

        retriever = new MediaMetadataRetriever();

        try
        {
//            File file = new File(Environment.getExternalStorageDirectory().getPath());
//
//            if(file.listFiles(new FileExtensionFilter()) != null){
//              if(file.isDirectory()){
//                  for (File f :file.listFiles(new FileExtensionFilter())) {
//                      retriever.setDataSource(f.getPath());
//                      Log.d("Hasan"," : "+f);
//                      library.add(new MusicModel(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE),
//                              retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST),
//                              retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM),
//                              retriever.getEmbeddedPicture()));
//                  }
//              }
//            }

            Scan scan = new Scan();
            scan.execute();
        }
        catch (Exception e){
            library.add(new MusicModel("Unkown","Unkown", "Unkown"));
        }


        listView = findViewById(R.id.list);

        listView.setAdapter(adaptor);

    }




    private class Scan extends AsyncTask<Void,Void,ArrayList<MusicModel>>{

        private void rescan2(File f) {
            File[] file = f.listFiles();
            for (File ff : file) {
//                if (ff.isDirectory()) rescan2(f);
                if (ff.isFile() && ff.getPath().endsWith(".mp3")) {

                    retriever.setDataSource(ff.getPath());
                    library.add(new MusicModel(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE),
                            retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST),
                            retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM),
                            retriever.getEmbeddedPicture()));
                    Log.d("Hasan", ff.toString());
                }
            }

        }

        @Override
        protected ArrayList<MusicModel> doInBackground(Void... voids) {
            File file = new File(Environment.getExternalStorageDirectory().getPath());
            rescan2(file);
            return library;
        }


        @Override
        protected void onPostExecute(ArrayList<MusicModel> musicModels) {
            super.onPostExecute(musicModels);
            adaptor = new Adaptor(getApplicationContext(),library);
            listView.setAdapter(adaptor);

        }
    }













//    private void reScan(File f) {
//        File[] file = f.listFiles();
//        for (File ff : file) {
//            if (ff.isDirectory()) reScan(f);
//            if (ff.isFile() && ff.getPath().endsWith(".mp3")) {
//
//                retriever.setDataSource(ff.getPath());
//                library.add(new MusicModel(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE),
//                        retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST),
//                        retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM),
//                        retriever.getEmbeddedPicture()));
//                Log.d("Hasan", ff.toString());
//            }
//        }
//    }


    private class FileExtensionFilter implements FilenameFilter{
        @Override
        public boolean accept(File file, String name) {
            return (name.endsWith(".mp3") || name.endsWith(".MP3"));
        }
    }

//


}
