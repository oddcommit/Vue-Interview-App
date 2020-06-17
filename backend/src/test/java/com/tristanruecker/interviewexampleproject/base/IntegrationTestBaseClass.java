package com.tristanruecker.interviewexampleproject.base;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class IntegrationTestBaseClass {

    @LocalServerPort
    protected int randomServerPort;

    protected <T> T createRetrofitClient(Class<T> className) {
        Retrofit retrofit = createRetrofitClient(new OkHttpClient.Builder());
        return retrofit.create(className);
    }


    protected <T> T createRetrofitClient(Class<T> className, String token) {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    Request.Builder newRequest = request
                            .newBuilder()
                            .header("Authorization",
                                    "Bearer " + token);
                    return chain.proceed(newRequest.build());
                });

        Retrofit retrofit = createRetrofitClient(okHttpClientBuilder);
        return retrofit.create(className);
    }


    private Retrofit createRetrofitClient(OkHttpClient.Builder okHttpClientBuilder) {
        return new Retrofit.Builder()
                .baseUrl("http://localhost:" + randomServerPort + "/api/")
                .addConverterFactory(JacksonConverterFactory.create())
                .client(okHttpClientBuilder.build())
                .build();
    }

}
