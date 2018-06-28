package com.rektapps.greendictionary.storage.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.rektapps.greendictionary.model.entity.Example;

import java.util.List;

@Dao
public interface ExampleDao extends BaseDao<Example> {

    @Query("SELECT * FROM Example WHERE entryId = :entryId")
    List<Example> getExamplesByEntryId(long entryId);

}
