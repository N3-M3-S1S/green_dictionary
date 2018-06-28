package com.rektapps.greendictionary.service.impl;

import android.content.Context;
import android.content.res.Resources;

import com.rektapps.greendictionary.greendictionary.R;
import com.rektapps.greendictionary.model.language.Language;
import com.rektapps.greendictionary.service.LanguageService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class LanguageServiceImpl implements LanguageService {
    private List<Language> languages = new ArrayList<>();

    @Inject
    LanguageServiceImpl(Context context) {
        Resources resources = context.getResources();
        String packageName = context.getPackageName();
        String[] languageCodes = resources.getStringArray(R.array.language_codes);
        for (String languageCode : languageCodes) {
            String languageName = resources.getString(resources.getIdentifier(languageCode, "string", packageName));
            languages.add(new Language(languageCode, languageName));
        }
    }

    @Override
    public List<Language> getLanguages() {
        return languages;
    }

    @Override
    public Language getLanguageByCode(String languageCode) {
        for (Language language : languages)
            if (language.getLanguageCode().equals(languageCode))
                return language;

        return null;
    }


}
