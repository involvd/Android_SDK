package com.involvd.sdk.ui.bug_list;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.involvd.sdk.ui.BaseMvpActivity;

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

}
