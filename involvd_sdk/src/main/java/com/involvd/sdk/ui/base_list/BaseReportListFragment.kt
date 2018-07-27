package com.involvd.sdk.ui.app_list

import android.os.Bundle
import android.view.View
import com.involvd.sdk.data.models.BaseReport
import com.involvd.sdk.ui.base_list.BaseReportAdapter
import com.robj.radicallyreusable.base.Searchable
import com.robj.radicallyreusable.base.base_list.BaseListFragment

abstract class BaseReportListFragment<V: BaseReportListView, P: BaseReportListPresenter<T, V>, T : BaseReport, A: BaseReportAdapter<T>> : BaseListFragment<V, P, A, Any>(), BaseReportListView {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSwipeToRefreshEnabled(true)
        getPresenter().loadReports(activity!!)
    }

    override fun onRefresh() {
        super.onRefresh()
        getPresenter().refresh(activity!!)
    }

    override fun addOrReplaceResult(result: Searchable?) {
        adapter.addOrReplace(result)
    }

}