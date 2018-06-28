package com.rektapps.greendictionary.service.impl;

import com.rektapps.greendictionary.model.entity.DictionaryEntry;
import com.rektapps.greendictionary.service.DictionaryService;
import com.rektapps.greendictionary.storage.db.DictionaryEntryDao;
import com.rektapps.greendictionary.storage.db.ExampleDao;
import com.rektapps.greendictionary.storage.db.TranslationDao;
import com.rektapps.greendictionary.view.ListType;
import com.rektapps.greendictionary.view.displayitem.DisplayEntryItem;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DictionaryService_implTest {

    @Mock
    private DictionaryEntryDao mockEntryDao;

    @Mock
    private TranslationDao mockTranslationsDao;

    @Mock
    private ExampleDao mockExampleDao;

    private DictionaryService dictionaryService;


    @Before
    public void init() {
        dictionaryService = new DictionaryServiceImpl(mockEntryDao, mockExampleDao, mockTranslationsDao);
    }


    @Test
    public void testRemoveItemFromList() {
        dictionaryService.removeItemFromList(ListType.HISTORY, new TestDisplayItem());
        verify(mockEntryDao, timeout(100).times(1)).delete(any(DictionaryEntry.class));
    }

    @Test
    public void testUpdateItem() {
        DisplayEntryItem testItem = new TestDisplayItem();
        testItem.getEntry().setFavorite(true);
        dictionaryService.removeItemFromList(ListType.HISTORY, testItem);
        verify(mockEntryDao, timeout(100).times(1)).update(any(DictionaryEntry.class));
    }

    @Test
    public void testRemoveOrUpdateListOfItems() {
        List<DisplayEntryItem> testItems = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            TestDisplayItem testDisplayItem = new TestDisplayItem();
            testDisplayItem.getEntry().setFavorite(i % 2 == 0);
            testItems.add(testDisplayItem);
        }

        dictionaryService.removeItemsFromList(ListType.HISTORY, testItems);

        ArgumentCaptor<List> argumentCaptor = ArgumentCaptor.forClass(List.class);

        verify(mockEntryDao, timeout(100).times(1)).update(argumentCaptor.capture());
        assertTrue(argumentCaptor.getValue().size() == 5);

        verify(mockEntryDao, timeout(100).times(1)).delete(argumentCaptor.capture());
        assertTrue(argumentCaptor.getValue().size() == 5);


    }


    private class TestDisplayItem extends DisplayEntryItem {
        TestDisplayItem() {
            super(new DictionaryEntry());
        }
    }

}