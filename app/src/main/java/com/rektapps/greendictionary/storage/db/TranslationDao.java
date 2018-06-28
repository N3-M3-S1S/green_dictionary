package com.rektapps.greendictionary.storage.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.rektapps.greendictionary.model.entity.Translation;

import java.util.List;

@Dao
public interface TranslationDao extends BaseDao<Translation> {

    @Query("SELECT * FROM Translation WHERE entryId = :entryId")
    List<Translation> getTranslationsByEntryId(long entryId);


}
