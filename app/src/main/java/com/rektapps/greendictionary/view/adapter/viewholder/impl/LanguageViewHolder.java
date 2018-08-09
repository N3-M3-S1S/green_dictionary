package com.rektapps.greendictionary.view.adapter.viewholder.impl;

import com.rektapps.greendictionary.greendictionary.databinding.LanguageItemBinding;
import com.rektapps.greendictionary.model.language.Language;
import com.rektapps.greendictionary.view.adapter.viewholder.BaseViewModelViewHolder;
import com.rektapps.greendictionary.viewmodel.LanguagesSelectViewModel;

public class LanguageViewHolder extends BaseViewModelViewHolder<LanguagesSelectViewModel, LanguageItemBinding, Language> {

    public LanguageViewHolder(LanguagesSelectViewModel viewModel, LanguageItemBinding dataBinding) {
        super(viewModel, dataBinding);
    }

    @Override
    public void bind(Language language) {
        dataBinding.setLanguage(language);
        dataBinding.setViewmodel(viewModel);
    }
}
