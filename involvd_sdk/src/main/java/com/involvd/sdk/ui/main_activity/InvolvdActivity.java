package com.involvd.sdk.ui.main_activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.involvd.sdk.ui.BaseMvpActivity;
import com.robj.involvd.ui.app.InvolvdFragment;

public class InvolvdActivity extends BaseMvpActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showHomeAsUp();
    }

    @Override
    protected String getFragmentName() {
        return InvolvdFragment.class.getName();
    }

    public static Intent getLaunchIntent(Context context, String userIdentifier) {
        Intent i = new Intent(context, InvolvdActivity.class);
        if(!TextUtils.isEmpty(userIdentifier))
            i.putExtra(InvolvdFragment.USER_ID, userIdentifier);
        return i;
    }

}
