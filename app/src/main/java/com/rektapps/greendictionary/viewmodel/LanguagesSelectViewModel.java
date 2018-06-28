package com.rektapps.greendictionary.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.rektapps.greendictionary.model.language.Language;
import com.rektapps.greendictionary.service.LanguageService;
import com.rektapps.greendictionary.viewmodel.util.SingleLiveEvent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class LanguagesSelectViewModel extends ViewModel {
    private LanguageService languageNameProvider;
    private SingleLiveEvent<Language> languageSelectedEvent = new SingleLiveEvent<>();
    private MutableLiveData<List<Language>> languagesLiveData = new MutableLiveData<>();

    @Inject
    LanguagesSelectViewModel(LanguageService languageNameProvider) {
        this.languageNameProvider = languageNameProvider;
        languagesLiveData.setValue(languageNameProvider.getLanguages());
    }

    public LiveData<List<Language>> getLanguagesLiveData() {
        return languagesLiveData;
    }


    public void filterLanguages(String query) {
        String trimmedLowercaseQuery = query.trim().toLowerCase();
        if (trimmedLowercaseQuery.length() == 0) {
            languagesLiveData.setValue(languageNameProvider.getLanguages());
        } else {
            List<Language> filteredLanguages = new ArrayList<>();
            for (Language language : languageNameProvider.getLanguages()) {
                if (language.getLanguageName().toLowerCase().contains(trimmedLowercaseQuery))
                    filteredLanguages.add(language);
            }
            languagesLiveData.setValue(filteredLanguages);
        }

    }

    public void onLanguageSelected(Language language) {
        languageSelectedEvent.setValue(language);
    }

    public LiveData<Language> getLanguageSelectedEvent() {
        return languageSelectedEvent;
    }
}
