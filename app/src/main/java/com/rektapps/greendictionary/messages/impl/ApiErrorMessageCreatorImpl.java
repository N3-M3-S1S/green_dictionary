package com.rektapps.greendictionary.messages.impl;

import android.content.Context;

import com.rektapps.greendictionary.greendictionary.R;
import com.rektapps.greendictionary.messages.ApiErrorMessageCreator;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.inject.Inject;

public class ApiErrorMessageCreatorImpl implements ApiErrorMessageCreator {
    private Context application;

    @Inject
    ApiErrorMessageCreatorImpl(Context context) {
        this.application = context;
    }

    @Override
    public String createMessage(Throwable error) {
        if (error instanceof SocketTimeoutException)
            return application.getString(R.string.timeout_error);
        else if (error instanceof UnknownHostException)
            return application.getString(R.string.unknown_host_error);
        else
            return application.getString(R.string.unknown_error);
    }
}
