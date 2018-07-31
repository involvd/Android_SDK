package com.involvd.sdk.ui.bug_list

import android.content.Context
import com.involvd.R
import com.involvd.sdk.data.DatabaseManager
import com.involvd.sdk.data.PrefManager
import com.involvd.sdk.data.models.BugVote
import com.involvd.sdk.data.viewmodels.BugReportViewModel
import com.involvd.sdk.networking.retrofit.ApiClient
import com.involvd.sdk.ui.app_list.BaseReportListPresenter
import com.involvd.sdk.utils.SdkUtils
import io.reactivex.Flowable
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class BugReportListPresenter(appId: String) : BaseReportListPresenter<BugReportViewModel, BugVote, BugReportListView>(appId) {

    var userIdentifier: String? = null

    override fun submitVote(context: Context, vote: BugVote): Observable<Boolean> {
        return ApiClient.getInstance(context).voteOnBug(vote)
                .compose(ApiClient.applyFlowableRules())
                .toObservable()
                .flatMap { DatabaseManager.addOrUpdateBugReportVote(context, vote) }
                .flatMap {
                    if(vote.votedUp != null) {
                        DatabaseManager.getBugReport(context, vote.reportId)
                                .flatMap {
                                    if (!it.isEmpty) {
                                        val report = it.get()
                                        if (vote.votedUp!!)
                                            report.upvotes++
                                        else
                                            report.downvotes--
                                        DatabaseManager.addOrUpdateBugReport(context, report)
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
        return R.string.empty_bug_reports
    }

    override fun createVote(context: Context, t: BugReportViewModel, voteUp: Boolean?): BugVote {
        return BugVote(appId, t.getId(), userIdentifier?:SdkUtils.createUniqueIdentifier(context), voteUp)
    }

    override fun getReports(context: Context, loadFromId: String?, refresh: Boolean): Observable<MutableList<BugReportViewModel>> {
        val lastFetchTimedOut = (System.currentTimeMillis() - PrefManager.getBugListTime(context)) >= TimeUnit.MINUTES.toMillis(15)
        var f: Flowable<MutableList<BugReportViewModel>>
        if(!refresh && !lastFetchTimedOut)
            f = DatabaseManager.getBugReports(context)
        else
            f = ApiClient.getInstance(context).getBugs(null, loadFromId, getLimit())
        return f.toObservable()
                .flatMap { reports -> DatabaseManager.addOrUpdateBugReports(context, reports).map { reports } }

    }

}
