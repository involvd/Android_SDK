package com.involvd.sdk.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.involvd.R;

public abstract class BaseDialogActivity extends BaseMvpActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        Bundle bundle = getIntent() != null && getIntent().getExtras() != null ? getIntent().getExtras() : new Bundle();
        Fragment fragment = Fragment.instantiate(this, getFragmentName(), bundle);
        pushFragment(fragment, false);
    }

}
