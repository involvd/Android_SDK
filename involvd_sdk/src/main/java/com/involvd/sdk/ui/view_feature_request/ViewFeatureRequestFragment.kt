package com.involvd.sdk.ui.create_bug_report

import com.involvd.R
import com.involvd.sdk.data.models.FeatureRequest

class ViewFeatureRequestFragment : BaseEditReportFragment<FeatureRequest, BaseEditReportView<FeatureRequest>, BaseEditReportPresenter<FeatureRequest, BaseEditReportView<FeatureRequest>>>() {

    override fun getSuccessResId(): Int {
        return R.string.error_unknown
    }

    override fun createPresenter(): BaseEditReportPresenter<FeatureRequest, BaseEditReportView<FeatureRequest>> {
        return object : BaseEditReportPresenter<FeatureRequest, BaseEditReportView<FeatureRequest>>() {
            override fun getReportClass(): Class<FeatureRequest> {
                return FeatureRequest::class.java
            }
        }
    }

}