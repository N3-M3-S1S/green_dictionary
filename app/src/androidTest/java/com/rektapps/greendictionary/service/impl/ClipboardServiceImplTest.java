package com.rektapps.greendictionary.service.impl;

import android.content.ClipboardManager;
import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.rektapps.greendictionary.service.ClipboardService;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ClipboardServiceImplTest {

    @Test
    public void copyToClipboard() {
        InstrumentationRegistry.getInstrumentation().runOnMainSync(()->{
            ClipboardManager clipboardManager = (ClipboardManager) InstrumentationRegistry.getTargetContext().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipboardService clipboardService = new ClipboardServiceImpl(InstrumentationRegistry.getTargetContext());
            String testString = "test";
            String testHtmlString = "<p>test</p>";

            clipboardService.copyToClipboard(testHtmlString);
            assertTrue(clipboardManager.getPrimaryClip().getItemAt(0).getText().toString().equals(testString));

            clipboardService.copyToClipboard(testString);
            assertTrue(clipboardManager.getPrimaryClip().getItemAt(0).getText().toString().equals(testString));


        });

    }
}