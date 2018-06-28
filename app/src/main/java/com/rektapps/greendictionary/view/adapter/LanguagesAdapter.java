package com.rektapps.greendictionary.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.rektapps.greendictionary.greendictionary.R;
import com.rektapps.greendictionary.model.language.Language;
import com.rektapps.greendictionary.view.adapter.viewholder.LanguageViewHolder;
import com.rektapps.greendictionary.viewmodel.LanguagesSelectViewModel;

import java.util.List;

public class LanguagesAdapter extends RecyclerView.Adapter<LanguageViewHolder> {
    private List<Language> languages;
    private LanguagesSelectViewModel viewModel;


    @NonNull
    @Override
    public LanguageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LanguageViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.language_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LanguageViewHolder holder, int position) {
        holder.bind(viewModel, languages.get(position));
    }

    @Override
    public int getItemCount() {
        return languages == null ? 0 : languages.size();
    }

    public void setLanguages(List<Language> newLanguages) {
        if (languages == null) {
            languages = newLanguages;
            notifyDataSetChanged();
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new LanguageDiffCallback(languages, newLanguages));
            result.dispatchUpdatesTo(this);
            languages = newLanguages;
        }
    }


    public void setViewModel(LanguagesSelectViewModel viewModel) {
        this.viewModel = viewModel;
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
