package com.rektapps.greendictionary.model;

import com.rektapps.greendictionary.model.entity.DictionaryEntry;
import com.rektapps.greendictionary.model.entity.Example;
import com.rektapps.greendictionary.model.entity.Translation;

import java.util.List;

public class ApiResponse {
    private DictionaryEntry entry;
    private List<Translation> translations;
    private List<Example> examples;

    public ApiResponse(DictionaryEntry entry, List<Translation> translations, List<Example> examples) {
        this.entry = entry;
        this.translations = translations;
        this.examples = examples;
    }

    public DictionaryEntry getEntry() {
        return entry;
    }

    public List<Translation> getTranslations() {
        return translations;
    }

    public List<Example> getExamples() {
        return examples;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "entry=" + entry +
                ", translations=" + translations +
                ", examples=" + examples +
                '}';
    }
}
