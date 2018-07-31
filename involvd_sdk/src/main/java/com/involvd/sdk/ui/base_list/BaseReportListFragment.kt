package com.involvd.sdk.ui.app_list

import android.os.Bundle
import android.view.View
import com.involvd.sdk.data.models.BaseReport
import com.involvd.sdk.data.models.BaseVote
import com.involvd.sdk.ui.base_list.BaseReportAdapter
import com.robj.radicallyreusable.base.Searchable
import com.robj.radicallyreusable.base.base_list.BaseListFragment

abstract class BaseReportListFragment<V: BaseReportListView<T, VT>, P: BaseReportListPresenter<T, *, V>, T : BaseReport, A: BaseReportAdapter<T>, VT: BaseVote> : BaseListFragment<V, P, A, Any>(), BaseReportListView<T, VT> {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSwipeToRefreshEnabled(true)
        setOnScrollToLoadListener {
            getPresenter().loadReports(activity!!)
        }
        getPresenter().loadReports(activity!!)
    }

    override fun onRefresh() {
        super.onRefresh()
        getPresenter().refresh(activity!!)
    }

    override fun addOrReplaceResult(result: Searchable?) {
        adapter.addOrReplace(result)
    }

    override fun clearInProgress(result: Searchable?) {
        adapter.clearInProgress(result)
    }

    override fun adjustVoteCount(t: T, vote: VT) {
        when(vote.votedUp) {
            true -> (t as BaseReport).upvotes++
            false -> (t as BaseReport).downvotes++
            else -> { } //TODO: ??
        }
        setVote(t, vote)
        addOrReplaceResult(t as BaseReport)
    }

    abstract fun setVote(t: T, vote: VT)

}