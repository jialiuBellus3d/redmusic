package com.itu.redmusic;

/**
 * Created by Jia Liu on 3/17/2019.
 */

import android.app.Fragment;
import android.app.FragmentManager;
import android.util.Log;

import static android.content.ContentValues.TAG;

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
    private SignInScreenFragment signInFragment;
    private SignUpScreenFragment signUpFragment;
    private PreferenceScreenFragment preferenceFragment;
    private PlayerScreenFragment playerFragment;
    private PlaylistScreenFragment playlistFragment;
    private ProfileScreenFragment profileFragment;
    private SettingScreenFragment settingFragment;
    private ResetPasswordScreenFragment resetPasswordFragment;

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
            signInFragment = new SignInScreenFragment();
        }
        open(signInFragment);
    }

    void startSignUpFragment() {
        if (signUpFragment == null) {
            signUpFragment = new SignUpScreenFragment();
        }
        open(signUpFragment);
    }

    void startPreferenceFragment() {
        if (preferenceFragment == null) {
            preferenceFragment = new PreferenceScreenFragment();
        }
        open(preferenceFragment);
    }

    void startPlayerFragment() {
        if (playerFragment == null) {
            playerFragment = new PlayerScreenFragment();
        }
        open(playerFragment);
    }

    void startPlaylistFragment() {
        if (playlistFragment == null) {
            playlistFragment = new PlaylistScreenFragment();
        }
        open(playlistFragment);
    }

    void startProfileFragment() {
        if (profileFragment == null) {
            profileFragment = new ProfileScreenFragment();
        }
        open(profileFragment);
    }

    void startSettingFragment() {
        if (settingFragment == null) {
            settingFragment = new SettingScreenFragment();
        }
        open(settingFragment);
    }

    void startResetPasswordFragment() {
        if (resetPasswordFragment == null) {
            resetPasswordFragment = new ResetPasswordScreenFragment();
        }
        open(resetPasswordFragment);
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
