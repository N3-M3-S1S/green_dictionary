package com.rektapps.greendictionary.storage.db;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Update;

import java.util.Collection;

public interface BaseDao<T> {
    @Insert
    long insert(T t);

    @Insert
    void insert(Collection<T> t);

    @Delete
    void delete(T t);

    @Delete
    void delete(Collection<T> t);

    @Update
    void update(T t);

    @Update
    void update(Collection<T> t);

}
