package com.involvd.sdk.ui.bug_list

import android.content.Context
import com.involvd.sdk.data.models.BaseReport
import com.involvd.sdk.data.models.BugReport
import com.involvd.sdk.ui.base_list.BaseReportAdapter

class BugReportAdapter(context: Context) : BaseReportAdapter<BugReport>(context) {
    override fun getStatusLabelResId(viewModel: BugReport): Int {
        return viewModel.getStatus().labelResId
    }


}
