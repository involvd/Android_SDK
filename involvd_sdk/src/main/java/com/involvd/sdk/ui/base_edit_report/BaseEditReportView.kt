package com.involvd.sdk.ui.create_bug_report

import com.involvd.sdk.data.models.BaseReport
import com.involvd.sdk.ui.create_bug_report.BaseReportView

interface BaseEditReportView<T : BaseReport> : BaseReportView {

    fun showProgress()
    fun hideProgress()
    fun showError(errorResId: Int)
    fun populate(report: T)

}
