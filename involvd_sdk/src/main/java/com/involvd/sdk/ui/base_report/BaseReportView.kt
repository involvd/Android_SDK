package com.involvd.sdk.ui.create_bug_report

import com.robj.radicallyreusable.base.mvp.fragment.BaseMvpView

interface BaseReportView : BaseMvpView {

    fun onSubmittedSuccess(reportId: String)

}
