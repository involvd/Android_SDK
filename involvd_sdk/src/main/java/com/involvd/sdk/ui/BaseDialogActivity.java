package com.involvd.sdk.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.involvd.R;

public abstract class BaseDialogActivity extends BaseMvpActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.involvd_activity_dialog);
        Bundle bundle = getIntent() != null && getIntent().getExtras() != null ? getIntent().getExtras() : new Bundle();
        Fragment fragment = Fragment.instantiate(this, getFragmentName(), bundle);
        pushFragment(fragment, false);
        findViewById(R.id.cancel_btn).setOnClickListener(v -> finish());
    }

    @Override
    public void setTitle(CharSequence title) {
        ((TextView) findViewById(R.id.dialog_title)).setText(title);
    }

    @Override
    public void setTitle(int titleId) {
        ((TextView) findViewById(R.id.dialog_title)).setText(titleId);
    }
}
