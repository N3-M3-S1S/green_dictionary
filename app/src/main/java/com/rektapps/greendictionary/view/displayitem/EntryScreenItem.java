package com.rektapps.greendictionary.view.displayitem;


import com.rektapps.greendictionary.model.entity.DictionaryEntry;
import com.rektapps.greendictionary.model.entity.Example;
import com.rektapps.greendictionary.model.entity.Translation;

import java.util.List;

public class EntryScreenItem extends DisplayEntryItem {

    private List<Translation> translations;

    private List<Example> examples;

    public EntryScreenItem(DictionaryEntry entry, List<Translation> translations, List<Example> examples) {
        super(entry);
        this.translations = translations;
        this.examples = examples;
    }

    public List<Translation> getTranslations() {
        return translations;
    }

    public List<Example> getExamples() {
        return examples;
    }


}
