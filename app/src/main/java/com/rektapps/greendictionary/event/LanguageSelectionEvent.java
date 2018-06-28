package com.rektapps.greendictionary.event;

import com.rektapps.greendictionary.model.language.Language;
import com.rektapps.greendictionary.model.language.LanguageType;

public class LanguageSelectionEvent {
    private LanguageType languageType;
    private Language selectedLanguage;

    public LanguageSelectionEvent(LanguageType languageType, Language selectedLanguage) {
        this.languageType = languageType;
        this.selectedLanguage = selectedLanguage;
    }

    public LanguageType getLanguageType() {
        return languageType;
    }

    public Language getSelectedLanguage() {
        return selectedLanguage;
    }
}
