package com.rektapps.greendictionary.viewmodel.shared.eventviewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.rektapps.greendictionary.event.LanguageSelectionEvent;
import com.rektapps.greendictionary.model.language.Language;
import com.rektapps.greendictionary.model.language.LanguageType;
import com.rektapps.greendictionary.viewmodel.util.SingleLiveEvent;

public class LanguageSelectionEventViewModel extends ViewModel {
    private MutableLiveData<LanguageSelectionEvent> languageSelectionEvent = new SingleLiveEvent<>();

    public LiveData<LanguageSelectionEvent> getLanguageSelectionEvent() {
        return languageSelectionEvent;
    }

    public void onLanguageSelected(LanguageType languageType, Language selectedLanguage) {
        languageSelectionEvent.setValue(new LanguageSelectionEvent(languageType, selectedLanguage));
    }


}
