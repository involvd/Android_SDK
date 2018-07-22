package com.robj.involvd.ui.app_list

import com.robj.radicallyreusable.base.Searchable
import com.robj.radicallyreusable.base.base_list.BaseListView

interface BugReportListView : BaseListView<Any> {

    fun addOrReplaceResult(result: Searchable?)

}
