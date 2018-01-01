package com.robj.radicallyreusable.base.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

/**
 * Created by jj on 15/12/17.
 */

public class LaunchUtils {

    public static Intent getUrlIntent(String url) {
        Intent marketIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        marketIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_NEW_TASK);
        return marketIntent;
    }

    public static Intent getEmailIntent(String email, String subject, String body) {
        Intent i = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email, null));
        if(!TextUtils.isEmpty(subject))
            i.putExtra(Intent.EXTRA_SUBJECT, subject);
        if(!TextUtils.isEmpty(body))
            i.putExtra(Intent.EXTRA_TEXT, body);
        return i;
    }

    public static Intent getPhoneIntent(String phoneNo) {
        return new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNo));
    }

    public static Intent getOpenFileIntent(Uri uri) {
        Intent i = new Intent(Intent.ACTION_VIEW, uri);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        return i;
    }

    public static Intent getShareIntent(Context context, Uri uri, String body, String mimeType) {
        Intent i = new Intent();
        i.setAction(Intent.ACTION_SEND);
        if(uri != null)
            i.putExtra(Intent.EXTRA_STREAM, uri);
        if(!TextUtils.isEmpty(body))
            i.putExtra(Intent.EXTRA_TEXT, body);
        if(!TextUtils.isEmpty(mimeType))
            i.setType(mimeType);
        return i;
    }
}
