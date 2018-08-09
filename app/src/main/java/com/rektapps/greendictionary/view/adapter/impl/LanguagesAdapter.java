package com.rektapps.greendictionary.view.adapter.impl;

import android.support.v7.util.DiffUtil;

import com.rektapps.greendictionary.model.language.Language;
import com.rektapps.greendictionary.view.adapter.BaseViewModelRecyclerAdapter;
import com.rektapps.greendictionary.view.adapter.viewholder.factory.ViewModelViewHolderFactory;
import com.rektapps.greendictionary.viewmodel.LanguagesSelectViewModel;

import java.util.List;

import javax.inject.Inject;

public class LanguagesAdapter extends BaseViewModelRecyclerAdapter<Language, LanguagesSelectViewModel> {

    @Inject
    LanguagesAdapter(ViewModelViewHolderFactory<LanguagesSelectViewModel, Language> factory) {
        super(factory);
    }

    @Override
    public void setItems(List<Language> languages) {
        if (items != null) {
           DiffUtil.calculateDiff(new LanguageDiffCallback(items, languages)).dispatchUpdatesTo(this);
           items = languages;
        }
        else
            super.setItems(languages);
    }

    private class LanguageDiffCallback extends DiffUtil.Callback {
        private List<Language> oldList;
        private List<Language> newList;

        LanguageDiffCallback(List<Language> oldList, List<Language> newList) {
            this.oldList = oldList;
            this.newList = newList;
        }

        @Override
        public int getOldListSize() {
            return oldList.size();
        }

        @Override
        public int getNewListSize() {
            return newList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
        }
    }

}
