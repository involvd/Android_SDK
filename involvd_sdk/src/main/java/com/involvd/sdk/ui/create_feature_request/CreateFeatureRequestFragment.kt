package com.involvd.sdk.ui.create_bug_report

import android.app.Activity
import android.content.Intent
import com.involvd.BuildConfig
import com.involvd.R
import com.involvd.sdk.data.models.BaseReport

open class CreateFeatureRequestFragment : BaseReportFragment<BaseReport, CreateFeatureRequestView, CreateFeatureRequestPresenter>(), CreateFeatureRequestView {

    override fun getSuccessResId(): Int {
        return R.string.create_feature_request_success
    }

    override fun createPresenter(): CreateFeatureRequestPresenter {
        return CreateFeatureRequestPresenter(activity!!)
    }

}