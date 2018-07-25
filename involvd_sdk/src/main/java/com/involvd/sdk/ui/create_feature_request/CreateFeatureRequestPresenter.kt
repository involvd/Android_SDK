package com.involvd.sdk.ui.create_bug_report

import android.content.Context
import com.involvd.sdk.data.models.FeatureRequest
import com.involvd.sdk.networking.retrofit.ApiClient
import com.involvd.sdk.utils.SdkUtils
import io.reactivex.Observable

open class CreateFeatureRequestPresenter(val context: Context) : BaseReportPresenter<FeatureRequest, CreateFeatureRequestView>() {

    override fun writeReport(featureRequest: FeatureRequest): Observable<FeatureRequest> {
        val userIdentifier = "some@email2.com"
        featureRequest.submittedBy = "__" + SdkUtils.hashString("MD5", userIdentifier)
        featureRequest.upvotes = 1
        val apiKey = SdkUtils.getApiKeyForPackage(context, context.packageName)
        val sigHash = SdkUtils.getCertificateSHA1Fingerprint(context, context.packageName)
        return ApiClient.getInstance(context).createFeatureRequest(context.packageName, apiKey, sigHash, featureRequest).toObservable();
    }

    override fun createReport(title: String, description: String): FeatureRequest {
        val featureRequest = FeatureRequest(appId, title, description)
        featureRequest.setId("") //Populated server side
        return featureRequest
    }

}
