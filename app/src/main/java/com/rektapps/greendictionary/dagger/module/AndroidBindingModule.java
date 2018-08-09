package com.rektapps.greendictionary.dagger.module;

import com.rektapps.greendictionary.view.DictionaryListFragment;
import com.rektapps.greendictionary.view.EntryScreen;
import com.rektapps.greendictionary.view.LanguageSelectDialog;
import com.rektapps.greendictionary.view.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AndroidBindingModule {

    @ContributesAndroidInjector
    abstract MainActivity mainActivity();

    @ContributesAndroidInjector
    abstract DictionaryListFragment listFragment();

    @ContributesAndroidInjector
    abstract EntryScreen entryScreenDialog();

    @ContributesAndroidInjector
    abstract LanguageSelectDialog languageSelectDialog();

}
