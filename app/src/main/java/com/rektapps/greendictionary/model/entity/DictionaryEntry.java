package com.rektapps.greendictionary.model.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class DictionaryEntry implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String phrase;

    private String languageFromCode;

    private String languageDestCode;

    private boolean isHistory = true;

    private boolean isFavorite;

    private long addedToFavoriteDate;


    @Ignore
    public DictionaryEntry(String phrase, String languageFrom, String languageDest) {
        this.phrase = phrase;
        this.languageFromCode = languageFrom;
        this.languageDestCode = languageDest;
    }

    public DictionaryEntry() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public String getLanguageFromCode() {
        return languageFromCode;
    }

    public void setLanguageFromCode(String languageFromCode) {
        this.languageFromCode = languageFromCode;
    }

    public String getLanguageDestCode() {
        return languageDestCode;
    }

    public void setLanguageDestCode(String languageDestCode) {
        this.languageDestCode = languageDestCode;
    }

    public boolean isHistory() {
        return isHistory;
    }

    public void setHistory(boolean history) {
        isHistory = history;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public long getAddedToFavoriteDate() {
        return addedToFavoriteDate;
    }

    public void setAddedToFavoriteDate(long addedToFavoriteDate) {
        this.addedToFavoriteDate = addedToFavoriteDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DictionaryEntry entry = (DictionaryEntry) o;
        return id == entry.id;
    }

    @Override
    public int hashCode() {
        return (int) id * 31;
    }

    @Override
    public String toString() {
        return "DictionaryEntry{" +
                "id=" + id +
                ", phrase='" + phrase + '\'' +
                ", languageFromCode='" + languageFromCode + '\'' +
                ", languageDestCode='" + languageDestCode + '\'' +
                ", isHistory=" + isHistory +
                ", isFavorite=" + isFavorite +
                ", addedToFavoriteDate=" + addedToFavoriteDate +
                '}';
    }
}
