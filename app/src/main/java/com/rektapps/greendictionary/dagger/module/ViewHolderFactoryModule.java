package com.rektapps.greendictionary.dagger.module;

import com.rektapps.greendictionary.view.adapter.viewholder.TranslationViewHolder;
import com.rektapps.greendictionary.view.adapter.viewholder.factory.TranslationViewHolderFactory;
import com.rektapps.greendictionary.view.adapter.viewholder.factory.ViewHolderFactory;
import com.rektapps.greendictionary.view.adapter.viewholder.factory.impl.TranslationWithMeaningsViewHolderFactory;
import com.rektapps.greendictionary.view.adapter.viewholder.factory.impl.TranslationWithoutMeaningsViewHolderFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntKey;
import dagger.multibindings.IntoMap;

@Module
public interface ViewHolderFactoryModule {

    @Binds
    @IntoMap
    @IntKey(TranslationViewHolderFactory.TRANSLATION_WITHOUT_MEANINGS)
    ViewHolderFactory<TranslationViewHolder> translationWithoutMeaningsViewHolderFactory(TranslationWithoutMeaningsViewHolderFactory factory);

    @Binds
    @IntoMap
    @IntKey(TranslationViewHolderFactory.TRANSLATION_WITH_MEANINGS)
    ViewHolderFactory<TranslationViewHolder> translationWithMeaningsViewHolderFactory(TranslationWithMeaningsViewHolderFactory factory);


}
