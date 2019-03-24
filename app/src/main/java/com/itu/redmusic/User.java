package com.itu.redmusic;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Jia Liu on 3/23/2019.
 */
@Entity
public class User {
    @PrimaryKey
    @NonNull
    public String userId;

    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "password")
    public String password;

    public User(@NonNull String email, String password) {
        this.email = email;
        this.password = password;
    }
}
