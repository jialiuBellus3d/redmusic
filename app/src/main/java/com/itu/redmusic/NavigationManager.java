package com.itu.redmusic;

/**
 * Created by Jia Liu on 3/17/2019.
 */

import android.app.Fragment;
import android.app.FragmentManager;
import android.util.Log;

/**
 * Navigate across different fragments.
 */

public class NavigationManager {

    private static final String LOGTAG = "NavigationManager";


    /**
     * Listener interface for navigation events.
     */
    public interface NavigationListener {

        /**
         * Callback on backstack changed.
         */
        void onBackstackChanged();
    }

    private FragmentManager mFragmentManager;

    private NavigationListener mNavigationListener;


    private SplashScreenFragment splashScreen;
    private SignInFragment signInFragment;

    /**
     * Initialize the NavigationManager with a FragmentManager, which will be used at the
     * fragment transactions.
     *
     */
    public NavigationManager(FragmentManager fragmentManager) {

        mFragmentManager = fragmentManager;

        mFragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (mNavigationListener != null) {
                    mNavigationListener.onBackstackChanged();
                }
            }
        });
    }

    void startSplashScreen() {
        if (splashScreen == null) {
            splashScreen = new SplashScreenFragment();
        }
        open(splashScreen);
    }

    void startSignInFragment() {
        if (signInFragment == null) {
            signInFragment = new SignInFragment();
        }
        open(signInFragment);
    }

    /**
     * Displays the next fragment
     *
     * @param fragment
     */
    private void open(Fragment fragment) {

        Log.d(LOGTAG, "open fragment called");
        if (mFragmentManager != null) {
            mFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
    }

}
