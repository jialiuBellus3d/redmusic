package com.itu.redmusic;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;

/**
 * Created by Jia Liu on 3/17/2019.
 */
public class PreferenceScreenFragment extends Fragment {

    private MainActivity mMainActivity;

    boolean isJazzPressed = false;
    boolean isBluePressed = false;
    boolean isHiphopPressed = false;
    boolean isElectricPressed = false;

    boolean ispopPressed = false;
    boolean isDubPressed = false;
    boolean isCountryPressed = false;
    boolean isClassicalPressed = false;

    boolean isRockPressed = false;
    boolean isRelaxingPressed = false;
    boolean isDancePressed = false;
    boolean isExePressed = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.preference, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {

        mMainActivity = (MainActivity) getActivity();

        Button startButton = view.findViewById(R.id.preferenceStartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMainActivity.mNavigationManager.startPlayerFragment();
            }
        });

        final Button jazzButton = view.findViewById(R.id.jazzButton);
        jazzButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isJazzPressed){
                    isJazzPressed = true;
                    jazzButton.setBackgroundResource(R.color.super_bright_blue);
                } else {
                    isJazzPressed = false;
                    jazzButton.setBackgroundResource(R.color.transparent);
                }
            }
        });

        final Button blueButton = view.findViewById(R.id.blueButton);
        blueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isBluePressed){
                    isBluePressed = true;
                    blueButton.setBackgroundResource(R.color.super_bright_blue);
                } else {
                    isBluePressed = false;
                    blueButton.setBackgroundResource(R.color.transparent);
                }
            }
        });

        final Button hiphopButton = view.findViewById(R.id.hiphopButton);
        hiphopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isHiphopPressed){
                    isHiphopPressed = true;
                    hiphopButton.setBackgroundResource(R.color.super_bright_blue);
                } else {
                    isHiphopPressed = false;
                    hiphopButton.setBackgroundResource(R.color.transparent);
                }
            }
        });

        final Button electricButton = view.findViewById(R.id.electricButton);
        electricButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isElectricPressed){
                    isElectricPressed = true;
                    electricButton.setBackgroundResource(R.color.super_bright_blue);
                } else {
                    isElectricPressed = false;
                    electricButton.setBackgroundResource(R.color.transparent);
                }
            }
        });

        final Button popButton = view.findViewById(R.id.popButton);
        popButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!ispopPressed){
                    ispopPressed = true;
                    popButton.setBackgroundResource(R.color.super_bright_blue);
                } else {
                    ispopPressed = false;
                    popButton.setBackgroundResource(R.color.transparent);
                }
            }
        });

        final Button dubstepButton = view.findViewById(R.id.DubstepButton);
        dubstepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isDubPressed){
                    isDubPressed = true;
                    dubstepButton.setBackgroundResource(R.color.super_bright_blue);
                } else {
                    isDubPressed = false;
                    dubstepButton.setBackgroundResource(R.color.transparent);
                }
            }
        });

        final Button countryButton = view.findViewById(R.id.CountryButton);
        countryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isCountryPressed){
                    isCountryPressed = true;
                    countryButton.setBackgroundResource(R.color.super_bright_blue);
                } else {
                    isCountryPressed = false;
                    countryButton.setBackgroundResource(R.color.transparent);
                }
            }
        });

        final Button classicalButton = view.findViewById(R.id.classicalButton);
        classicalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isClassicalPressed){
                    isClassicalPressed = true;
                    classicalButton.setBackgroundResource(R.color.super_bright_blue);
                } else {
                    isClassicalPressed = false;
                    classicalButton.setBackgroundResource(R.color.transparent);
                }
            }
        });

        final Button rockButton = view.findViewById(R.id.rockButton);
        rockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isRockPressed){
                    isRockPressed = true;
                    rockButton.setBackgroundResource(R.color.super_bright_blue);
                } else {
                    isRockPressed = false;
                    rockButton.setBackgroundResource(R.color.transparent);
                }
            }
        });

        final Button relaxingButton = view.findViewById(R.id.relaxingButton);
        relaxingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isRelaxingPressed){
                    isRelaxingPressed = true;
                    relaxingButton.setBackgroundResource(R.color.super_bright_blue);
                } else {
                    isRelaxingPressed = false;
                    relaxingButton.setBackgroundResource(R.color.transparent);
                }
            }
        });

        final Button danceButton = view.findViewById(R.id.danceButton);
        danceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isDancePressed){
                    isDancePressed = true;
                    danceButton.setBackgroundResource(R.color.super_bright_blue);
                } else {
                    isDancePressed = false;
                    danceButton.setBackgroundResource(R.color.transparent);
                }
            }
        });

        final Button exercisingButton = view.findViewById(R.id.exercisingButton);
        exercisingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isExePressed){
                    isExePressed = true;
                    exercisingButton.setBackgroundResource(R.color.super_bright_blue);
                } else {
                    isExePressed = false;
                    exercisingButton.setBackgroundResource(R.color.transparent);
                }
            }
        });
    }
}
