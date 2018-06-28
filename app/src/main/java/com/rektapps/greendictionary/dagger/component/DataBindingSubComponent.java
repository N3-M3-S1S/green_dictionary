package com.rektapps.greendictionary.dagger.component;


import com.rektapps.greendictionary.dagger.scope.DataBindingScope;

import dagger.Subcomponent;

@DataBindingScope
@Subcomponent
public interface DataBindingSubComponent extends android.databinding.DataBindingComponent {

    @Subcomponent.Builder
    interface Builder {
        DataBindingSubComponent build();
    }


}
