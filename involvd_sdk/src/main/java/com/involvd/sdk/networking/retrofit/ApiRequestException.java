package com.involvd.sdk.networking.retrofit;

import android.util.Log;

import com.involvd.BuildConfig;
import com.involvd.R;

import java.io.IOException;
import java.net.SocketTimeoutException;

import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by rjoseph on 04/11/2016.
 */
public class ApiRequestException extends RuntimeException {

    private final int errorMsgId;
    private final Kind kind;

    public static ApiRequestException httpError(final String url, final Response response, final Retrofit retrofit) {
        final String message = response.code() + " " + response.message();
        return new ApiRequestException(message, url, response, response.code() == 401 ? Kind.PERMISSION_DENIED : Kind.HTTP, null, retrofit);
    }

    public static ApiRequestException networkError(final IOException exception) {
        return new ApiRequestException(exception.getMessage(), null, null, Kind.NETWORK, exception, null);
    }

    public static ApiRequestException unexpectedError(final Throwable exception) {
        return new ApiRequestException(exception.getMessage(), null, null, Kind.UNEXPECTED, exception, null);
    }

    public ApiRequestException(int errorMsgId, Kind kind) {
        this.errorMsgId = errorMsgId;
        this.kind = kind;
    }

    public ApiRequestException(final String message, final String url, final Response response, final Kind kind, final Throwable exception, final Retrofit retrofit) {
        super(message, exception);
        switch (kind) {
            case NETWORK:
                if(exception instanceof SocketTimeoutException)
                    errorMsgId = R.string.error_network_timeout_exception;
                else
                    errorMsgId = R.string.error_network_exception;
                break;
            case HTTP:
            case UNEXPECTED:
            default:
                errorMsgId = R.string.error_unknown;
                break;
        }
        this.kind = kind;
        if (BuildConfig.DEBUG) {
            logError(response, kind);
        }
    }

    private void logError(final Response response, final Kind kind) {
        try {
            final StringBuilder sb = new StringBuilder("kind: " + kind.name());
            sb.append(", status code: " + response.code());
            sb.append(", body: ");
            try {
                sb.append(response.errorBody().string());
            } catch (final IOException e) {
                sb.append("BODY_UNRECOVERABLE");
            }
            Log.e(getClass().getSimpleName(), sb.toString());
        } catch (final Exception ignore) {
        }
    }

    /**
     * Identifies the event kind which triggered a {@link ApiRequestException}.
     */
    public enum Kind {
        /**
         * An {@link IOException} occurred while communicating to the server.
         */
        NETWORK,
        /**
         * A non-200 HTTP status code was received from the server.
         */
        HTTP,
        /**
         * An internal error occurred while attempting to execute a request. It is best practice to
         * re-throw this exception so your application crashes.
         */
        UNEXPECTED,
        PERMISSION_DENIED
    }

}
