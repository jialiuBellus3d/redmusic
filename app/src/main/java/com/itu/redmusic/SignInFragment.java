package com.itu.redmusic;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Jia Liu on 3/17/2019.
 */
public class SignInFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.sign_in, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
    }
}
