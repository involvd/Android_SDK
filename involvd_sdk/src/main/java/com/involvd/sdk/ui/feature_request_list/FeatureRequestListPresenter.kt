package com.involvd.sdk.ui.bug_list

import android.content.Context
import com.involvd.R
import com.involvd.sdk.data.DatabaseManager
import com.involvd.sdk.data.PrefManager
import com.involvd.sdk.data.models.FeatureRequest
import com.involvd.sdk.data.models.FeatureVote
import com.involvd.sdk.networking.retrofit.ApiClient
import com.involvd.sdk.ui.app_list.BaseReportListPresenter
import com.involvd.sdk.utils.SdkUtils
import io.reactivex.Flowable
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class FeatureRequestListPresenter(appId: String) : BaseReportListPresenter<FeatureRequest, FeatureVote, FeatureRequestListView>(appId) {

    var userIdentifier: String? = null

    override fun submitVote(context: Context, vote: FeatureVote): Observable<Boolean> {
        return ApiClient.getInstance(context).voteOnFeatureRequest(vote).map { true }.toObservable()
    }

    override fun getLimit(): Int {
        return 10
    }

    override fun getEmptyResId(): Int {
        return R.string.empty_feature_requests
    }

    override fun createVote(context: Context, t: FeatureRequest, voteUp: Boolean?): FeatureVote {
        return FeatureVote(appId, t.getId(), userIdentifier?:SdkUtils.createUniqueIdentifier(context), voteUp)
    }

    override fun getReports(context: Context, loadFromId: String?, refresh: Boolean): Observable<MutableList<FeatureRequest>> {
        val lastFetchTimedOut = (System.currentTimeMillis() - PrefManager.getFeatureListTime(context)) < TimeUnit.MINUTES.toMillis(15)
        var f: Flowable<MutableList<FeatureRequest>>
        if(!refresh && !lastFetchTimedOut)
            f = DatabaseManager.getFeatureRequests(context)
        else
            f = ApiClient.getInstance(context).getFeatureRequests(null, loadFromId, getLimit())
                    .doOnNext{ PrefManager.setFeatureListTime(context)}
        return f.toObservable()
                .flatMap { reports -> DatabaseManager.addOrUpdateFeatureRequests(context, reports).map { reports } }
    }

}
