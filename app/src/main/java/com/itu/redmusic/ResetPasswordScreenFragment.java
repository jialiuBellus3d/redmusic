package com.itu.redmusic;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Jia Liu on 3/17/2019.
 */
public class ResetPasswordScreenFragment extends Fragment {
    private MainActivity mMainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.reset_password, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        mMainActivity = (MainActivity) getActivity();

        Button resetButton = view.findViewById(R.id.resetSubmitButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMainActivity.mNavigationManager.startSignInFragment();
            }
        });
    }
}
