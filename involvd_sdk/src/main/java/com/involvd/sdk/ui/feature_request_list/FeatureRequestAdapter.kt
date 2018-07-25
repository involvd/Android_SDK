package com.involvd.sdk.ui.bug_list

import android.content.Context
import com.involvd.sdk.data.models.BaseReport
import com.involvd.sdk.data.models.FeatureRequest
import com.involvd.sdk.ui.base_list.BaseReportAdapter

class FeatureRequestAdapter(context: Context) : BaseReportAdapter<FeatureRequest>(context) {
    override fun getStatusLabelResId(viewModel: FeatureRequest): Int {
        return viewModel.getStatus().labelResId
    }


}
