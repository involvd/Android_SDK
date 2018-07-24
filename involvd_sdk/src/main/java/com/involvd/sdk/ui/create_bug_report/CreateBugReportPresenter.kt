package com.involvd.sdk.ui.create_bug_report

import android.content.Context
import android.util.Log
import com.involvd.sdk.data.models.BaseReport
import com.involvd.sdk.networking.retrofit.ApiClient
import com.involvd.sdk.utils.SdkUtils
import io.reactivex.Observable
import java.util.*

open class CreateBugReportPresenter(val context: Context) : BaseReportPresenter<BaseReport, CreateBugReportView>() {

    override fun writeReport(baseReport: BaseReport): Observable<BaseReport> {
        val userIdentifier = "some@email.com"
        baseReport.submittedBy = "__" + SdkUtils.hashString("MD5", userIdentifier)
        val apiKey = SdkUtils.getApiKeyForPackage(context, context.packageName)
        val sigHash = SdkUtils.getCertificateSHA1Fingerprint(context, context.packageName)
        return ApiClient.getInstance(context).createBugReport(context.packageName, apiKey, sigHash, baseReport)
                .toObservable()
    }

    override fun createReport(title: String, description: String): BaseReport {
        val baseReport = BaseReport(appId, title, description)
        baseReport.setId("") //Populated server side
        return baseReport
    }

}
