package com.involvd.sdk.ui.create_bug_report

import android.content.Context
import com.involvd.sdk.data.models.BaseReport
import com.involvd.sdk.networking.retrofit.ApiClient
import com.involvd.sdk.utils.SdkUtils
import io.reactivex.Observable

open class CreateFeatureRequestPresenter(val context: Context) : BaseReportPresenter<BaseReport, CreateFeatureRequestView>() {

    override fun writeReport(baseReport: BaseReport): Observable<BaseReport> {
        val userEmail = "some@email2.com"
        baseReport.submittedByEmail = userEmail
        baseReport.upvotes = 1
        val apiKey = SdkUtils.getApiKeyForPackage(context, context.packageName)
        val sigHash = SdkUtils.getCertificateSHA1Fingerprint(context, context.packageName)
        return ApiClient.getInstance(context).createFeatureRequest(context.packageName, apiKey, sigHash, baseReport).toObservable();
    }

    override fun createReport(title: String, description: String): BaseReport {
        return BaseReport(appId, title, description)
    }

}
