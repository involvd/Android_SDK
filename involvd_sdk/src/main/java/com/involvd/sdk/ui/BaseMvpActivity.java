package com.involvd.sdk.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.robj.radicallyreusable.base.mvp.fragment.BaseMvpPresenter;

/**
 * Created by JJ on 21/05/15.
 */
public abstract class BaseMvpActivity extends com.robj.radicallyreusable.base.mvp.activity.BaseMvpActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent() != null && getIntent().getExtras() != null ? getIntent().getExtras() : new Bundle();
        Fragment fragment = Fragment.instantiate(this, getFragmentName(), bundle);
        pushFragment(fragment, false);
    }

    protected abstract String getFragmentName();

    @NonNull
    @Override
    public MvpPresenter createPresenter() {
        return new BaseMvpPresenter() { };
    }

    @Override
    public void showHomeAsUp() {
        super.showHomeAsUp();
    }

    public void hideHomeAsUp() {
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        this.getSupportActionBar().setDisplayShowHomeEnabled(false);
        this.getSupportActionBar().setHomeButtonEnabled(false);
    }

}
