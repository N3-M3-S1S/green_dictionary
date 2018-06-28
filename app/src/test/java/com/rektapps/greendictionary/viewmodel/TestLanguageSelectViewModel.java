package com.rektapps.greendictionary.viewmodel;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.rektapps.greendictionary.model.language.Language;
import com.rektapps.greendictionary.service.LanguageService;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestLanguageSelectViewModel {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private LanguageService mockLanguageService;


    private LanguagesSelectViewModel viewModel;


    @Before
    public void init() {
        viewModel = new LanguagesSelectViewModel(mockLanguageService);
    }

    @Test
    public void testFilter() {
        when(mockLanguageService.getLanguages()).thenReturn(createTestLanguages());

        viewModel.filterLanguages("x");
        assertTrue(viewModel.getLanguagesLiveData().getValue().size() == 1);

        viewModel.filterLanguages("     ");
        assertTrue(viewModel.getLanguagesLiveData().getValue().size() == 4);

        viewModel.filterLanguages("zxc");
        assertTrue(viewModel.getLanguagesLiveData().getValue().size() == 0);

        viewModel.filterLanguages("  AB ");
        assertTrue(viewModel.getLanguagesLiveData().getValue().size() == 2);

    }


    private List<Language> createTestLanguages() {
        List<Language> testLanguages = new ArrayList<>();
        testLanguages.add(new Language("1", "abc"));
        testLanguages.add(new Language("1", "abd"));
        testLanguages.add(new Language("2", "xyz"));
        testLanguages.add(new Language("3", "qwe"));
        return testLanguages;
    }
}
