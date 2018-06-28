package com.rektapps.greendictionary.view.bindingadapter;

import android.app.Application;
import android.databinding.BindingAdapter;
import android.widget.TextView;

import com.rektapps.greendictionary.dagger.scope.DataBindingScope;
import com.rektapps.greendictionary.greendictionary.R;
import com.rektapps.greendictionary.service.LanguageService;

import javax.inject.Inject;

@DataBindingScope
public class BindingAdapters {
    private LanguageService languageService;
    private Application application;

    @Inject
    BindingAdapters(LanguageService languageService, Application application) {
        this.languageService = languageService;
        this.application = application;
    }


    @BindingAdapter({"bindLanguageFrom", "bindLanguageDest"})
    public void bindTranslation(TextView textView, String languageCodeFrom, String languageCodeDest) {
        textView.setText(application.getString(R.string.languages_names,
                languageService.getLanguageByCode(languageCodeFrom).getLanguageName(),
                languageService.getLanguageByCode(languageCodeDest).getLanguageName()));
    }


}
