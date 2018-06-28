package com.rektapps.greendictionary.viewmodel;


import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.support.annotation.NonNull;

import com.rektapps.greendictionary.api.GlosbeApi;
import com.rektapps.greendictionary.event.LanguageSelectionEvent;
import com.rektapps.greendictionary.messages.ApiErrorMessageCreator;
import com.rektapps.greendictionary.model.ApiResponse;
import com.rektapps.greendictionary.model.language.Language;
import com.rektapps.greendictionary.model.language.LanguageType;
import com.rektapps.greendictionary.service.DictionaryService;
import com.rektapps.greendictionary.service.LanguageService;
import com.rektapps.greendictionary.storage.SelectedLanguageStorage;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

import static com.rektapps.greendictionary.view.ListType.FAVORITES;
import static com.rektapps.greendictionary.view.ListType.HISTORY;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestMainViewModel {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private DictionaryService mockService;

    @Mock
    private GlosbeApi mockApi;

    @Mock
    private ApiErrorMessageCreator mockApiErrorMessageCreator;

    @Mock
    private SelectedLanguageStorage mockSelectedLanguageStorage;

    @Mock
    private LanguageService mockLanguageNameProvider;

    private MainViewViewModel mainViewViewModel;

    private List<Language> testLanguages;

    @BeforeClass
    public static void setUpRxSchedulers() {
        Scheduler immediate = new Scheduler() {
            @Override
            public Disposable scheduleDirect(@NonNull Runnable run, long delay, @NonNull TimeUnit unit) {
                // this prevents StackOverflowErrors when scheduling with a delay
                return super.scheduleDirect(run, 0, unit);
            }

            @Override
            public Scheduler.Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(Runnable::run);
            }
        };

        RxJavaPlugins.setInitIoSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitComputationSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitNewThreadSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitSingleSchedulerHandler(scheduler -> immediate);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> immediate);
    }

    @Before
    public void init() {
        testLanguages = createTestLanguages();
        doReturn(testLanguages).when(mockLanguageNameProvider).getLanguages();
        mainViewViewModel = new MainViewViewModel(mockService, mockApi, mockApiErrorMessageCreator,
                mockSelectedLanguageStorage, mockLanguageNameProvider);

    }

    @Test
    public void testSetWord() {
        mainViewViewModel.setPhrase("   abc    ");
        assertTrue(mainViewViewModel.getPhrase().get().equals("abc"));
    }


    @Test
    public void testOnListCountChanged() {
        mainViewViewModel.onActiveListChanged(HISTORY);
        mainViewViewModel.onListCountChanged(HISTORY, 1);
        mainViewViewModel.onListCountChanged(FAVORITES, 0);
        assertTrue(mainViewViewModel.getIsMultipleSelectionEnabledLiveData().getValue());

        mainViewViewModel.onActiveListChanged(FAVORITES);
        assertFalse(mainViewViewModel.getIsMultipleSelectionEnabledLiveData().getValue());

        mainViewViewModel.onListCountChanged(FAVORITES, 2);
        assertTrue(mainViewViewModel.getIsMultipleSelectionEnabledLiveData().getValue());

        mainViewViewModel.onActiveListChanged(HISTORY);
        assertTrue(mainViewViewModel.getIsMultipleSelectionEnabledLiveData().getValue());
    }


    @Test
    public void testSearchApi() {
        when(mockApi.search(any(), any(), any())).thenReturn(Single.just(new ApiResponse(null, null, null)));
        mainViewViewModel.search();
        verify(mockService, times(1)).saveToHistory(any());


        when(mockApi.search(any(), any(), any())).thenReturn(Single.error(new Throwable("test")));
        mainViewViewModel.search();

        verify(mockApiErrorMessageCreator, times(1)).createMessage(any(Throwable.class));
    }

    @Test
    public void testSwapLanguages() {
        Language destLanguage = mainViewViewModel.getDestLanguageLiveData().getValue();

        mainViewViewModel.onLanguageSelected(new LanguageSelectionEvent(LanguageType.FROM, destLanguage));

        assertTrue(mainViewViewModel.getFromLanguageLiveData().getValue().getLanguageCode().equals("2"));
    }


    private List<Language> createTestLanguages() {
        List<Language> testLanguages = new ArrayList<>();
        testLanguages.add(new Language("1", "testLanguage1"));
        testLanguages.add(new Language("2", "testLanguage2"));
        return testLanguages;
    }


}
