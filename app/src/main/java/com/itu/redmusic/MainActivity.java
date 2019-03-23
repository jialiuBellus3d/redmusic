package com.itu.redmusic;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by Jia Liu on 3/16/2019.
 */
public class MainActivity extends Activity {

    public NavigationManager mNavigationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationManager = new NavigationManager(getFragmentManager());
        mNavigationManager.startSplashScreen();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mNavigationManager.startSignInFragment();
            }
        }, 1000);
    }
}
