package com.rektapps.greendictionary.service.impl;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import com.rektapps.greendictionary.service.ClipboardService;
import com.rektapps.greendictionary.util.TextUtils;

import javax.inject.Inject;

public class ClipboardServiceImpl implements ClipboardService {
    private ClipboardManager clipboardManager;

    @Inject
    ClipboardServiceImpl(Context context){
        clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
    }



    @Override
    public void copyToClipboard(String text) {
        clipboardManager.setPrimaryClip(ClipData.newPlainText(null, TextUtils.removeHTMLTags(text)));
    }
}
