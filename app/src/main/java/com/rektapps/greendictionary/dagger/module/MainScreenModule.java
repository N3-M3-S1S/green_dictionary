package com.rektapps.greendictionary.dagger.module;

import com.rektapps.greendictionary.messages.ApiErrorMessageCreator;
import com.rektapps.greendictionary.messages.impl.ApiErrorMessageCreatorImpl;
import com.rektapps.greendictionary.storage.SelectedLanguageStorage;
import com.rektapps.greendictionary.storage.impl.SharedPreferencesSelectedLanguageStorage;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class MainScreenModule {

    @Binds
    abstract ApiErrorMessageCreator bindsApiErrorMessageCreator(ApiErrorMessageCreatorImpl apiErrorMessageCreator);

    @Binds
    abstract SelectedLanguageStorage bindsSelectedLanguageStorage(SharedPreferencesSelectedLanguageStorage selectedLanguageStorage);


}

