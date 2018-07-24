package com.involvd.sdk.ui.create_feature_request;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.involvd.sdk.ui.BaseMvpActivity;
import com.involvd.sdk.ui.create_bug_report.CreateBugReportActivity;
import com.involvd.sdk.ui.create_bug_report.CreateFeatureRequestFragment;

public class CreateFeatureRequestActivity extends CreateBugReportActivity {

    @Override
    protected String getFragmentName() {
        return CreateFeatureRequestFragment.class.getName();
    }

}
