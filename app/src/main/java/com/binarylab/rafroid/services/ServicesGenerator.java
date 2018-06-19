package com.binarylab.rafroid.services;

import com.binarylab.rafroid.util.JsonDateDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServicesGenerator {

    public static final String BACKEND = "https://api.raf.ninja/v1/";
    public static final String apikey = "xie7djkRnbi3YmXo1sEm";

    private static final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss").registerTypeAdapter(Date.class, new JsonDateDeserializer())
            .create();

    private static final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS).build();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BACKEND)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson));

    private static Retrofit retrofit = builder.build();

    public static <S> S createService(
            Class<S> serviceClass) {
        return retrofit.create(serviceClass);

    }
}
