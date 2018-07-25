package com.involvd.sdk.ui.bug_list

import android.os.Bundle
import android.view.View
import com.involvd.BuildConfig
import com.involvd.R
import com.involvd.sdk.data.models.BaseReport
import com.involvd.sdk.data.models.FeatureRequest
import com.robj.involvd.ui.app_list.BaseReportListFragment

class FeatureRequestListFragment : BaseReportListFragment<FeatureRequestListView, FeatureRequestListPresenter, FeatureRequest, FeatureRequestAdapter>(), FeatureRequestListView {

    override fun getSearchString(): String {
        return getString(R.string.progress_bug_reports)
    }

    override fun createAdapter(): FeatureRequestAdapter {
        val adapter = FeatureRequestAdapter(activity!!)
        return adapter
    }

    override fun createPresenter(): FeatureRequestListPresenter {
        return FeatureRequestListPresenter(BuildConfig.APPLICATION_ID)
    }

}