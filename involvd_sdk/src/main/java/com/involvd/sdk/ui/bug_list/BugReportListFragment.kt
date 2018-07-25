package com.involvd.sdk.ui.bug_list

import android.os.Bundle
import android.view.View
import com.involvd.BuildConfig
import com.involvd.R
import com.involvd.sdk.data.models.BaseReport
import com.involvd.sdk.data.models.BugReport
import com.robj.involvd.ui.app_list.BaseReportListFragment

class BugReportListFragment : BaseReportListFragment<BugReportListView, BugReportListPresenter, BugReport, BugReportAdapter>(), BugReportListView {

    override fun getSearchString(): String {
        return getString(R.string.progress_bug_reports)
    }

    override fun createAdapter(): BugReportAdapter {
        val adapter = BugReportAdapter(activity!!)
        return adapter
    }

    override fun createPresenter(): BugReportListPresenter {
        return BugReportListPresenter(BuildConfig.APPLICATION_ID)
    }

}