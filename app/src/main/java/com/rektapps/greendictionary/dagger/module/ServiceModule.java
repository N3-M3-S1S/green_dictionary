package com.rektapps.greendictionary.dagger.module;

import com.rektapps.greendictionary.service.ClipboardService;
import com.rektapps.greendictionary.service.DictionaryService;
import com.rektapps.greendictionary.service.LanguageService;
import com.rektapps.greendictionary.service.impl.ClipboardServiceImpl;
import com.rektapps.greendictionary.service.impl.DictionaryServiceImpl;
import com.rektapps.greendictionary.service.impl.LanguageServiceImpl;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public interface ServiceModule {

    @Singleton
    @Binds
    DictionaryService providesEntryService(DictionaryServiceImpl dictionaryService);

    @Singleton
    @Binds
    ClipboardService providesClipboardService(ClipboardServiceImpl clipboardService);

    @Singleton
    @Binds
    LanguageService providesLanguageService(LanguageServiceImpl languageService);

}
