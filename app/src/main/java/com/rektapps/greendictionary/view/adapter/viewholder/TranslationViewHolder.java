package com.rektapps.greendictionary.view.adapter.viewholder;


import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rektapps.greendictionary.greendictionary.databinding.TranslationBinding;
import com.rektapps.greendictionary.model.entity.Translation;
import com.rektapps.greendictionary.viewmodel.EntryScreenViewModel;

public class TranslationViewHolder extends RecyclerView.ViewHolder {

    public TranslationViewHolder(View translationView) {
        super(translationView);
    }

    public void bind(Translation translation, EntryScreenViewModel viewModel) {
        TranslationBinding translationViewBinding = DataBindingUtil.bind(itemView);
        translationViewBinding.setTranslation(translation);
        translationViewBinding.setViewmodel(viewModel);
    }
}
