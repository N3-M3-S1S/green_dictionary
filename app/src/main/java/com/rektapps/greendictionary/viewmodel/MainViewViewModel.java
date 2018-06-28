package com.rektapps.greendictionary.viewmodel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;

import com.rektapps.greendictionary.api.GlosbeApi;
import com.rektapps.greendictionary.event.LanguageSelectionEvent;
import com.rektapps.greendictionary.event.MultipleSelectionEvent;
import com.rektapps.greendictionary.greendictionary.R;
import com.rektapps.greendictionary.messages.ApiErrorMessageCreator;
import com.rektapps.greendictionary.model.language.Language;
import com.rektapps.greendictionary.model.language.LanguageType;
import com.rektapps.greendictionary.service.DictionaryService;
import com.rektapps.greendictionary.service.LanguageService;
import com.rektapps.greendictionary.storage.SelectedLanguageStorage;
import com.rektapps.greendictionary.view.ListType;
import com.rektapps.greendictionary.view.displayitem.EntryScreenItem;
import com.rektapps.greendictionary.viewmodel.shared.eventviewmodel.MultipleSelectionEventViewModel;
import com.rektapps.greendictionary.viewmodel.util.SingleLiveEvent;

import java.util.EnumMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.rektapps.greendictionary.model.language.LanguageType.DEST;
import static com.rektapps.greendictionary.model.language.LanguageType.FROM;


public class MainViewViewModel extends ViewModel implements LifecycleObserver {
    private DictionaryService service;
    private GlosbeApi api;
    private ApiErrorMessageCreator apiErrorMessageCreator;
    private SelectedLanguageStorage selectedLanguageStorage;
    private LanguageService languageService;
    private ListType activeListType;
    private MultipleSelectionEventViewModel multipleSelectionEventModel;

    private Disposable apiRequest;

    private MutableLiveData<Boolean> isMultipleSelectionActiveLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isMultipleSelectionEnabledLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isEntryLoading = new MutableLiveData<>();

    private SingleLiveEvent<String> apiErrorMessage = new SingleLiveEvent<>();

    private ObservableField<String> phrase = new ObservableField<>();

    private Map<ListType, Boolean> multipleSelectionEnabledForListTypeMap = new EnumMap<>(ListType.class);
    private Map<LanguageType, MutableLiveData<Language>> selectedLanguageMap = new EnumMap<>(LanguageType.class);


    @SuppressLint("CheckResult")
    @Inject
    MainViewViewModel(
            DictionaryService dictionaryService,
            GlosbeApi api,
            ApiErrorMessageCreator apiErrorMessageCreator,
            SelectedLanguageStorage selectedLanguageStorage,
            LanguageService languageNameProvider
    ) {
        this.service = dictionaryService;
        this.api = api;
        this.apiErrorMessageCreator = apiErrorMessageCreator;
        this.selectedLanguageStorage = selectedLanguageStorage;
        this.languageService = languageNameProvider;
        initLanguages();
    }

    private void initLanguages() {
        for (int i = 0; i < LanguageType.values().length; i++) {
            LanguageType languageType = LanguageType.values()[i];
            selectedLanguageMap.put(languageType, new MutableLiveData<>());

            Language savedLanguage = selectedLanguageStorage.restoreLanguage(languageType);
            if (savedLanguage == null)
                savedLanguage = languageService.getLanguages().get(i);

            selectedLanguageMap.get(languageType).setValue(savedLanguage);

        }

    }


    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPause() {
        for (LanguageType languageType : LanguageType.values())
            selectedLanguageStorage.saveLanguage(languageType, selectedLanguageMap.get(languageType).getValue());
    }


    public void onMenuItemClicked(int menuItemId) {
        switch (menuItemId) {
            case R.id.multi_select:
                isMultipleSelectionActiveLiveData.setValue(true);
                multipleSelectionEventModel.postEvent(activeListType, MultipleSelectionEvent.SELECTION_ACTIVE);
                break;
            case R.id.delete_selected:
                multipleSelectionEventModel.postEvent(activeListType, MultipleSelectionEvent.DELETE_SELECTED);

        }
    }

    public void onMultipleSelectionCanceled() {
        multipleSelectionEventModel.postEvent(activeListType, MultipleSelectionEvent.SELECTION_CANCELED);
        isMultipleSelectionActiveLiveData.setValue(false);
    }


    public void onLanguageSelected(LanguageSelectionEvent languageSelectionEvent) {
        Language selectedLanguage = languageSelectionEvent.getSelectedLanguage();
        LanguageType selectedLanguageType = languageSelectionEvent.getLanguageType();

        if (isLanguageSelectedForOtherType(selectedLanguageType, selectedLanguage))
            swapFromDestLanguages();

        else
            selectedLanguageMap.get(selectedLanguageType).setValue(selectedLanguage);

    }

    private boolean isLanguageSelectedForOtherType(LanguageType selectedLanguageType, Language selectedLanguage) {
        for (LanguageType languageType : LanguageType.values()) {
            if (languageType == selectedLanguageType)
                continue;
            return selectedLanguageMap.get(languageType).getValue() == selectedLanguage;
        }
        return false;
    }


    @SuppressLint("CheckResult")
    public void search() {
        isEntryLoading.setValue(true);

        String languageFromCode = selectedLanguageMap.get(FROM).getValue().getLanguageCode();
        String languageDestCode = selectedLanguageMap.get(DEST).getValue().getLanguageCode();

        //cancel a request if there is an active one
        if (apiRequest != null && !apiRequest.isDisposed())
            apiRequest.dispose();

        apiRequest = api.search(languageFromCode, languageDestCode, phrase.get())
                .subscribeOn(Schedulers.io())
                .doAfterTerminate(() -> isEntryLoading.postValue(false))
                .subscribe(
                        apiResponse -> service.saveToHistory(apiResponse),

                        throwable -> {
                            apiErrorMessage.postValue(apiErrorMessageCreator.createMessage(throwable));
//                            Log.e("API ERROR", throwable.getMessage());
                        }
                );
    }


    public void swapFromDestLanguages() {
        Language tmp = selectedLanguageMap.get(FROM).getValue();
        selectedLanguageMap.get(FROM).setValue(selectedLanguageMap.get(DEST).getValue());
        selectedLanguageMap.get(DEST).setValue(tmp);

    }

    public void onActiveListChanged(ListType listType) {
        activeListType = listType;
        if (multipleSelectionEnabledForListTypeMap.containsKey(listType))
            isMultipleSelectionEnabledLiveData.setValue(multipleSelectionEnabledForListTypeMap.get(listType));
    }


    public void onListCountChanged(ListType listType, int count) {

        boolean isMultipleSelectionEnabled = count > 0;
        multipleSelectionEnabledForListTypeMap.put(listType, isMultipleSelectionEnabled);
        if (activeListType == listType) {
            isMultipleSelectionEnabledLiveData.setValue(isMultipleSelectionEnabled);

            boolean isMultipleSelectionActive = isMultipleSelectionActiveLiveData.getValue() != null && isMultipleSelectionActiveLiveData.getValue();

            if (isMultipleSelectionActive && !isMultipleSelectionEnabled)
                onMultipleSelectionCanceled();
        }
    }

    public void setMultipleSelectionEventModel(MultipleSelectionEventViewModel multipleSelectionEventModel) {
        this.multipleSelectionEventModel = multipleSelectionEventModel;
    }

    public ObservableField<String> getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase.set(phrase.trim());
    }

    public LiveData<EntryScreenItem> getEntryScreenItemLiveData() {
        return service.getEntryScreenItemLiveData();
    }

    public LiveData<Boolean> getIsEntryLoading() {
        return isEntryLoading;
    }

    public LiveData<Boolean> isMultipleSelectionActive() {
        return isMultipleSelectionActiveLiveData;
    }

    public LiveData<Boolean> getIsMultipleSelectionEnabledLiveData() {
        return isMultipleSelectionEnabledLiveData;
    }

    public LiveData<String> getApiErrorMessage() {
        return apiErrorMessage;
    }

    public LiveData<Language> getFromLanguageLiveData() {
        return selectedLanguageMap.get(FROM);
    }

    public LiveData<Language> getDestLanguageLiveData() {
        return selectedLanguageMap.get(DEST);
    }

}
