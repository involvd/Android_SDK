package com.involvd.sdk.ui.create_bug_report

import com.involvd.R
import com.involvd.sdk.data.models.BugReport

class ViewBugReportFragment : BaseEditReportFragment<BugReport, BaseEditReportView<BugReport>, BaseEditReportPresenter<BugReport, BaseEditReportView<BugReport>>>() {

    override fun getSuccessResId(): Int {
        return R.string.error_sdk_unknown
    }

    override fun createPresenter(): BaseEditReportPresenter<BugReport, BaseEditReportView<BugReport>> {
        return object : BaseEditReportPresenter<BugReport, BaseEditReportView<BugReport>>() {
            override fun getReportClass(): Class<BugReport> {
                return BugReport::class.java
            }
        }
    }

}