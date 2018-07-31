package com.involvd.sdk.ui.bug_list

import com.involvd.sdk.data.models.BugReport
import com.involvd.sdk.data.models.BugVote
import com.involvd.sdk.data.viewmodels.BugReportViewModel
import com.involvd.sdk.ui.app_list.BaseReportListView
import com.robj.radicallyreusable.base.Searchable
import com.robj.radicallyreusable.base.base_list.BaseListView

interface BugReportListView : BaseReportListView<BugReportViewModel, BugVote>