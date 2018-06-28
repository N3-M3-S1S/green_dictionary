package com.rektapps.greendictionary;

import android.databinding.DataBindingUtil;

import com.rektapps.greendictionary.dagger.component.AppComponent;
import com.rektapps.greendictionary.dagger.component.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;


public class App extends DaggerApplication {

    @Inject
    AppComponent appComponent;

    @Override
    public void onCreate() {
        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);
        //set non static binding adapters
        DataBindingUtil.setDefaultComponent(appComponent.bindingBuilder().build());
        super.onCreate();
    }

    @Override
    protected AndroidInjector<? extends dagger.android.support.DaggerApplication> applicationInjector() {
        return appComponent;
    }


}

