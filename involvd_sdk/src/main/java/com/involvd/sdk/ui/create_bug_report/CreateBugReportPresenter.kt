package com.involvd.sdk.ui.create_bug_report

import android.content.Context
import com.involvd.sdk.data.models.BugReport
import com.involvd.sdk.networking.retrofit.ApiClient
import com.involvd.sdk.utils.SdkUtils
import io.reactivex.Observable

open class CreateBugReportPresenter(val context: Context) : BaseReportPresenter<BugReport, CreateBugReportView>() {

    override fun writeReport(bugReport: BugReport): Observable<BugReport> {
        val userIdentifier = "some@email.com"
        bugReport.submittedBy = "__" + SdkUtils.hashString("MD5", userIdentifier)
        val apiKey = SdkUtils.getApiKeyForPackage(context, context.packageName)
        val sigHash = SdkUtils.getCertificateSHA1Fingerprint(context, context.packageName)
        return ApiClient.getInstance(context).createBugReport(context.packageName, apiKey, sigHash, bugReport)
                .toObservable()
    }

    override fun createReport(title: String, description: String): BugReport {
        val bugReport = BugReport(appId, title, description)
        bugReport.setId("") //Populated server side
        return bugReport
    }

}
