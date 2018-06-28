package com.rektapps.greendictionary.service;

import com.rektapps.greendictionary.model.language.Language;

import java.util.List;

public interface LanguageService {
    List<Language> getLanguages();

    Language getLanguageByCode(String languageCode);
}
