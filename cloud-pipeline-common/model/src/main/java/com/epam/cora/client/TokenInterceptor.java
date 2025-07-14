package com.epam.cora.client;

import lombok.RequiredArgsConstructor;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

@RequiredArgsConstructor
public class TokenInterceptor implements Interceptor {

    private final String token;

    /**
     * Adds an authorization header with the provided {@link #token} on the request
     * or response.
     *
     * @see Interceptor
     */
    @Override
    public Response intercept(final Interceptor.Chain chain) throws IOException {
        final Request original = chain.request();
        final Request request = original.newBuilder().header("Authorization", String.format("Bearer %s", token)).build();
        return chain.proceed(request);
    }
}
