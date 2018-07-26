package com.involvd.sdk.ui.app_list

import com.robj.radicallyreusable.base.Searchable
import com.robj.radicallyreusable.base.base_list.BaseListView

interface BaseReportListView : BaseListView<Any> {

    fun addOrReplaceResult(result: Searchable?)

}
