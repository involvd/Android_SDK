package com.involvd.sdk.ui.bug_list

import android.content.Context
import android.text.TextUtils
import com.involvd.sdk.data.models.BugReport
import com.involvd.sdk.ui.base_list.BaseReportAdapter
import com.involvd.sdk.utils.SdkUtils

class BugReportAdapter(context: Context) : BaseReportAdapter<BugReport>(context) {

    override fun hasVotedUp(submittedBy: BugReport): Boolean {
        return false
    }

    override fun hasVotedDown(viewModel: BugReport): Boolean {
        return false
    }

    var userIdentifier: String = ""
        get() {
            if(TextUtils.isEmpty(field))
                field = SdkUtils.createUniqueIdentifier(context)
            return field
        }
        set(userIdentifier) { this.userIdentifier = userIdentifier }

    override fun getStatusLabelResId(viewModel: BugReport): Int {
        return viewModel.getStatus().labelResId
    }


}
