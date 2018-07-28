package com.involvd.sdk.ui.bug_list

import android.content.Context
import com.involvd.R
import com.involvd.sdk.data.models.FeatureRequest
import com.involvd.sdk.data.models.FeatureVote
import com.involvd.sdk.networking.retrofit.ApiClient
import com.involvd.sdk.ui.app_list.BaseReportListPresenter
import com.involvd.sdk.utils.SdkUtils
import io.reactivex.Observable

class FeatureRequestListPresenter(appId: String) : BaseReportListPresenter<FeatureRequest, FeatureVote, FeatureRequestListView>(appId) {

    override fun submitVote(context: Context, vote: FeatureVote): Observable<Boolean> {
        return ApiClient.getInstance(context).voteOnFeatureRequest(vote).map { true }.toObservable()
    }

    override fun getLimit(): Int {
        return 10
    }

    override fun getEmptyResId(): Int {
        return R.string.empty_feature_requests
    }

    override fun createVote(t: FeatureRequest, voteUp: Boolean?): FeatureVote {
        return FeatureVote(appId, t.getId(), "", voteUp)
    }

    override fun getReports(context: Context, loadFromId: String?): Observable<MutableList<FeatureRequest>> {
        return ApiClient.getInstance(context).getFeatureRequests(null, loadFromId, getLimit()).toObservable()
    }

}
