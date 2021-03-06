package com.involvd.sdk.ui.bug_list

import android.os.Bundle
import android.view.View
import com.involvd.R
import com.involvd.sdk.data.models.FeatureVote
import com.involvd.sdk.data.viewmodels.FeatureRequestViewModel
import com.involvd.sdk.ui.app_list.BaseReportListFragment
import com.involvd.sdk.ui.base_list.BaseReportAdapter
import com.involvd.sdk.ui.base_report.BaseCreateReportFragment
import com.involvd.sdk.ui.view_feature_request.ViewFeatureRequestActivity

class FeatureRequestListFragment : BaseReportListFragment<FeatureRequestListView, FeatureRequestListPresenter, FeatureRequestViewModel, FeatureRequestAdapter, FeatureVote>(), FeatureRequestListView {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(arguments?.containsKey(BaseCreateReportFragment.USER_ID) == true) {
            getPresenter().userIdentifier = arguments!!.getString(BaseCreateReportFragment.USER_ID)
            adapter.userIdentifier = arguments!!.getString(BaseCreateReportFragment.USER_ID)
        }
    }

    override fun getSearchString(): String {
        return getString(R.string.progress_feature_requests)
    }

    override fun createAdapter(): FeatureRequestAdapter {
        val adapter = FeatureRequestAdapter(activity!!)
        adapter.setOnClickListener(object : BaseReportAdapter.OnClickListener<FeatureRequestViewModel> {
            override fun onClick(t: FeatureRequestViewModel) {
                val i = ViewFeatureRequestActivity.getLaunchIntent(activity!!, t)
                startActivity(i)
            }
            override fun onVoteClick(featureRequest: FeatureRequestViewModel, voteUp: Boolean?) {
                getPresenter().voteOn(activity!!, featureRequest, voteUp);
            }
        })
        return adapter
    }

    override fun createPresenter(): FeatureRequestListPresenter {
        return FeatureRequestListPresenter(activity!!.packageName)
    }

    override fun setVote(viewModel: FeatureRequestViewModel, vote: FeatureVote) {
        viewModel._votes.add(vote)
    }

}