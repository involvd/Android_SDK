package com.involvd.sdk.networking.retrofit;

import android.content.Context;
import android.support.annotation.VisibleForTesting;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.involvd.BuildConfig;
import com.involvd.sdk.utils.SdkUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by rjoseph on 25/10/2016.
 */

public class ApiClient {

    private static final String BASE_API_URL = "https://us-central1-involvd-app.cloudfunctions.net";

    private static ApiService apiService;

    public static ApiService getInstance(Context context) {
        if(apiService == null)
            apiService = getApiService(context);
        return apiService;
    }

    private static ApiService getApiService(Context context) {
        final OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30,TimeUnit.SECONDS);
        builder.addInterceptor(new NetworkCheckIntercepter(context));
        builder.addInterceptor(chain -> {
            String appId = SdkUtils.getAppIdForPackage(context, context.getPackageName());
            if(!appId.equals(context.getPackageName()))
                appId = context.getPackageName() + ":" + appId;
            String apiKey = SdkUtils.getApiKeyForPackage(context, context.getPackageName());
            String sigHash = SdkUtils.getCertificateSHA1Fingerprint(context, context.getPackageName());
            Request newRequest = chain.request().newBuilder()
                    .addHeader("app_id", appId)
                    .addHeader("api_key", apiKey)
                    .addHeader("hash", sigHash)
                    .build();
            return chain.proceed(newRequest);
        });
        if (BuildConfig.DEBUG) {
            final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            builder.addInterceptor(httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)); //Use log level of BODY if you NEEED the body, otherwise use BASIC
        }
        return getDeepThoughtService(BASE_API_URL, builder.build());
    }

    @VisibleForTesting
    private static ApiService getDeepThoughtService(final String url, final OkHttpClient okHttpClient) {
        final OkHttpClient client = okHttpClient;
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
//                .addConverterFactory(ScalarsConverterFactory.create())//Used if we want to send data as string in body of the post request.
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create()) //Always run requests on background thread
                .addConverterFactory(JacksonConverterFactory.create(new ObjectMapper()))
                .client(client)
                .build();
        return retrofit.create(ApiService.class);
    }

}
