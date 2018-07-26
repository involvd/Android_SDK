package com.involvd.sdk.ui.create_bug_report

import android.content.Context
import com.involvd.BuildConfig
import com.involvd.sdk.data.models.AppVersion
import com.involvd.sdk.data.models.BugReport
import com.involvd.sdk.networking.retrofit.ApiClient
import io.reactivex.Observable

open class CreateBugReportPresenter(context: Context) : BaseCreatePresenter<BugReport, CreateBugReportView>(context) {

    override fun getApiCall(apiKey: String?, sigHash: String?, report: BugReport): Observable<BugReport> {
        return ApiClient.getInstance(context).createBugReport(context.packageName, apiKey, sigHash, report)
                .toObservable()
    }

    override fun createReport(title: String, description: String): BugReport {
        val bugReport = BugReport(appId, title, description)
        bugReport.setId("") //Populated server side
        bugReport.appVersionsAffected.add(AppVersion(BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE))
        return bugReport
    }

}
