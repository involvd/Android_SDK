package com.involvd.sdk.ui.main_activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.involvd.R;
import com.involvd.sdk.ui.bug_list.BugReportListFragment;
import com.involvd.sdk.ui.bug_list.FeatureRequestListFragment;

/**
 * Created by jj on 18/12/17.
 */

public class AppPagerAdapter extends FragmentPagerAdapter {

    private static final String TAG = AppPagerAdapter.class.getSimpleName();

    public static final int POS_BUGS = 0;
    public static final int POS_FEATURES = 1;

    private final Context context;

    public AppPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = createBundle();
        Fragment fragment;
        switch (position) {
            case POS_BUGS:
                fragment = Fragment.instantiate(context, BugReportListFragment.class.getName(), bundle);
                return fragment;
            case POS_FEATURES:
                fragment = Fragment.instantiate(context, FeatureRequestListFragment.class.getName(), bundle);
                return fragment;
            default:
                return null;
        }
    }

    protected Bundle createBundle() {
        return new Bundle();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case POS_BUGS:
                return getContext().getString(R.string.tab_bug_reports);
            case POS_FEATURES:
                return getContext().getString(R.string.tab_feature_requests);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

}
