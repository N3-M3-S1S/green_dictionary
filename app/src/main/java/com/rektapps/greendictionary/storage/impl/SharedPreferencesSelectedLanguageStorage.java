package com.rektapps.greendictionary.storage.impl;

import android.content.SharedPreferences;

import com.rektapps.greendictionary.model.language.Language;
import com.rektapps.greendictionary.model.language.LanguageType;
import com.rektapps.greendictionary.service.LanguageService;
import com.rektapps.greendictionary.storage.SelectedLanguageStorage;

import javax.inject.Inject;

public class SharedPreferencesSelectedLanguageStorage implements SelectedLanguageStorage {
    private SharedPreferences sharedPreferences;
    private LanguageService languageService;

    @Inject
    SharedPreferencesSelectedLanguageStorage(SharedPreferences sharedPreferences, LanguageService languageService) {
        this.sharedPreferences = sharedPreferences;
        this.languageService = languageService;
    }


    @Override
    public void saveLanguage(LanguageType type, Language language) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(type.name(), language.getLanguageCode());
        editor.apply();
    }

    @Override
    public Language restoreLanguage(LanguageType type) {
        return languageService.getLanguageByCode(sharedPreferences.getString(type.name(), null));

    }


}
