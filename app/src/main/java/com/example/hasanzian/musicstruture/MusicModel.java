package com.example.hasanzian.musicstruture;

/**
 * Created by hasanZian on 12-07-2018.
 */

public class MusicModel {
    private long mID;
    private String mSongName;
    private String mArtist;
    private String mAlbum;
    private String mCover;

    public MusicModel(long mID, String mSongName, String mArtist, String mAlbum, String mCover) {
        this.mID = mID;
        this.mSongName = mSongName;
        this.mArtist = mArtist;
        this.mAlbum = mAlbum;
        this.mCover = mCover;
    }

    public MusicModel(String mSongName, String mArtist, String mAlbum, String mCover) {
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

    public String getmCover() {
        return mCover;
    }

    public long getmID() {
        return mID;
    }
}
