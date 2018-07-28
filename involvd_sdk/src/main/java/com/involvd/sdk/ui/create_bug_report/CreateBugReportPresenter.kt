package com.involvd.sdk.ui.create_bug_report

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import com.involvd.BuildConfig
import com.involvd.sdk.data.models.AppVersion
import com.involvd.sdk.data.models.BugReport
import com.involvd.sdk.data.models.Device
import com.involvd.sdk.networking.retrofit.ApiClient
import io.reactivex.Observable

open class CreateBugReportPresenter(context: Context) : BaseCreatePresenter<BugReport, CreateBugReportView>(context) {

    override fun getApiCall(apiKey: String?, sigHash: String?, report: BugReport): Observable<BugReport> {
        return ApiClient.getInstance(context).createBugReport(report)
                .toObservable()
    }

    override fun createReport(context: Context, title: String, description: String): BugReport {
        val bugReport = BugReport(appId, title, description)
        bugReport.setId("") //Populated server side
        try { bugReport.appVersionsAffected.add(AppVersion(context, context.packageName)) } catch (e: PackageManager.NameNotFoundException) { e.printStackTrace() }
        return bugReport
    }

}
