package com.rektapps.greendictionary.dagger.component;

import android.app.Application;

import com.rektapps.greendictionary.App;
import com.rektapps.greendictionary.dagger.module.AndroidBindingModule;
import com.rektapps.greendictionary.dagger.module.ApiModule;
import com.rektapps.greendictionary.dagger.module.AppModule;
import com.rektapps.greendictionary.dagger.module.DaoModule;
import com.rektapps.greendictionary.dagger.module.ServiceModule;
import com.rektapps.greendictionary.dagger.module.ViewModelModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        ApiModule.class,
        DaoModule.class,
        ServiceModule.class,
        AndroidBindingModule.class,
        AppModule.class,
        ViewModelModule.class,
        AndroidSupportInjectionModule.class
})
public interface AppComponent extends AndroidInjector<App> {
    void inject(App app);

    DataBindingSubComponent.Builder bindingBuilder();

    @Component.Builder
    interface Builder {
        AppComponent build();

        @BindsInstance
        Builder application(Application application);

    }


}
