package com.involvd.sdk.ui.bug_list

import com.involvd.R
import com.involvd.sdk.data.models.FeatureRequest
import com.involvd.sdk.ui.app_list.BaseReportListFragment
import com.involvd.sdk.ui.base_list.BaseReportAdapter
import com.involvd.sdk.ui.view_feature_request.ViewFeatureRequestActivity

class FeatureRequestListFragment : BaseReportListFragment<FeatureRequestListView, FeatureRequestListPresenter, FeatureRequest, FeatureRequestAdapter>(), FeatureRequestListView {

    override fun getSearchString(): String {
        return getString(R.string.progress_feature_requests)
    }

    override fun createAdapter(): FeatureRequestAdapter {
        val adapter = FeatureRequestAdapter(activity!!)
        adapter.setOnClickListener(object : BaseReportAdapter.OnClickListener<FeatureRequest> {
            override fun onClick(t: FeatureRequest) {
                val i = ViewFeatureRequestActivity.getLaunchIntent(activity!!, t)
                startActivity(i)
            }
            override fun onVoteClick(t: FeatureRequest, voteUp: Boolean?) {
                //TODO
            }
        })
        return adapter
    }

    override fun createPresenter(): FeatureRequestListPresenter {
        return FeatureRequestListPresenter(activity!!.packageName)
    }

}