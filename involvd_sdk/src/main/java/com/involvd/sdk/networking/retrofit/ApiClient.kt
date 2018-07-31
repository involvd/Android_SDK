package com.involvd.sdk.networking.retrofit

import android.content.Context

import com.fasterxml.jackson.databind.ObjectMapper
import com.involvd.BuildConfig
import com.involvd.R
import com.involvd.sdk.data.PrefManager
import com.involvd.sdk.utils.SdkUtils
import io.reactivex.FlowableTransformer
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import okhttp3.Interceptor

import java.util.concurrent.TimeUnit

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import java.net.SocketTimeoutException

/**
 * Created by Rob J on 25/07/2018.
 */

object ApiClient {

    private val BASE_API_URL = "https://us-central1-involvd-app.cloudfunctions.net"

    private var apiService: ApiService? = null
    val authPublishSubject = PublishSubject.create<Boolean>()

    @JvmStatic
    fun getAuthPublishSubect(): PublishSubject<Boolean> {
        return authPublishSubject
    }

    @JvmStatic
    fun getInstance(context: Context): ApiService {
        if (apiService == null)
            apiService = getApiService(context)
        return apiService as ApiService
    }

    private fun getApiService(context: Context): ApiService {
        val builder = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
        builder.addInterceptor(NetworkCheckIntercepter(context))
        builder.addInterceptor { chain ->
            var appId = SdkUtils.getAppIdForPackage(context, context.packageName)
            if (appId != context.packageName)
                appId = context.packageName + ":" + appId
            val apiKey = SdkUtils.getApiKeyForPackage(context, context.packageName)
            val sigHash = SdkUtils.getCertificateSHA1Fingerprint(context, context.packageName)
            val newRequest = chain.request().newBuilder()
                    .addHeader("app_id", appId?:null)
                    .addHeader("api_key", apiKey?:null)
                    .addHeader("hash", sigHash?:null)
                    .build()
            chain.proceed(newRequest)
        }
        builder.addInterceptor {
                chain: Interceptor.Chain ->
            val request = chain.request();
            val response = chain.proceed(request);
            if (response.code() == 200) {
                PrefManager.setIsValid(context, true)
                authPublishSubject.onNext(true)
            } else if(response.code() == 401) {
                PrefManager.setIsValid(context, false)
                authPublishSubject.onNext(false)
            }
            response;
        }
        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            builder.addInterceptor(httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)) //Use log level of BODY if you NEEED the body, otherwise use BASIC
        }

        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_API_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create(ObjectMapper()))
                .client(builder.build())
                .build()
        return retrofit.create(ApiService::class.java)
    }


        fun <T> applyFlowableRules(): FlowableTransformer<T, T> {
            return FlowableTransformer { observable ->
                observable
                        .onErrorReturn{
                            if(it is SocketTimeoutException)
                                throw ApiException(R.string.error_network_timeout_exception)
                            throw it
                        }
            }
        }

    class ApiException(val errorResId: Int) : RuntimeException()

}
