package com.rektapps.greendictionary.storage;

import com.rektapps.greendictionary.model.language.Language;
import com.rektapps.greendictionary.model.language.LanguageType;

public interface SelectedLanguageStorage {
    void saveLanguage(LanguageType type, Language language);

    Language restoreLanguage(LanguageType type);

}

