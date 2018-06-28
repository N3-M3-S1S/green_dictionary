package com.rektapps.greendictionary.viewmodel;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.rektapps.greendictionary.greendictionary.R;
import com.rektapps.greendictionary.model.entity.DictionaryEntry;
import com.rektapps.greendictionary.service.ClipboardService;
import com.rektapps.greendictionary.service.DictionaryService;
import com.rektapps.greendictionary.view.ListType;
import com.rektapps.greendictionary.view.displayitem.EntryScreenItem;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class TestEntryScreenViewModel {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private DictionaryService mockDictionaryService;

    @Mock
    private ClipboardService mockClipboardService;

    @InjectMocks
    private EntryScreenViewModel viewModel;

    @Test
    public void testAddToFavorites() {
        viewModel.setEntryScreenItem(new EntryScreenItem(new DictionaryEntry(), null, null));
        viewModel.onMenuItemClicked(R.id.entry_favorite);
        assertTrue(viewModel.getIsEntryFavoriteLiveData().getValue());
        viewModel.onStop();
        verify(mockDictionaryService, times(1)).addToFavorites(any());
    }

    @Test
    public void testRemoveFromFavorites() {
        DictionaryEntry testEntry = new DictionaryEntry();
        testEntry.setFavorite(true);
        viewModel.setEntryScreenItem(new EntryScreenItem(testEntry, null, null));
        viewModel.onMenuItemClicked(R.id.entry_favorite);
        assertTrue(!viewModel.getIsEntryFavoriteLiveData().getValue());
        viewModel.onStop();
        verify(mockDictionaryService, times(1)).removeItemFromList(eq(ListType.FAVORITES), any());
    }

    @Test
    public void testCopyToClipboard(){
        String testString = "test";
        viewModel.copyTextToClipboardOnLongClick(testString);
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        verify(mockClipboardService, times(1)).copyToClipboard(argumentCaptor.capture());
        assertTrue(argumentCaptor.getValue().equals(testString));



    }


}
