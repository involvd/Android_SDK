package com.involvd.sdk.networking.retrofit;

import android.support.annotation.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import io.reactivex.schedulers.Schedulers;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by rjoseph on 04/11/2016.
 * Based on the post here: http://bytes.babbel.com/en/articles/2016-03-16-retrofit2-rxjava-error-handling.html
 */

public class RxErrorHandlingCallAdapterFactory extends CallAdapter.Factory {
    private final RxJava2CallAdapterFactory original;

    private RxErrorHandlingCallAdapterFactory() {
        original = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()); //Always run requests on background thread
    }

    @Nullable
    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        return original.get(returnType, annotations, retrofit);
    }

    public static CallAdapter.Factory create() {
        return new RxErrorHandlingCallAdapterFactory();
    }

//    @Override
//    public CallAdapter<?> get(final Type returnType, final Annotation[] annotations, final Retrofit retrofit) {
//        return new RxCallAdapterWrapper(retrofit, original.get(returnType, annotations, retrofit));
//    }
//
//    private static class RxCallAdapterWrapper implements CallAdapter<Observable<?>> {
//        private final Retrofit retrofit;
//        private final CallAdapter<?> wrapped;
//
//        public RxCallAdapterWrapper(final Retrofit retrofit, final CallAdapter<?> wrapped) {
//            this.retrofit = retrofit;
//            this.wrapped = wrapped;
//        }
//
//        @Override
//        public Type responseType() {
//            return wrapped.responseType();
//        }
//
//        @Override
//        public <R> Observable<?> adapt(final Call<R> call) {
//            return ((Observable) wrapped.adapt(call))
//                    .onErrorResumeNext(new Func1<Throwable, Observable>() {
//                        @Override
//                        public Observable call(Throwable throwable) {
//                            return Observable.error(asApiRequestException(throwable));
//                        }
//                    });
//        }
//
//        private ApiRequestException asApiRequestException(final Throwable throwable) {
//            //We had 200, but with custom error
//            if (throwable instanceof ApiRequestException) {
//                return (ApiRequestException) throwable;
//            }
//            // We had non-200 http error
//            if (throwable instanceof HttpException) {
//                final HttpException httpException = (HttpException) throwable;
//                final Response response = httpException.response();
//                return ApiRequestException.httpError(response.raw().request().url().toString(), response, retrofit);
//            }
//            // A network error happened
//            if (throwable instanceof IOException) {
//                return ApiRequestException.networkError((IOException) throwable);
//            }
//
//            // We don't know what happened. We need to simply convert to an unknown error
//
//            return ApiRequestException.unexpectedError(throwable);
//        }
//    }
}
