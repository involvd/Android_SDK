package com.involvd.sdk.ui.app_list

import com.involvd.sdk.data.models.BaseReport
import com.involvd.sdk.data.models.BaseVote
import com.robj.radicallyreusable.base.Searchable
import com.robj.radicallyreusable.base.base_list.BaseListView

interface BaseReportListView<T: BaseReport, VT: BaseVote> : BaseListView<Any> {

    fun addOrReplaceResult(result: Searchable?)
    fun hasMoreToLoad(hasMoreToLoad: Boolean)
    fun adjustVoteCount(t: T, vote: VT)
    fun clearInProgress(result: Searchable?)

}
