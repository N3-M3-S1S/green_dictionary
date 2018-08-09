package com.rektapps.greendictionary.dagger.module;

import com.rektapps.greendictionary.model.entity.Example;
import com.rektapps.greendictionary.model.entity.Translation;
import com.rektapps.greendictionary.model.language.Language;
import com.rektapps.greendictionary.view.adapter.impl.TranslationsAdapter;
import com.rektapps.greendictionary.view.adapter.viewholder.factory.ViewModelViewHolderFactory;
import com.rektapps.greendictionary.view.adapter.viewholder.factory.impl.ExamplesViewHolderFactory;
import com.rektapps.greendictionary.view.adapter.viewholder.factory.impl.LanguagesViewHolderFactory;
import com.rektapps.greendictionary.view.adapter.viewholder.factory.impl.TranslationWithMeaningsViewHolderFactory;
import com.rektapps.greendictionary.view.adapter.viewholder.factory.impl.TranslationWithoutMeaningsViewHolderFactory;
import com.rektapps.greendictionary.viewmodel.EntryScreenViewModel;
import com.rektapps.greendictionary.viewmodel.LanguagesSelectViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntKey;
import dagger.multibindings.IntoMap;

@Module
public interface ViewHolderFactoryModule {

    @Binds
    @IntoMap
    @IntKey(TranslationsAdapter.TRANSLATION_WITH_MEANINGS)
    ViewModelViewHolderFactory<EntryScreenViewModel, Translation> bindsTranslationsWithMeaningsViewHolderFactory(TranslationWithMeaningsViewHolderFactory factory);

    @Binds
    @IntoMap
    @IntKey(TranslationsAdapter.TRANSLATION_WITHOUT_MEANINGS)
    ViewModelViewHolderFactory<EntryScreenViewModel, Translation> bindsTranslationsWithoutMeaningsViewHolderFactory(TranslationWithoutMeaningsViewHolderFactory factory);

    @Binds
    ViewModelViewHolderFactory<EntryScreenViewModel, Example> bindsExampleViewHolderFactory(ExamplesViewHolderFactory factory);

    @Binds
    ViewModelViewHolderFactory<LanguagesSelectViewModel, Language> bindsLanguageViewHolderFactory(LanguagesViewHolderFactory factory);


}
