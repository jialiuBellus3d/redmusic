package com.itu.redmusic;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.concurrent.TimeUnit;

/**
 * Created by Jia Liu on 3/17/2019.
 */
public class PlayerScreenFragment extends Fragment {

    private MainActivity mMainActivity;

    private static int oTime =0, sTime =0, eTime =0, fTime = 5000, bTime = 5000;
    TextView startTimeTextView, endTimeTextView;
    ProgressBar progressBar;
    private Handler hdlr = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.music_player, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {

        mMainActivity = (MainActivity) getActivity();

        startTimeTextView = view.findViewById(R.id.playTimeElapsedTextview);
        endTimeTextView = view.findViewById(R.id.playTimeTotalTextview);
        progressBar = view.findViewById(R.id.playerProgressBar);

        final ImageButton playButton = view.findViewById(R.id.playerPlayButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mMainActivity, "Playing Audio", Toast.LENGTH_SHORT).show();
                if(!mMainActivity.mMusicPlayer.isNowPlaying) {
                    mMainActivity.mMusicPlayer.isNowPlaying = true;
                    mMainActivity.mMusicPlayer.playSound("sample", 1);
                    eTime = mMainActivity.mMusicPlayer.mPlayer.getDuration();
                    sTime = mMainActivity.mMusicPlayer.mPlayer.getCurrentPosition();
                    if (oTime == 0) {
                        progressBar.setMax(eTime);
                        oTime = 1;
                    }
                    endTimeTextView.setText(String.format("%d:%d", TimeUnit.MILLISECONDS.toMinutes(eTime),
                            TimeUnit.MILLISECONDS.toSeconds(eTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(eTime))));
                    startTimeTextView.setText(String.format("%d:%d", TimeUnit.MILLISECONDS.toMinutes(sTime),
                            TimeUnit.MILLISECONDS.toSeconds(sTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(sTime))));
                    progressBar.setProgress(sTime);
                    playButton.setBackgroundResource(R.drawable.pause);
                    hdlr.postDelayed(UpdateSongTime, 100);
                } else {
                    mMainActivity.mMusicPlayer.isNowPlaying = false;
                    playButton.setBackgroundResource(R.drawable.play);
                    mMainActivity.mMusicPlayer.mPlayer.pause();
                    Toast.makeText(mMainActivity.getApplicationContext(),"Pausing Audio", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImageButton menuButton = view.findViewById(R.id.playerMenuButton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                     // show the list view as dropdown
                mMainActivity.mMenuPopupWindow.showAsDropDown(view, -5, 0);
            }
        });

    }

    private Runnable UpdateSongTime = new Runnable() {
        @Override
        public void run() {
            sTime = mMainActivity.mMusicPlayer.mPlayer.getCurrentPosition();
            startTimeTextView.setText(String.format("%d:%d", TimeUnit.MILLISECONDS.toMinutes(sTime),
                    TimeUnit.MILLISECONDS.toSeconds(sTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(sTime))) );

            progressBar.setProgress(sTime);
            hdlr.postDelayed(this, 100);
        }
    };
}
