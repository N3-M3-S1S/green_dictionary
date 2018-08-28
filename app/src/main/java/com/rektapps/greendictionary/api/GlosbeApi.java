package com.rektapps.greendictionary.api;

import com.rektapps.greendictionary.model.ApiResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GlosbeApi {

    @GET("/gapi/translate?format=json&tm=true")
    Single<ApiResponse> search(@Query("from") String from, @Query("dest") String dest, @Query("phrase") String phrase);

}
