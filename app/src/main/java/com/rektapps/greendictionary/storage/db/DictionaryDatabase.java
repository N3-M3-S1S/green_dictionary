package com.rektapps.greendictionary.storage.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.rektapps.greendictionary.model.entity.DictionaryEntry;
import com.rektapps.greendictionary.model.entity.Example;
import com.rektapps.greendictionary.model.entity.Translation;

@Database(entities = {DictionaryEntry.class, Translation.class, Example.class}, version = 1, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class DictionaryDatabase extends RoomDatabase {
    public abstract DictionaryEntryDao wordsDao();

    public abstract TranslationDao translationDao();

    public abstract ExampleDao examplesDao();
}
