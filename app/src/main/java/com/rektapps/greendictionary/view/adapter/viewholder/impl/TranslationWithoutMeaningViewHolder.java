package com.rektapps.greendictionary.view.adapter.viewholder.impl;

import com.rektapps.greendictionary.greendictionary.databinding.TranslationBinding;
import com.rektapps.greendictionary.model.entity.Translation;
import com.rektapps.greendictionary.view.adapter.viewholder.EntryScreenTextViewHolder;
import com.rektapps.greendictionary.viewmodel.EntryScreenViewModel;

public class TranslationWithoutMeaningViewHolder extends EntryScreenTextViewHolder<TranslationBinding, Translation> {

    public TranslationWithoutMeaningViewHolder(TranslationBinding translationBinding, EntryScreenViewModel entryScreenViewModel) {
        super(entryScreenViewModel, translationBinding);
    }

    @Override
    public void bind(Translation translation) {
        dataBinding.setTranslation(translation);
        copyTextToClipboardOnLongClick(dataBinding.translationTv, translation.getText());
    }
}
