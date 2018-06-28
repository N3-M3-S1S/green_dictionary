package com.rektapps.greendictionary.storage.db;

import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import com.rektapps.greendictionary.model.entity.DictionaryEntry;
import com.rektapps.greendictionary.view.displayitem.ListItem;


@Dao
public interface DictionaryEntryDao extends BaseDao<DictionaryEntry> {

    @Transaction
    @Query("SELECT * from DictionaryEntry D LEFT JOIN Translation T on T.t_id= " +
            "(SELECT T1.t_id from Translation T1 where T1.entryId = D.id LIMIT 1) WHERE D.isHistory = 1 ORDER BY d.id DESC")
    DataSource.Factory<Integer, ListItem> getHistory();

    @Transaction
    @Query("SELECT * from DictionaryEntry D LEFT JOIN Translation T on T.t_id= " +
            "(SELECT T1.t_id from Translation T1 where T1.entryId = D.id LIMIT 1) WHERE D.isFavorite = 1 ORDER BY d.addedToFavoriteDate DESC")
    DataSource.Factory<Integer, ListItem> getFavorite();


}
