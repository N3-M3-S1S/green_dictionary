package com.rektapps.greendictionary.messages;

public interface ApiErrorMessageCreator {
    String createMessage(Throwable error);
}
