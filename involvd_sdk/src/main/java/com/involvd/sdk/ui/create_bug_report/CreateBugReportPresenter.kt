package com.involvd.sdk.ui.create_bug_report

import android.content.Context
import com.involvd.sdk.data.models.BaseReport
import com.involvd.sdk.networking.retrofit.ApiClient
import com.involvd.sdk.utils.SdkUtils
import io.reactivex.Observable

open class CreateBugReportPresenter(val context: Context) : BaseReportPresenter<BaseReport, CreateBugReportView>() {

    override fun writeReport(baseReport: BaseReport): Observable<BaseReport> {
        val userEmail = "some@email.com"
        baseReport.submittedByEmail = userEmail //TODO
        val apiKey = SdkUtils.getApiKeyForPackage(context, context.packageName)
        val sigHash = SdkUtils.getCertificateSHA1Fingerprint(context, context.packageName)
        return ApiClient.getInstance(context).createBugReport(context.packageName, apiKey, sigHash, baseReport).toObservable();
    }

    override fun createReport(title: String, description: String): BaseReport {
        return BaseReport(appId, title, description)
    }

}
