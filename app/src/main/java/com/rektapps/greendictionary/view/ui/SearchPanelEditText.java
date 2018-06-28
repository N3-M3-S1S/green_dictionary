package com.rektapps.greendictionary.view.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;


public class SearchPanelEditText extends android.support.v7.widget.AppCompatEditText {

    public SearchPanelEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        //clear focus when soft keyboard is closed
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP)
            clearFocus();
        return super.onKeyPreIme(keyCode, event);
    }



}
