package com.robj.involvd.ui.app_list

import android.os.Bundle
import android.view.View
import com.involvd.R
import com.robj.involvd.data.models.BaseReport
import com.robj.involvd.ui.bugReport_list.BaseReportAdapter
import com.robj.involvd.ui.bugReport_list.BugReportAdapter
import com.robj.radicallyreusable.base.Searchable
import com.robj.radicallyreusable.base.base_list.BaseListFragment

class BugReportListFragment : BaseListFragment<BugReportListView, BugReportListPresenter, BugReportAdapter, Any>(), BugReportListView {

    companion object {
        const val REQ_VOTE_LOGIN = 1001
        const val REQ_VIEW = 1002
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPresenter().loadAcceptedBugReports(activity!!)
    }

    override fun getSearchString(): String {
        return getString(R.string.progress_bug_reports)
    }

    override fun createAdapter(): BugReportAdapter {
        val adapter = BugReportAdapter(activity!!)
        adapter.setOnClickListener(object : BaseReportAdapter.OnClickListener<BaseReport> {
            override fun onClick(bugReport: BaseReport) {
                //TODO
//                val i = EditBugReportActivity.getLaunchIntent(activity, bugReport.packageName, bugReport.getId())
//                startActivityForResult(i, REQ_VIEW)
            }
        })
        return adapter
    }

    override fun addOrReplaceResult(result: Searchable?) {
        adapter.addOrReplace(result)
    }

    override fun createPresenter(): BugReportListPresenter {
        return BugReportListPresenter()
    }

}