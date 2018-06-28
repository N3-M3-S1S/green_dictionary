package com.rektapps.greendictionary.view.adapter.viewholder.factory.impl;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.rektapps.greendictionary.greendictionary.R;
import com.rektapps.greendictionary.view.adapter.viewholder.TranslationViewHolder;
import com.rektapps.greendictionary.view.adapter.viewholder.TranslationWithMeaningsViewHolder;
import com.rektapps.greendictionary.view.adapter.viewholder.factory.TranslationViewHolderFactory;

import javax.inject.Inject;

public class TranslationWithMeaningsViewHolderFactory implements TranslationViewHolderFactory {

    @Inject
    TranslationWithMeaningsViewHolderFactory() {
    }

    @Override
    public TranslationViewHolder createViewHolder(ViewGroup parent) {
        return new TranslationWithMeaningsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.translation_with_meanings, parent, false));
    }
}
