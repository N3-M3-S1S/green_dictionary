package com.rektapps.greendictionary.view.bindingadapter;

import android.databinding.BindingAdapter;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;

import com.rektapps.greendictionary.greendictionary.R;
import com.rektapps.greendictionary.model.entity.Translation;

public class StaticBindingAdapters {

    @BindingAdapter("bindHtmlText")
    public static void bindHTMLText(TextView textView, String htmlText) {
        Spanned spannedHtmlText;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            spannedHtmlText = Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY);
        else
            spannedHtmlText = Html.fromHtml(htmlText);
        textView.setText(spannedHtmlText);
    }


    @BindingAdapter("bindListItemTranslation")
    public static void bindListItemTranslation(TextView textView, Translation translation) {
        if (translation == null)
            textView.setText(R.string.noTranslations);
        else
            bindHTMLText(textView, translation.getText());
    }

}
