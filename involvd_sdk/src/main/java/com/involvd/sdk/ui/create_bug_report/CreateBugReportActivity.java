package com.involvd.sdk.ui.create_bug_report;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.involvd.sdk.ui.BaseMvpActivity;
import com.involvd.sdk.ui.base_report.BaseCreateReportFragment;

public class CreateBugReportActivity extends BaseMvpActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showHomeAsUp();
    }

    @Override
    protected String getFragmentName() {
        return CreateBugReportFragment.class.getName();
    }

    public static Intent getLaunchIntent(Context context, String userIdentifier) {
        Intent i = new Intent(context, CreateBugReportActivity.class);
        if(!TextUtils.isEmpty(userIdentifier))
            i.putExtra(BaseCreateReportFragment.USER_ID, userIdentifier);
        return i;
    }

}
