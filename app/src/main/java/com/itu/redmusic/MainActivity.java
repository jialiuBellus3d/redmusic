package com.itu.redmusic;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jia Liu on 3/16/2019.
 */
public class MainActivity extends Activity{

    public NavigationManager mNavigationManager;
    public MusicPlayer mMusicPlayer;
    public PopupWindow mMenuPopupWindow;
    UserDatabase mDatabase;
    UserDao userDao;
    User mCurrentUser;
    HashMap<String, Boolean> mUserIdMap;

    String mPopUpContents[];

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
//                mNavigationManager.startPlayerFragment();
//                mNavigationManager.startPlaylistFragment();
            }
        }, 1000);

        mMusicPlayer = new MusicPlayer(this);
        List<String>mMenuList = new ArrayList<String>();
        mMenuList.add("Preference::1");
        mMenuList.add("Playlist::2");
        mMenuList.add("Account::3");
        mPopUpContents = new String[mMenuList.size()];
        mMenuList.toArray(mPopUpContents);
        mMenuPopupWindow = popupWindowDogs();

        mDatabase = UserDatabase.buildDatabase(this);
        userDao = mDatabase.userDao();


        // hide the navigation bar for consistent UI
        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        // This work only for android 4.4+
        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().getDecorView().setSystemUiVisibility(flags);
            // Code below is to handle presses of Volume up or Volume down.
            // Without this, after pressing volume buttons, the navigation bar will
            // show up and won't hide
            final View decorView = getWindow().getDecorView();
            decorView
                    .setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
                        @Override
                        public void onSystemUiVisibilityChange(int visibility) {
                            if((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                                decorView.setSystemUiVisibility(flags);
                            }
                        }
                    });
        }
    }

    private ArrayAdapter<String> dogsAdapter(String dogsArray[]) {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dogsArray) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                // setting the ID and text for every items in the list
                String item = getItem(position);
                String[] itemArr = item.split("::");
                String text = itemArr[0];
                String id = itemArr[1];

                // visual settings for the list item
                TextView listItem = new TextView(MainActivity.this);

                listItem.setText(text);
                listItem.setTag(id);
                listItem.setTextSize(20);
                listItem.setPadding(10, 10, 10, 10);
                listItem.setTextColor(Color.WHITE);

                return listItem;
            }
        };

        return adapter;
    }
    public PopupWindow popupWindowDogs() {

        // initialize a pop up window type
        PopupWindow popupWindow = new PopupWindow(this);

        // the drop down list is a list view
        ListView listViewMenu = new ListView(this);

        // set our adapter and pass our pop up window contents
        listViewMenu.setAdapter(dogsAdapter(mPopUpContents));

        // set the item click listener
        listViewMenu.setOnItemClickListener(new MenuDropdownOnItemClickListener());

        // some other visual settings
        popupWindow.setFocusable(true);
        popupWindow.setWidth(500);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

        // set the list view as pop up window content
        popupWindow.setContentView(listViewMenu);

        return popupWindow;
    }

    void loadUserId(){
        mUserIdMap = new HashMap<String, Boolean>();
        InputStream inputStream = getResources().openRawResource(R.raw.user);
        CSVFile csvFile = new CSVFile(inputStream);
        List idList = csvFile.read();
        for (int i = 0; i < idList.size(); i++) {
            String[] res = (String[]) idList.get(i);
            for(int j = 0; j < res.length; j++){
                mUserIdMap.put(res[j], false);
            }
        }
    }
}
