package com.itu.redmusic;

/**
 * Created by Jia Liu on 3/23/2019.
 */
class SongData {
    public String name;
    public String artist;
    public float score;
    public int rank;

    public SongData(){};

    public SongData(String name, String artist, float score, int rank) {
        this.name = name;
        this.artist = artist;
        this.score = score;
        this.rank = rank;
    }

    // for debugging
    public String toString(){
        return name + "/" + artist + "/" + score + "/" + rank;
    }
}
