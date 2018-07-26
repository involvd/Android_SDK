package com.involvd.sdk.ui.feature_request_list;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.involvd.sdk.ui.BaseMvpActivity;
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

}
