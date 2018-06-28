package com.rektapps.greendictionary.dagger.module;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;

import com.rektapps.greendictionary.dagger.component.DataBindingSubComponent;
import com.rektapps.greendictionary.storage.db.DictionaryDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module(subcomponents = DataBindingSubComponent.class)
public abstract class AppModule {

    @Singleton
    @Provides
    static DictionaryDatabase providesDatabase(Application application) {
        return Room.databaseBuilder(application, DictionaryDatabase.class, "db").build();
    }

    @Singleton
    @Provides
    static SharedPreferences providesSharedPreferences(Application application) {
        return application.getSharedPreferences("prefs", Context.MODE_PRIVATE);
    }

    @Singleton
    @Binds
    abstract Context providesApplicationContext(Application application);

    @Singleton
    @Provides
    static ExecutorService providesExecutorService(){
        return Executors.newSingleThreadExecutor();
    }


}
