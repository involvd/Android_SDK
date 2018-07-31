package com.involvd.sdk.ui.bug_list

import android.os.Bundle
import android.view.View
import com.involvd.R
import com.involvd.sdk.data.models.BugVote
import com.involvd.sdk.data.viewmodels.BugReportViewModel
import com.involvd.sdk.ui.app_list.BaseReportListFragment
import com.involvd.sdk.ui.base_list.BaseReportAdapter
import com.involvd.sdk.ui.base_report.BaseCreateReportFragment
import com.involvd.sdk.ui.view_bug_report.ViewBugReportActivity

class BugReportListFragment : BaseReportListFragment<BugReportListView, BugReportListPresenter, BugReportViewModel, BugReportAdapter, BugVote>(), BugReportListView {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(arguments?.containsKey(BaseCreateReportFragment.USER_ID) == true) {
            getPresenter().userIdentifier = arguments!!.getString(BaseCreateReportFragment.USER_ID)
            adapter.userIdentifier = arguments!!.getString(BaseCreateReportFragment.USER_ID)
        }
    }

    override fun getSearchString(): String {
        return getString(R.string.progress_bug_reports)
    }

    override fun createAdapter(): BugReportAdapter {
        val adapter = BugReportAdapter(activity!!)
        adapter.setOnClickListener(object : BaseReportAdapter.OnClickListener<BugReportViewModel> {
            override fun onClick(t: BugReportViewModel) {
                val i = ViewBugReportActivity.getLaunchIntent(activity!!, t)
                startActivity(i)
            }
            override fun onVoteClick(bugReport: BugReportViewModel, voteUp: Boolean?) {
                getPresenter().voteOn(activity!!, bugReport, voteUp);
            }
        })
        return adapter
    }

    override fun createPresenter(): BugReportListPresenter {
        return BugReportListPresenter(activity!!.packageName)
    }

    override fun setVote(viewModel: BugReportViewModel, vote: BugVote) {
        viewModel._votes.add(vote)
    }

}