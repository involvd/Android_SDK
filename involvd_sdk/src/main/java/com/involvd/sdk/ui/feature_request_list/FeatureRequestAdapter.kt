package com.involvd.sdk.ui.bug_list

import android.content.Context
import android.text.TextUtils
import com.involvd.sdk.data.models.FeatureRequest
import com.involvd.sdk.ui.base_list.BaseReportAdapter
import com.involvd.sdk.utils.SdkUtils

class FeatureRequestAdapter(context: Context) : BaseReportAdapter<FeatureRequest>(context) {

    override fun hasVotedUp(viewModel: FeatureRequest): Boolean {
        return false
    }

    override fun hasVotedDown(viewModel: FeatureRequest): Boolean {
        return false
    }

    var userIdentifier: String = ""
        get() {
            if(TextUtils.isEmpty(field))
                field = SdkUtils.createUniqueIdentifier(context)
            return field
        }
        set(userIdentifier) { this.userIdentifier = userIdentifier }

    override fun getStatusLabelResId(viewModel: FeatureRequest): Int {
        return viewModel.getStatus().labelResId
    }


}
