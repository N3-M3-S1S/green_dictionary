package com.rektapps.greendictionary.dagger.module;

import com.rektapps.greendictionary.storage.db.DictionaryDatabase;
import com.rektapps.greendictionary.storage.db.DictionaryEntryDao;
import com.rektapps.greendictionary.storage.db.ExampleDao;
import com.rektapps.greendictionary.storage.db.TranslationDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class DaoModule {

    @Provides
    @Singleton
    static DictionaryEntryDao providesWordsDao(DictionaryDatabase database) {
        return database.wordsDao();
    }

    @Provides
    @Singleton
    static TranslationDao providesTranslationDao(DictionaryDatabase database) {
        return database.translationDao();
    }

    @Provides
    @Singleton
    static ExampleDao providesExamplesDao(DictionaryDatabase database) {
        return database.examplesDao();
    }


}
