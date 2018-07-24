package com.involvd.sdk.ui.create_bug_report;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.involvd.sdk.ui.BaseMvpActivity;

public class CreateBugReportActivity extends BaseMvpActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(null);
        showHomeAsUp();
    }

    @Override
    protected String getFragmentName() {
        return CreateBugReportFragment.class.getName();
    }

    public static Intent getLaunchIntent(Context context, String appId) {
        Intent i = new Intent(context, CreateBugReportActivity.class);
        i.putExtra("packageName", appId);
        return i;
    }

}
