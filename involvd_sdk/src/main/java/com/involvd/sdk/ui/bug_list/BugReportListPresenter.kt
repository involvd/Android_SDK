package com.involvd.sdk.ui.bug_list

import android.content.Context
import com.involvd.R
import com.involvd.sdk.data.models.BaseReport
import com.involvd.sdk.networking.retrofit.ApiClient
import com.robj.involvd.ui.app_list.BaseReportListPresenter
import com.robj.radicallyreusable.base.mvp.fragment.BaseMvpPresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class BugReportListPresenter(appId: String) : BaseReportListPresenter<BaseReport, BugReportListView>(appId) {

    override fun getReports(context: Context): Observable<MutableList<BaseReport>> {
        return ApiClient.getInstance(context).getBugs(context.packageName, null, null).toObservable() //TODO: Params
    }

}