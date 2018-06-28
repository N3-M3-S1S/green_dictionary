package com.rektapps.greendictionary.view.adapter.viewholder.factory.impl;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.rektapps.greendictionary.greendictionary.R;
import com.rektapps.greendictionary.view.adapter.viewholder.TranslationViewHolder;
import com.rektapps.greendictionary.view.adapter.viewholder.factory.TranslationViewHolderFactory;

import javax.inject.Inject;

public class TranslationWithoutMeaningsViewHolderFactory implements TranslationViewHolderFactory {

    @Inject
    TranslationWithoutMeaningsViewHolderFactory() {
    }

    @Override
    public TranslationViewHolder createViewHolder(ViewGroup parent) {
        return new TranslationViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.translation, parent, false));
    }
}
