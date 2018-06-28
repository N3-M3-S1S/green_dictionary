package com.rektapps.greendictionary.service.impl;

import android.support.test.InstrumentationRegistry;

import com.rektapps.greendictionary.model.language.Language;
import com.rektapps.greendictionary.service.LanguageService;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class LanguageServiceImplTest {
    private LanguageService languageService = new LanguageServiceImpl(InstrumentationRegistry.getTargetContext());


    @Test
    public void getLanguages() {
        assertTrue(languageService.getLanguages().size() == 106); //21.06.2018
    }

    @Test
    public void getLanguageByCode() {
        String testLanguageCode = "ja";
        Language language = languageService.getLanguageByCode(testLanguageCode);
        assertTrue(language.getLanguageCode().equals(testLanguageCode));
    }
}