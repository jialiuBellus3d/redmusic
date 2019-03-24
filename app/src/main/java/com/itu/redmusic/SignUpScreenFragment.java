package com.itu.redmusic;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.content.ContentValues.TAG;

/**
 * Created by Jia Liu on 3/17/2019.
 */
public class SignUpScreenFragment extends Fragment {
    private MainActivity mMainActivity;
    EditText emailET, passwordET;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.sign_up, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        mMainActivity = (MainActivity) getActivity();

        Button signUpButton = view.findViewById(R.id.signUpSubmitButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailET.getText().toString();
                String password = passwordET.getText().toString();
                mMainActivity.mCurrentUser = new User(email, password);

                List<String> keysAsArray = new ArrayList<String>(mMainActivity.mUserIdMap.keySet());
                Random r = new Random();
                String randomId = keysAsArray.get(r.nextInt(keysAsArray.size()));
                boolean randomVal = mMainActivity.mUserIdMap.get(randomId);
                while(randomVal){
                    randomId = keysAsArray.get(r.nextInt(keysAsArray.size()));
                    randomVal = mMainActivity.mUserIdMap.get(randomId);
                }

                mMainActivity.mCurrentUser.userId = randomId;
                mMainActivity.mUserIdMap.put(randomId, true);
                new SignUpAsyncTask(mMainActivity, mMainActivity.mCurrentUser).execute();
            }
        });

        emailET = view.findViewById(R.id.signUpEmail_input);
        passwordET = view.findViewById(R.id.signUpPassword_input);
    }

    private static class SignUpAsyncTask extends AsyncTask<Void, Void, Integer> {

        //Prevent leak
        private MainActivity mMainActivity;
        private User user;

        public SignUpAsyncTask(MainActivity activity, User user) {
            mMainActivity = activity;
            this.user = user;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            UserDao userDao = mMainActivity.userDao;
            User user = userDao.findByEmail(this.user.email);
            if(user==null){
                Log.e(TAG, "sign up successfully!");
                userDao.add(mMainActivity.mCurrentUser);
                return 1;
            }
            Log.e(TAG, "sign up failed!");
            return 0;
        }

        @Override
        protected void onPostExecute(Integer agentsCount) {
            if(mMainActivity == null) {
                return;
            }

            if (agentsCount > 0) {
                //2: If it already exists then prompt user
                Toast.makeText(mMainActivity, "Sign up successfully!", Toast.LENGTH_LONG).show();
                mMainActivity.mNavigationManager.startPreferenceFragment();
            } else {
                Toast.makeText(mMainActivity, "User already exist! Please sign in", Toast.LENGTH_LONG).show();
                mMainActivity.mNavigationManager.startSignInFragment();
            }
        }
    }

}
