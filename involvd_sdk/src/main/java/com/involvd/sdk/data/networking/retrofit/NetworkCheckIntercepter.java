package com.involvd.sdk.data.networking.retrofit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.involvd.R;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by jj on 10/03/17.
 */

public class NetworkCheckIntercepter implements okhttp3.Interceptor {

    private final Context context;

    public NetworkCheckIntercepter(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(final Chain chain) throws IOException {
        if (!hasNetworkConnection(getContext())) {
            throw new ApiRequestException(R.string.error_network_error, ApiRequestException.Kind.NETWORK);
        } else {
            return chain.proceed(chain.request());
        }
    }

    public static boolean hasNetworkConnection(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private Context getContext() {
        return context;
    }

}