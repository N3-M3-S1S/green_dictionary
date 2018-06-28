package com.rektapps.greendictionary.viewmodel;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.rektapps.greendictionary.model.entity.DictionaryEntry;
import com.rektapps.greendictionary.service.DictionaryService;
import com.rektapps.greendictionary.view.displayitem.ListItem;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.rektapps.greendictionary.event.MultipleSelectionEvent.DELETE_SELECTED;
import static com.rektapps.greendictionary.event.MultipleSelectionEvent.SELECTION_ACTIVE;
import static com.rektapps.greendictionary.event.MultipleSelectionEvent.SELECTION_CANCELED;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyCollection;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TestDictionaryListViewModel {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();
    @Mock
    private DictionaryService mockService;
    private DictionaryListViewModel viewModel;


    @Before
    public void init() {
        viewModel = new DictionaryListViewModel(mockService);
    }


    @Test
    public void testItemClick() {
        viewModel.listItemClicked(new ListItem(null, null));
        verify(mockService, times(1)).loadEntryScreenItem(any());
    }


    @Test
    public void testItemSelection() {
        viewModel.onMultipleSelectionEvent(SELECTION_ACTIVE);
        viewModel.listItemClicked(new ListItem(new DictionaryEntry(), null));
        viewModel.listItemClicked(new ListItem(null, null));
        assertTrue(viewModel.getSelectedCountChangedEventLiveData().getValue() == 2);
    }

    @Test
    public void testMultipleSelectionEvents() {
        viewModel.onMultipleSelectionEvent(SELECTION_ACTIVE);
        assertTrue(viewModel.getIsMultipleSelectionActiveLiveData().getValue());

        viewModel.onMultipleSelectionEvent(SELECTION_CANCELED);
        assertFalse(viewModel.getIsMultipleSelectionActiveLiveData().getValue());

        viewModel.onMultipleSelectionEvent(DELETE_SELECTED);
        verify(mockService, times(1)).removeItemsFromList(any(), anyCollection());
    }


    @Test
    public void testDeleteItem() {
        viewModel.onDeleteItemClicked(null);
        verify(mockService, times(1)).removeItemFromList(any(), any());
    }


}
