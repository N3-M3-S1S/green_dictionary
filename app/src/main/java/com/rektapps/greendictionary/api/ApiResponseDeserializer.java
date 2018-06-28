package com.rektapps.greendictionary.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.rektapps.greendictionary.model.ApiResponse;
import com.rektapps.greendictionary.model.entity.DictionaryEntry;
import com.rektapps.greendictionary.model.entity.Example;
import com.rektapps.greendictionary.model.entity.Translation;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;


public class ApiResponseDeserializer implements JsonDeserializer<ApiResponse> {

    @Inject
    ApiResponseDeserializer() {

    }

    @Override
    public ApiResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        DictionaryEntry entry = parseEntry(json);
        List<Translation> translations = parseTranslations(json);
        List<Example> examples = parseExamples(json);
        return new ApiResponse(entry, translations, examples);
    }

    private DictionaryEntry parseEntry(JsonElement rootJsonElement) {
        JsonObject jsonObject = rootJsonElement.getAsJsonObject();
        String phrase = jsonObject.get("phrase").getAsString();
        String fromLanguageCode = jsonObject.get("from").getAsString();
        String destLanguageCode = jsonObject.get("dest").getAsString();
        return new DictionaryEntry(phrase, fromLanguageCode, destLanguageCode);
    }

    private List<Translation> parseTranslations(JsonElement json) {
        List<Translation> translations = new ArrayList<>();
        JsonArray tuc = json.getAsJsonObject().getAsJsonArray("tuc");
        if (tuc != null) {
            String PHRASE_KEY = "phrase";
            String TEXT_KEY = "text";
            String MEANINGS_KEY = "meanings";

            for (JsonElement arrayElement : tuc) {
                JsonObject translationObject = arrayElement.getAsJsonObject();

                if (translationObject.has(PHRASE_KEY)) {
                    String translationText = translationObject.getAsJsonObject(PHRASE_KEY).get(TEXT_KEY).getAsString();
                    if (translationObject.has(MEANINGS_KEY)) {
                        List<String> meanings = new ArrayList<>();
                        JsonArray meaningsJson = translationObject.getAsJsonArray(MEANINGS_KEY);
                        for (JsonElement meaningElement : meaningsJson) {
                            String meaning = meaningElement.getAsJsonObject().get(TEXT_KEY).getAsString();
                            meanings.add(meaning);
                        }
                        translations.add(new Translation(translationText, meanings));
                    } else {
                        translations.add(new Translation(translationText));
                    }
                }
            }

            //translations with more meanings go first
            Collections.sort(translations, (translation, t1) -> Integer.compare(t1.getMeanings().size(), translation.getMeanings().size()));
        }
        return translations;
    }


    private List<Example> parseExamples(JsonElement rootJsonElement) {
        List<Example> examples = new ArrayList<>();
        JsonArray examplesJsonArray = rootJsonElement.getAsJsonObject().getAsJsonArray("examples");
        for (JsonElement exampleJson : examplesJsonArray) {
            JsonObject exampleJsonObject = exampleJson.getAsJsonObject();
            String firstExample = exampleJsonObject.get("first").getAsString();
            String secondExample = exampleJsonObject.get("second").getAsString();
            examples.add(new Example(firstExample, secondExample));
        }
        return examples;
    }


}
