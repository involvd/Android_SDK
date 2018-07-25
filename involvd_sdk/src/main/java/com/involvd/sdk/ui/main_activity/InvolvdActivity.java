package com.involvd.sdk.ui.main_activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.involvd.sdk.ui.BaseMvpActivity;
import com.robj.involvd.ui.app.InvolvdFragment;

public class InvolvdActivity extends BaseMvpActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(null);
        showHomeAsUp();
    }

    @Override
    protected String getFragmentName() {
        return InvolvdFragment.class.getName();
    }

}
