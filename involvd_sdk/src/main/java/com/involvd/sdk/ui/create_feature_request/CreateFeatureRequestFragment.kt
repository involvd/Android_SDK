package com.involvd.sdk.ui.create_bug_report

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.google.android.material.textfield.TextInputLayout
import android.view.View
import com.involvd.BuildConfig
import com.involvd.R
import com.involvd.sdk.data.models.BaseReport
import com.involvd.sdk.data.models.FeatureRequest
import com.involvd.sdk.ui.base_report.BaseCreateReportFragment

open class CreateFeatureRequestFragment : BaseCreateReportFragment<FeatureRequest, CreateFeatureRequestView, CreateFeatureRequestPresenter>(), CreateFeatureRequestView {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextInputLayout>(R.id.description_hint).hint = getString(R.string.hint_feature_req_text)
    }

    override fun getSuccessResId(): Int {
        return R.string.create_feature_request_success
    }

    override fun createPresenter(): CreateFeatureRequestPresenter {
        return CreateFeatureRequestPresenter(activity!!)
    }

}