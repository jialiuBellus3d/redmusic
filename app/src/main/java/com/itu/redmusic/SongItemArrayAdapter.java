package com.itu.redmusic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jia Liu on 3/23/2019.
 */
public class SongItemArrayAdapter extends ArrayAdapter {
    private ArrayList<SongData> songsData;

    public SongItemArrayAdapter(Context context, ArrayList<SongData> songsData) {
        super(context, 0, songsData);
        this.songsData = songsData;
    }

    @Override
    public int getCount() {
        return this.songsData.size();
    }

    @Override
    public Object getItem(int index) {
        return this.songsData.get(index);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final SongData songData = songsData.get(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.playlist_song_item, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.playListItemName);
        TextView tvArtist = (TextView) convertView.findViewById(R.id.playListItemArtist);
        TextView tvRank = (TextView) convertView.findViewById(R.id.playListItemNumberTextView);
        final ImageButton addNewButton = (ImageButton) convertView.findViewById((R.id.playListItemAddNewButton));
        // Populate the data into the template view using the data object
        tvName.setText(songData.name);
        tvArtist.setText(songData.artist);
        tvRank.setText(String.valueOf(songData.rank));

        addNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewButton.setBackgroundResource(R.drawable.check);
            }
        });
        // Return the completed view to render on screen
        return convertView;
    }
}