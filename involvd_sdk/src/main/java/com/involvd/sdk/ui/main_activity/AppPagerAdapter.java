package com.involvd.sdk.ui.main_activity;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import android.text.TextUtils;

import com.involvd.R;
import com.involvd.sdk.ui.base_report.BaseCreateReportFragment;
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
    private final String userIdentifier;

    public AppPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
        this.userIdentifier = null;
    }

    public AppPagerAdapter(Context context, FragmentManager fm, String userIdentifier) {
        super(fm);
        this.context = context;
        this.userIdentifier = userIdentifier;
    }

    protected Context getContext() {
        return context;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = createBundle();
        Fragment fragment;
        switch (position) {
            case POS_BUGS:
                fragment = Fragment.instantiate(context, getBugFragmentClass().getName(), bundle);
                return fragment;
            case POS_FEATURES:
                fragment = Fragment.instantiate(context, getFeatureRequestFragmentClass().getName(), bundle);
                return fragment;
            default:
                return null;
        }
    }

    protected Class getBugFragmentClass() {
        return BugReportListFragment.class;
    }

    protected Class getFeatureRequestFragmentClass() {
        return FeatureRequestListFragment.class;
    }

    protected Bundle createBundle() {
        Bundle bundle = new Bundle();
        if(!TextUtils.isEmpty(userIdentifier))
            bundle.putString(BaseCreateReportFragment.USER_ID, userIdentifier);
        return bundle;
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
