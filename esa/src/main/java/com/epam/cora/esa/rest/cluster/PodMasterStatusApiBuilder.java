package com.epam.cora.esa.rest.cluster;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class PodMasterStatusApiBuilder {

    private String baseUrl;

    public PodMasterStatusApiBuilder(final String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public PodMasterStatusApi build() {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(new OkHttpClient())
                .build()
                .create(PodMasterStatusApi.class);
    }
}
