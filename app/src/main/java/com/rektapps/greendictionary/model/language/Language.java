package com.rektapps.greendictionary.model.language;

public class Language {
    private String languageCode;

    private String languageName;

    public Language(String languageCode, String languageName) {
        this.languageCode = languageCode;
        this.languageName = languageName;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public String getLanguageName() {
        return languageName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Language language = (Language) o;

        return (languageCode != null ? languageCode.equals(language.languageCode) : language.languageCode == null)
                && (languageName != null ? languageName.equals(language.languageName) : language.languageName == null);
    }

    @Override
    public int hashCode() {
        int result = languageCode != null ? languageCode.hashCode() : 0;
        result = 31 * result + (languageName != null ? languageName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Language{" +
                "languageCode='" + languageCode + '\'' +
                ", languageName='" + languageName + '\'' +
                '}';
    }
}
