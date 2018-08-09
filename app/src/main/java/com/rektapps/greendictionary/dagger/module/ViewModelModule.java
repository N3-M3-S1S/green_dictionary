package com.rektapps.greendictionary.dagger.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.rektapps.greendictionary.dagger.key.ViewModelKey;
import com.rektapps.greendictionary.viewmodel.DictionaryListViewModel;
import com.rektapps.greendictionary.viewmodel.EntryScreenViewModel;
import com.rektapps.greendictionary.viewmodel.LanguagesSelectViewModel;
import com.rektapps.greendictionary.viewmodel.MainViewViewModel;
import com.rektapps.greendictionary.viewmodel.factory.ViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module(includes = MainScreenModule.class)
public interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewViewModel.class)
    ViewModel mainViewModel(MainViewViewModel mainScreenViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(DictionaryListViewModel.class)
    ViewModel providesListViewModel(DictionaryListViewModel dictionaryListViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(LanguagesSelectViewModel.class)
    ViewModel providesLanguageSelectViewModel(LanguagesSelectViewModel languagesSelectViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(EntryScreenViewModel.class)
    ViewModel entryScreenViewModel(EntryScreenViewModel entryScreenViewModel);

    @Binds
    ViewModelProvider.Factory bindsFactory(ViewModelFactory factory);

}
