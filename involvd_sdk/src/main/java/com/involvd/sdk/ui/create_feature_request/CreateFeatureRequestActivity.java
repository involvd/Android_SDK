package com.involvd.sdk.ui.create_feature_request;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.involvd.sdk.ui.BaseMvpActivity;
import com.involvd.sdk.ui.base_report.BaseCreateReportFragment;
import com.involvd.sdk.ui.create_bug_report.CreateBugReportActivity;
import com.involvd.sdk.ui.create_bug_report.CreateFeatureRequestFragment;

public class CreateFeatureRequestActivity extends CreateBugReportActivity {

    @Override
    protected String getFragmentName() {
        return CreateFeatureRequestFragment.class.getName();
    }

    public static Intent getLaunchIntent(Context context, String userIdentifier) {
        Intent i = new Intent(context, CreateFeatureRequestActivity.class);
        if(!TextUtils.isEmpty(userIdentifier))
            i.putExtra(BaseCreateReportFragment.USER_ID, userIdentifier);
        return i;
    }

}
