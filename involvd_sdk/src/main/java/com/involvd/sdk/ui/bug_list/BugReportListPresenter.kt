package com.involvd.sdk.ui.bug_list

import android.content.Context
import com.involvd.R
import com.involvd.sdk.data.models.BaseReport
import com.involvd.sdk.data.models.BugReport
import com.involvd.sdk.networking.retrofit.ApiClient
import com.involvd.sdk.utils.SdkUtils
import com.involvd.sdk.ui.app_list.BaseReportListPresenter
import com.robj.radicallyreusable.base.mvp.fragment.BaseMvpPresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class BugReportListPresenter(appId: String) : BaseReportListPresenter<BugReport, BugReportListView>(appId) {

    companion object {
        const val LIMIT = 10
    }

    override fun getEmptyResId(): Int {
        return R.string.empty_bug_reports
    }

    override fun getReports(context: Context, loadFromId: String?): Observable<MutableList<BugReport>> {
        val apiKey = SdkUtils.getApiKeyForPackage(context, context.packageName)
        val sigHash = SdkUtils.getCertificateSHA1Fingerprint(context, context.packageName)
        return ApiClient.getInstance(context).getBugs(context.packageName, apiKey, sigHash, null, loadFromId, LIMIT).toObservable()
    }

}
