package com.itu.redmusic;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

/**
 * Created by Jia Liu on 3/17/2019.
 */
public class PlaylistScreenFragment extends Fragment {

    private MainActivity mMainActivity;

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
    }
}
