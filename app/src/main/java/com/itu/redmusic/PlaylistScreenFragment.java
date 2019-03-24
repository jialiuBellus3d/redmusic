package com.itu.redmusic;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jia Liu on 3/17/2019.
 */
public class PlaylistScreenFragment extends Fragment {

    private MainActivity mMainActivity;
    private ArrayList<SongData> recommendedData;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.playlist, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {

        mMainActivity = (MainActivity) getActivity();

        ImageButton menuButton = view.findViewById(R.id.playlistMenuButton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // show the list view as dropdown
                mMainActivity.mMenuPopupWindow.showAsDropDown(view, -5, 0);
            }
        });

        recommendedData = new ArrayList<SongData>();
        readDatasets();
        final SongItemArrayAdapter adapter = new SongItemArrayAdapter(mMainActivity.getApplicationContext(), recommendedData);
        ListView listView = (ListView) view.findViewById(R.id.playlistDataListView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = adapter.getItem(i);
                mMainActivity.mNavigationManager.startPlayerFragment();

            }
        });
    }

    public void readDatasets(){
        InputStream inputStream = getResources().openRawResource(R.raw.export);
        CSVFile csvFile = new CSVFile(inputStream);
        List scoreList = csvFile.read();
        for (int i = 0; i < scoreList.size(); i++) {
            String[] res = (String[]) scoreList.get(i);
            if(res[3].equals("rank")) {
                continue;
            } else {
                int rank = Integer.parseInt(res[3]);
                float score = Float.parseFloat(res[2]);
                String[] artistAndSong = res[1].split(" - ");
                String artist = artistAndSong[0];
                String name = artistAndSong[1];

                SongData temp = new SongData(name, artist, score, rank);
                recommendedData.add(temp);
                System.out.println(recommendedData.get(i-1).toString());
            }
        }
    }
}
