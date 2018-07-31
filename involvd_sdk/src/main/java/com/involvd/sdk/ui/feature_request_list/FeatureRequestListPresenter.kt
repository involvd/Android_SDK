package com.involvd.sdk.ui.bug_list

import android.content.Context
import com.involvd.R
import com.involvd.sdk.data.DatabaseManager
import com.involvd.sdk.data.PrefManager
import com.involvd.sdk.data.models.FeatureVote
import com.involvd.sdk.data.viewmodels.FeatureRequestViewModel
import com.involvd.sdk.networking.retrofit.ApiClient
import com.involvd.sdk.ui.app_list.BaseReportListPresenter
import com.involvd.sdk.utils.SdkUtils
import io.reactivex.Flowable
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class FeatureRequestListPresenter(appId: String) : BaseReportListPresenter<FeatureRequestViewModel, FeatureVote, FeatureRequestListView>(appId) {

    var userIdentifier: String? = null

    override fun submitVote(context: Context, vote: FeatureVote): Observable<Boolean> {
        return ApiClient.getInstance(context).voteOnFeatureRequest(vote)
                .compose(ApiClient.applyFlowableRules())
                .toObservable()
                .flatMap { DatabaseManager.addOrUpdateFeatureVote(context, vote) }
                .flatMap {
                    if(vote.votedUp != null) {
                        DatabaseManager.getFeatureRequest(context, vote.reportId)
                                .flatMap {
                                    if (!it.isEmpty) {
                                        val report = it.get()
                                        if (vote.votedUp!!)
                                            report.upvotes++
                                        else
                                            report.downvotes--
                                        DatabaseManager.addOrUpdateFeatureRequest(context, report)
                                    } else
                                    Observable.just(it)
                                }
                    } else
                    Observable.just(it)
                }
                .map { true }
    }

    override fun getLimit(): Int {
        return 10
    }

    override fun getEmptyResId(): Int {
        return R.string.empty_feature_requests
    }

    override fun createVote(context: Context, t: FeatureRequestViewModel, voteUp: Boolean?): FeatureVote {
        return FeatureVote(appId, t.getId(), userIdentifier?:SdkUtils.createUniqueIdentifier(context), voteUp)
    }

    override fun getReports(context: Context, loadFromId: String?, refresh: Boolean): Observable<MutableList<FeatureRequestViewModel>> {
        val lastFetchTimedOut = (System.currentTimeMillis() - PrefManager.getFeatureListTime(context)) >= TimeUnit.MINUTES.toMillis(15)
        var f: Flowable<MutableList<FeatureRequestViewModel>>
        if(!refresh && !lastFetchTimedOut)
            f = DatabaseManager.getFeatureRequests(context)
        else
            f = ApiClient.getInstance(context).getFeatureRequests(null, loadFromId, getLimit())
                    .doOnNext{ PrefManager.setFeatureListTime(context)}
        return f.toObservable()
                .flatMap { reports -> DatabaseManager.addOrUpdateFeatureRequests(context, reports).map { reports } }
    }

}
