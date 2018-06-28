package com.rektapps.greendictionary.dagger.module;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.rektapps.greendictionary.api.ApiResponseDeserializer;
import com.rektapps.greendictionary.api.GlosbeApi;
import com.rektapps.greendictionary.model.ApiResponse;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public abstract class ApiModule {

    @Singleton
    @Provides
    static GlosbeApi providesApi(JsonDeserializer<ApiResponse> deserializer, OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://glosbe.com")
                .addConverterFactory(
                        GsonConverterFactory.create(
                                new GsonBuilder()
                                        .registerTypeAdapter(ApiResponse.class, deserializer).create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(GlosbeApi.class);

    }


    @Provides
    static OkHttpClient providesOkHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();
    }


    @Binds
    abstract JsonDeserializer<ApiResponse> providesApiResponseJsonDeserializer(ApiResponseDeserializer entryWithDataDeserializer);

}
