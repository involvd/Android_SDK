package com.involvd.sdk.ui.create_bug_report

import com.involvd.R
import com.involvd.sdk.data.models.BaseReport

open class CreateBugReportFragment : BaseReportFragment<BaseReport, CreateBugReportView, CreateBugReportPresenter>(), CreateBugReportView {

    override fun getSuccessResId() : Int {
        return R.string.create_bug_success
    }

    override fun createPresenter(): CreateBugReportPresenter {
        return CreateBugReportPresenter(activity!!)
    }

}