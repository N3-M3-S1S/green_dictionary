package com.rektapps.greendictionary.view.adapter.viewholder;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;

import com.rektapps.greendictionary.greendictionary.R;
import com.rektapps.greendictionary.greendictionary.databinding.MeaningBinding;
import com.rektapps.greendictionary.greendictionary.databinding.TranslationWithMeaningsBinding;
import com.rektapps.greendictionary.model.entity.Translation;
import com.rektapps.greendictionary.viewmodel.EntryScreenViewModel;

import java.util.List;

public class TranslationWithMeaningsViewHolder extends TranslationViewHolder {
    private ObjectAnimator arrowRotateAnimator;


    public TranslationWithMeaningsViewHolder(View translationWithMeaningsView) {
        super(translationWithMeaningsView);
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void bind(Translation translation, EntryScreenViewModel viewModel) {
        TranslationWithMeaningsBinding binding = DataBindingUtil.bind(itemView);
        binding.setTranslation(translation);
        binding.setViewmodel(viewModel);
        bindMeanings(binding, translation.getMeanings());

        if (translation.getMeanings().size() > 1) {
            arrowRotateAnimator = ObjectAnimator.ofFloat(binding.expandArrow,
                    "rotation", 0f, -180f);
            arrowRotateAnimator.setDuration(300);

            binding.expandArrow.setOnClickListener(view -> {
                binding.expandableLayout.toggle();
                if (binding.expandableLayout.isExpanded())
                    arrowRotateAnimator.start();
                else
                    arrowRotateAnimator.reverse();
            });

        }
    }

    private void bindMeanings(TranslationWithMeaningsBinding binding, List<String> meanings) {
        for (int i = 0; i < meanings.size(); i++) {
            String meaning = meanings.get(i);
            if (i == 0) {
                binding.setVisibleMeaningText(meaning);
            } else {
                MeaningBinding meaningBinding = DataBindingUtil.inflate(LayoutInflater.from(binding.getRoot().getContext()),
                        R.layout.meaning, binding.collapsedMeaningsContainer, true);
                meaningBinding.setMeaningText("• " + meaning);

            }
        }
    }

}
