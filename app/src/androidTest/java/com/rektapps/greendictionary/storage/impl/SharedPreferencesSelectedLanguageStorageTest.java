package com.rektapps.greendictionary.storage.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.test.InstrumentationRegistry;

import com.rektapps.greendictionary.model.language.Language;
import com.rektapps.greendictionary.model.language.LanguageType;
import com.rektapps.greendictionary.service.LanguageService;
import com.rektapps.greendictionary.storage.SelectedLanguageStorage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SharedPreferencesSelectedLanguageStorageTest {
    private SelectedLanguageStorage selectedLanguageStorage;

    private Language testLanguage1 = new Language("1", "test1");
    private Language testLanguage2 = new Language("2", "test2");


    @Before
    public void init() {
        LanguageService mockLanguageService = mock(LanguageService.class);
        when(mockLanguageService.getLanguageByCode("1")).thenReturn(testLanguage1);
        when(mockLanguageService.getLanguageByCode("2")).thenReturn(testLanguage2);
        SharedPreferences testSharedPreferences = InstrumentationRegistry.getTargetContext().getSharedPreferences("prefs_test", Context.MODE_PRIVATE);
        selectedLanguageStorage = new SharedPreferencesSelectedLanguageStorage(testSharedPreferences, mockLanguageService);


    }

    @Test
    public void testSaveSelectedLanguages() {
        selectedLanguageStorage.saveLanguage(LanguageType.FROM, testLanguage1);
        selectedLanguageStorage.saveLanguage(LanguageType.DEST, testLanguage2);

        assertTrue(selectedLanguageStorage.restoreLanguage(LanguageType.FROM) == testLanguage1);
        assertTrue(selectedLanguageStorage.restoreLanguage(LanguageType.DEST) == testLanguage2);
    }


}