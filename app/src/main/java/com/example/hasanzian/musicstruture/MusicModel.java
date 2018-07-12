package com.example.hasanzian.musicstruture;

/**
 * Created by hasanZian on 12-07-2018.
 */

public class MusicModel {
    private String mSongName;
    private String mArtist;
    private String mAlbum;
    private byte[] mCover;

    public MusicModel(String mSongName, String mArtist, String mAlbum, byte[] mCover) {
        this.mSongName = mSongName;
        this.mArtist = mArtist;
        this.mAlbum = mAlbum;
        this.mCover = mCover;
    }

    public MusicModel(String mSongName, String mArtist, String mAlbum) {
        this.mSongName = mSongName;
        this.mArtist = mArtist;
        this.mAlbum = mAlbum;
    }

    public String getmSongName() {
        return mSongName;
    }

    public String getmArtist() {
        return mArtist;
    }

    public String getmAlbum() {
        return mAlbum;
    }

    public byte[] getmCover() {
        return mCover;
    }
}
