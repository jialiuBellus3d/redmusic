package com.itu.redmusic;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;


/**
 * Created by Jia Liu on 3/23/2019.
 */
@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {

    private static UserDatabase sInstance;

    public abstract UserDao userDao() ;

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();
    public static final String DATABASE_NAME = "database-user";

    public static UserDatabase getInstance(final Context context) {
        if (sInstance == null) {
            synchronized (UserDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext());
                    sInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    public static UserDatabase buildDatabase(final Context appContext) {
        return Room.databaseBuilder(appContext, UserDatabase.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        UserDatabase database = UserDatabase.getInstance(appContext);
                            // notify that the database was created and it's ready to be used
                            database.setDatabaseCreated();
                    }
                })
                .build();
    }
    /**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated(){
        mIsDatabaseCreated.postValue(true);
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }
}
