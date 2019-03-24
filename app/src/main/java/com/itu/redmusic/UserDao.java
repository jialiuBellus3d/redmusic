package com.itu.redmusic;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Jia Liu on 3/23/2019.
 */
@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE userId IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE email LIKE :email AND " +
            "password LIKE :password LIMIT 1")
    User findByBoth(String email, String password);

    @Query("SELECT * FROM user WHERE email LIKE :email LIMIT 1")
    User findByEmail(String email);

    @Insert()
    long add(User user);

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);
}
