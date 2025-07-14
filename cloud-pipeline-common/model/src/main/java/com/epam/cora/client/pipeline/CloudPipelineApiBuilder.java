package com.epam.cora.client.pipeline;

import com.epam.cora.client.TokenInterceptor;
import com.epam.cora.config.JsonMapper;
import com.epam.cora.utils.URLUtils;
import com.fasterxml.jackson.databind.DeserializationFeature;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class CloudPipelineApiBuilder {

    private static final String PIPELINE_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    private static final JacksonConverterFactory CONVERTER =
            JacksonConverterFactory
                    .create(new JsonMapper()
                            .setDateFormat(new SimpleDateFormat(PIPELINE_DATE_FORMAT))
                            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false));

    private final int connectTimeout;
    private final int readTimeout;
    private final String apiHost;
    private final String token;

    public CloudPipelineAPI buildClient() {
        return new Retrofit.Builder()
                .baseUrl(URLUtils.normalizeUrl(apiHost))
                .addConverterFactory(CONVERTER)
                .client(buildHttpClient())
                .build()
                .create(CloudPipelineAPI.class);
    }

    private OkHttpClient buildHttpClient() {
        final TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }
                }
        };
        final SSLContext sslContext = buildSSLContext(trustAllCerts);
        final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0]);
        builder.hostnameVerifier((hostname, session) -> true);
        return builder.readTimeout(readTimeout, TimeUnit.SECONDS)
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .hostnameVerifier((s, sslSession) -> true)
                .addInterceptor(new TokenInterceptor(token))
                .build();
    }

    private SSLContext buildSSLContext(TrustManager[] trustAllCerts) {
        try {
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            return sslContext;
        } catch (GeneralSecurityException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }

    }
}
