package com.rektapps.greendictionary.view.adapter.viewholder;

import com.rektapps.greendictionary.greendictionary.databinding.LanguageItemBinding;
import com.rektapps.greendictionary.model.language.Language;
import com.rektapps.greendictionary.viewmodel.LanguagesSelectViewModel;

public class LanguageViewHolder extends DataBindingViewHolder<LanguageItemBinding> {
    public LanguageViewHolder(LanguageItemBinding dataBinding) {
        super(dataBinding);
    }


    public void bind(LanguagesSelectViewModel viewModel, Language language) {
        dataBinding.setLanguage(language);
        dataBinding.setViewmodel(viewModel);
    }

}
