package com.involvd.sdk.ui.bug_list

import android.content.Context
import android.text.TextUtils
import com.involvd.sdk.data.viewmodels.BugReportViewModel
import com.involvd.sdk.ui.base_list.BaseReportAdapter
import com.involvd.sdk.utils.SdkUtils

class BugReportAdapter(context: Context) : BaseReportAdapter<BugReportViewModel>(context) {

    override fun hasVotedUp(viewModel: BugReportViewModel): Boolean {
        return viewModel.hasVotedUp?:false
    }

    override fun hasVotedDown(viewModel: BugReportViewModel): Boolean {
        if(viewModel.hasVotedUp != null)
            return !viewModel.hasVotedUp!!
        return false
    }

    var userIdentifier: String = ""
        get() {
            if(TextUtils.isEmpty(field))
                field = SdkUtils.createUniqueIdentifier(context)
            return field
        }
        set(userIdentifier) { this.userIdentifier = userIdentifier }

    override fun getStatusLabelResId(viewModel: BugReportViewModel): Int {
        return viewModel.getStatus().labelResId
    }


}
