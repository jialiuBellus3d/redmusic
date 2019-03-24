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

import static android.content.ContentValues.TAG;

/**
 * Created by Jia Liu on 3/17/2019.
 */
public class SignInScreenFragment extends Fragment {
    private MainActivity mMainActivity;
    EditText emailET, passwordET;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.sign_in, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        mMainActivity = (MainActivity) getActivity();
        emailET = view.findViewById(R.id.email_input);
        passwordET = view.findViewById(R.id.password_input);

        Button signInButton = view.findViewById(R.id.signInButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailET.getText().toString();
                String password = passwordET.getText().toString();
                mMainActivity.mCurrentUser = new User(email, password);
                new SignInAsyncTask(mMainActivity, mMainActivity.mCurrentUser).execute();
            }
        });

        Button forgotButton = view.findViewById(R.id.forgetButton);
        forgotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMainActivity.mNavigationManager.startResetPasswordFragment();
            }
        });

        Button signUpButton = view.findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMainActivity.mNavigationManager.startSignUpFragment();
            }
        });
    }

    private static class SignInAsyncTask extends AsyncTask<Void, Void, Integer> {

        //Prevent leak
        private MainActivity mMainActivity;
        private User user;

        public SignInAsyncTask(MainActivity activity, User user) {
            mMainActivity = activity;
            this.user = user;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            UserDao userDao = mMainActivity.userDao;
            User user = userDao.findByEmail(this.user.email);
            if(user!=null && this.user.password.equals(user.password)){
                Log.e(TAG, "log in successfully!");
                mMainActivity.mCurrentUser.userId = user.userId;
                return 1;
            }
            Log.e(TAG, "log in failed!");
            return 0;
        }

        @Override
        protected void onPostExecute(Integer agentsCount) {
            if(mMainActivity == null) {
                return;
            }

            if (agentsCount > 0) {
                //2: If it already exists then prompt user
                Toast.makeText(mMainActivity, "Log in successfully!", Toast.LENGTH_LONG).show();
                mMainActivity.mNavigationManager.startPlayerFragment();
                new HttpRequest(mMainActivity, "http://134.209.5.65:5000/retrieve", this.user.userId).execute();

            } else {
                Toast.makeText(mMainActivity, "Wrong User Name or Password!", Toast.LENGTH_LONG).show();
            }
        }
    }
}
