package com.involvd.sdk.ui.bug_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.text.TextUtils;

import com.involvd.sdk.ui.BaseMvpActivity;
import com.involvd.sdk.ui.base_report.BaseCreateReportFragment;

public class BugReportListActivity extends BaseMvpActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showHomeAsUp();
    }

    @Override
    protected String getFragmentName() {
        return BugReportListFragment.class.getName();
    }

    public static Intent getLaunchIntent(Context context, String userIdentifier) {
        Intent i = new Intent(context, BugReportListActivity.class);
        if(!TextUtils.isEmpty(userIdentifier))
            i.putExtra(BaseCreateReportFragment.USER_ID, userIdentifier);
        return i;
    }

}
