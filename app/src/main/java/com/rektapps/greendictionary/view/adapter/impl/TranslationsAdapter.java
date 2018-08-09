package com.rektapps.greendictionary.view.adapter.impl;

import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.rektapps.greendictionary.model.entity.Translation;
import com.rektapps.greendictionary.view.adapter.BaseViewModelRecyclerAdapter;
import com.rektapps.greendictionary.view.adapter.viewholder.BaseViewModelViewHolder;
import com.rektapps.greendictionary.view.adapter.viewholder.factory.ViewModelViewHolderFactory;
import com.rektapps.greendictionary.viewmodel.EntryScreenViewModel;

import java.util.Map;

import javax.inject.Inject;

public class TranslationsAdapter extends BaseViewModelRecyclerAdapter<Translation, EntryScreenViewModel> {
    public static final int TRANSLATION_WITHOUT_MEANINGS = 0;
    public static final int TRANSLATION_WITH_MEANINGS = 1;

    private Map<Integer, ViewModelViewHolderFactory<EntryScreenViewModel, Translation>> viewHolderFactoryMap;

    @Inject
    TranslationsAdapter(Map<Integer, ViewModelViewHolderFactory<EntryScreenViewModel, Translation>> viewHolderFactoryMap) {
        this.viewHolderFactoryMap = viewHolderFactoryMap;
    }

    @NonNull
    @Override
    public BaseViewModelViewHolder<EntryScreenViewModel, ? extends ViewDataBinding, Translation> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return viewHolderFactoryMap.get(viewType).build(viewModel, parent);
    }

    @Override
    public int getItemViewType(int position) {
        Translation translation = items.get(position);
        return translation.getMeanings().size() != 0 ? TRANSLATION_WITH_MEANINGS : TRANSLATION_WITHOUT_MEANINGS;
    }


}
