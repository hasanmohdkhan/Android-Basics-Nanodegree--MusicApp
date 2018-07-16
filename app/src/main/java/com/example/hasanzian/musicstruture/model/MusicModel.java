package com.example.hasanzian.musicstruture.model;

/**
 * Model class for data structure
 */

public class MusicModel {
    private String mID;
    private String mSongName;
    private String mArtist;
    private String mAlbum;
    private String mCover;

    public MusicModel(String mID, String mSongName, String mArtist, String mAlbum, String mCover) {
        this.mID = mID;
        this.mSongName = mSongName;
        this.mArtist = mArtist;
        this.mAlbum = mAlbum;
        this.mCover = mCover;
    }

    public String getSongName() {
        return mSongName;
    }

    public String getArtist() {
        return mArtist;
    }

    public String getAlbum() {
        return mAlbum;
    }

    public String getCover() {
        return mCover;
    }

    public String getID() {
        return mID;
    }
}