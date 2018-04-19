package com.andela.mrm.service;

import com.apollographql.apollo.ApolloClient;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by baron on 18/04/2018.
 */

final class MyApolloClient {

    private static final String BASE_URL =
            "https://api.graph.cool/simple/v1/cjg4sn59p5gkp01896q0wollo";
    private static ApolloClient myApolloClient;

    /**
     * private constructor.
     * prevent instantiation
     */
    private MyApolloClient() {

    }

    /**
     * Apollo implementation method.
     * @return expected myApolloClient object.
     */

    public static ApolloClient getMyApolloClient() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        myApolloClient = ApolloClient.builder()
                .serverUrl(BASE_URL)
                .okHttpClient(okHttpClient)
                .build();

        return  myApolloClient;

    }
}