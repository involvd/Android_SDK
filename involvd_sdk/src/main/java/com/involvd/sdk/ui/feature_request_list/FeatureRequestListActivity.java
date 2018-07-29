package com.involvd.sdk.ui.feature_request_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.involvd.sdk.ui.BaseMvpActivity;
import com.involvd.sdk.ui.base_report.BaseCreateReportFragment;
import com.involvd.sdk.ui.bug_list.BugReportListActivity;
import com.involvd.sdk.ui.bug_list.FeatureRequestListFragment;

public class FeatureRequestListActivity extends BaseMvpActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showHomeAsUp();
    }

    @Override
    protected String getFragmentName() {
        return FeatureRequestListFragment.class.getName();
    }

    public static Intent getLaunchIntent(Context context, String userIdentifier) {
        Intent i = new Intent(context, FeatureRequestListActivity.class);
        if(!TextUtils.isEmpty(userIdentifier))
            i.putExtra(BaseCreateReportFragment.USER_ID, userIdentifier);
        return i;
    }

}
