package com.involvd.sdk.ui.bug_list

import com.involvd.sdk.data.models.FeatureRequest
import com.involvd.sdk.data.models.FeatureVote
import com.involvd.sdk.data.viewmodels.FeatureRequestViewModel
import com.involvd.sdk.ui.app_list.BaseReportListView
import com.robj.radicallyreusable.base.Searchable
import com.robj.radicallyreusable.base.base_list.BaseListView

interface FeatureRequestListView : BaseReportListView<FeatureRequestViewModel, FeatureVote>