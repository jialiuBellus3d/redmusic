package com.itu.redmusic;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Integer.*;

/**
 * Created by Jia Liu on 3/23/2019.
 */
public class MenuDropdownOnItemClickListener implements AdapterView.OnItemClickListener {

    String TAG = "MenuDropdownOnItemClickListener";
    @Override
    public void onItemClick(AdapterView<?> arg0, View v, int arg2, long arg3) {

        // get the context and main activity to access variables
        Context mContext = v.getContext();
        MainActivity mainActivity = ((MainActivity) mContext);

        // add some animation when a list item was clicked
        Animation fadeInAnimation = AnimationUtils.loadAnimation(v.getContext(), android.R.anim.fade_in);
        fadeInAnimation.setDuration(10);
        v.startAnimation(fadeInAnimation);

        // dismiss the pop up
        mainActivity.mMenuPopupWindow.dismiss();

        // get the text and set it as the button text
        String selectedItemText = ((TextView) v).getText().toString();

        // get the id
        Toast.makeText(mContext, "ID is: " + ((TextView) v).getTag().toString(), Toast.LENGTH_SHORT).show();
        Log.e(TAG, selectedItemText);
        if(selectedItemText.equals("Preference")){
            mainActivity.mNavigationManager.startPreferenceFragment();
        } else if(selectedItemText.equals("Playlist")){
            mainActivity.mNavigationManager.startPlaylistFragment();
        } else if(selectedItemText.equals("Account")){
            mainActivity.mNavigationManager.startProfileFragment();
        } else {
        }
    }

}
