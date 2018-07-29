package com.involvd.sdk.ui.app_list

import com.robj.radicallyreusable.base.Searchable
import com.robj.radicallyreusable.base.base_list.BaseListView

interface BaseReportListView : BaseListView<Any> {

    fun addOrReplaceResult(result: Searchable?)
    fun hasMoreToLoad(hasMoreToLoad: Boolean)
    fun adjustVoteCount(t: Any, voteUp: Boolean?)
    fun clearInProgress(result: Searchable?)

}
