package com.rektapps.greendictionary.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.rektapps.greendictionary.model.entity.Translation;
import com.rektapps.greendictionary.view.adapter.viewholder.TranslationViewHolder;
import com.rektapps.greendictionary.view.adapter.viewholder.factory.TranslationViewHolderFactory;
import com.rektapps.greendictionary.view.adapter.viewholder.factory.ViewHolderFactory;
import com.rektapps.greendictionary.viewmodel.EntryScreenViewModel;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;


public class TranslationsAdapter extends RecyclerView.Adapter<TranslationViewHolder> {
    private List<Translation> translations;
    private Map<Integer, ViewHolderFactory<TranslationViewHolder>> viewHolderFactoryMap;
    private EntryScreenViewModel viewModel;

    @Inject
    public TranslationsAdapter(Map<Integer, ViewHolderFactory<TranslationViewHolder>> viewHolderFactoryMap) {
        this.viewHolderFactoryMap = viewHolderFactoryMap;
    }

    public void setTranslations(List<Translation> translations) {
        this.translations = translations;
    }

    public void setViewModel(EntryScreenViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public TranslationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return viewHolderFactoryMap.get(viewType).createViewHolder(parent);
    }

    @Override
    public int getItemViewType(int position) {
        Translation translation = translations.get(position);
        return translation.getMeanings().size() != 0 ? TranslationViewHolderFactory.TRANSLATION_WITH_MEANINGS : TranslationViewHolderFactory.TRANSLATION_WITHOUT_MEANINGS;
    }

    @Override
    public void onBindViewHolder(@NonNull TranslationViewHolder holder, int position) {
        holder.bind(translations.get(position), viewModel);
    }

    @Override
    public int getItemCount() {
        return translations.size();
    }


}
