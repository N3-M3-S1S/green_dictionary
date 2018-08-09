package com.rektapps.greendictionary.view.adapter.viewholder.impl;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.rektapps.greendictionary.greendictionary.R;
import com.rektapps.greendictionary.greendictionary.databinding.MeaningBinding;
import com.rektapps.greendictionary.greendictionary.databinding.TranslationWithMeaningsBinding;
import com.rektapps.greendictionary.model.entity.Translation;
import com.rektapps.greendictionary.view.adapter.viewholder.EntryScreenTextViewHolder;
import com.rektapps.greendictionary.viewmodel.EntryScreenViewModel;

import java.util.List;

public class TranslationWithMeaningsViewHolder extends EntryScreenTextViewHolder<TranslationWithMeaningsBinding, Translation> {
    private ValueAnimator meaningsContainerHeightAnimator;
    private int ANIMATION_DURATION_MS = 300;
    private boolean isMeaningsExpanded = false;
    private int heightToExpand;
    private float visibleMeaningDefaultPosition;


    public TranslationWithMeaningsViewHolder(TranslationWithMeaningsBinding translationWithMeaningsBinding, EntryScreenViewModel entryScreenViewModel) {
        super(entryScreenViewModel, translationWithMeaningsBinding);
    }

    @Override
    public void bind(Translation translation) {

        dataBinding.setTranslation(translation);


        //parent of these views already has vertical padding
        removeVerticalPadding(dataBinding.translationInclude.translationTv);
        removeVerticalPadding(dataBinding.visibleMeaningInclude.meaningTv);


        //if there is only one meaning for translation - remove clickable background from the parent and copy translation or meaning by long-click their views
        if (translation.getMeanings().size() == 1) {
            removeBackground(dataBinding.translationMeaningRoot);
            copyTextToClipboardOnLongClick(dataBinding.translationInclude.translationTv, translation.getText());
            copyTextToClipboardOnLongClick(dataBinding.visibleMeaningInclude.meaningTv, translation.getMeanings().get(0));
        }


        //if there > 1 meaning - remove clickable background from translation and visible meaning view,
        // copy only translation by long-click parent view and expand\collapse by click parent view.
        else {
            removeBackground(dataBinding.translationInclude.translationTv);
            removeBackground(dataBinding.visibleMeaningInclude.meaningTv);
            setMeaningTextToOneLine();
            copyTextToClipboardOnLongClick(dataBinding.translationMeaningRoot, translation.getText());
            dataBinding.translationMeaningRoot.setOnClickListener(view -> {
                if(!isAnimationsRunning())
                    startMeaningsContainerAnimations();
            });
        }


        bindMeanings(dataBinding, translation.getMeanings());
        measureExpandedMeaningsHeight();

        visibleMeaningDefaultPosition = dataBinding.visibleMeaningInclude.meaningTv.getY();
    }


    private void startMeaningsContainerAnimations() {

        meaningsContainerHeightAnimator = createMeaningsContainerHeightAnimator();

        meaningsContainerHeightAnimator.addUpdateListener(valueAnimator -> {
            int height = (int) valueAnimator.getAnimatedValue();
            updateMeaningsContainerHeight(height);
        });

        meaningsContainerHeightAnimator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                isMeaningsExpanded = !isMeaningsExpanded;
            }


            @Override
            public void onAnimationStart(Animator animation) {
                startArrowRotationAnimation();
                startVisibleMeaningTranslationAnimation();
            }
        });

        meaningsContainerHeightAnimator.setDuration(ANIMATION_DURATION_MS);
        meaningsContainerHeightAnimator.start();

    }


    private void setMeaningTextToOneLine() {
        dataBinding.visibleMeaningInclude.meaningTv.setMaxLines(1);
        dataBinding.visibleMeaningInclude.meaningTv.setEllipsize(TextUtils.TruncateAt.END);
    }

    private boolean isAnimationsRunning() {
        return meaningsContainerHeightAnimator != null && meaningsContainerHeightAnimator.isRunning();
    }

    private ValueAnimator createMeaningsContainerHeightAnimator() {
        int currentHeight = dataBinding.collapsedMeaningsContainer.getHeight();
        int resultHeight = isMeaningsExpanded ? 0 : heightToExpand;
        return ValueAnimator.ofInt(currentHeight, resultHeight);
    }

    private void updateMeaningsContainerHeight(int height) {
        ViewGroup.LayoutParams params = dataBinding.collapsedMeaningsContainer.getLayoutParams();
        params.height = height;
        dataBinding.collapsedMeaningsContainer.setLayoutParams(params);
    }

    private void startArrowRotationAnimation() {
        float rotation = isMeaningsExpanded ? 0 : -180;
        dataBinding.expandArrow.animate().rotation(rotation).setDuration(ANIMATION_DURATION_MS).start();
    }

    private void startVisibleMeaningTranslationAnimation() {
        float translation = isMeaningsExpanded ? visibleMeaningDefaultPosition : dataBinding.visibleMeaningInclude.meaningTv.getHeight();
        dataBinding.visibleMeaningInclude.meaningTv.animate().translationY(translation).setDuration(ANIMATION_DURATION_MS).start();
    }


    private void removeVerticalPadding(View view) {
        view.setPadding(view.getPaddingLeft(), 0, view.getPaddingRight(), 0);
    }

    private void removeBackground(View view) {
        view.setBackgroundResource(0);
    }

    private void measureExpandedMeaningsHeight() {
        dataBinding.collapsedMeaningsContainer.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        heightToExpand = dataBinding.collapsedMeaningsContainer.getMeasuredHeight();
    }

    private void bindMeanings(TranslationWithMeaningsBinding binding, List<String> meanings) {
        for (int i = 0; i < meanings.size(); i++) {
            if (i == 0) {
                String visibleMeaningText = meanings.get(i);
                binding.setVisibleMeaningText(visibleMeaningText);
            }
            String expandableMeaningText = meanings.get(i);

            MeaningBinding expandableMeaningBinding = DataBindingUtil.inflate(LayoutInflater.from(binding.getRoot().getContext()),
                    R.layout.meaning, binding.collapsedMeaningsContainer, true);

            expandableMeaningBinding.setMeaningText(expandableMeaningText);
            copyTextToClipboardOnLongClick(expandableMeaningBinding.meaningTv, expandableMeaningText);
        }
    }


}
