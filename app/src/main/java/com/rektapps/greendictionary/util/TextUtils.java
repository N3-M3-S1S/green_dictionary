package com.rektapps.greendictionary.util;

import android.text.Html;

public class TextUtils {

    public static String removeHTMLTags(String text){
        String textWithoutHTML;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            textWithoutHTML = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY).toString();
        } else {
            textWithoutHTML =  Html.fromHtml(text).toString();
        }
        return textWithoutHTML.replaceAll("\n", "");

    }
}
