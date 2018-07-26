package com.involvd.sdk.ui.create_bug_report

import android.content.Context
import com.involvd.BuildConfig
import com.involvd.sdk.data.models.AppVersion
import com.involvd.sdk.data.models.FeatureRequest
import com.involvd.sdk.networking.retrofit.ApiClient
import io.reactivex.Observable

open class CreateFeatureRequestPresenter(context: Context) : BaseCreatePresenter<FeatureRequest, CreateFeatureRequestView>(context) {

    override fun getApiCall(apiKey: String?, sigHash: String?, featureRequest: FeatureRequest): Observable<FeatureRequest> {
        return ApiClient.getInstance(context).createFeatureRequest(context.packageName, apiKey, sigHash, featureRequest).toObservable();
    }

    override fun createReport(title: String, description: String): FeatureRequest {
        val featureRequest = FeatureRequest(appId, title, description)
        featureRequest.setId("") //Populated server side
        featureRequest.appVersionsAffected.add(AppVersion(BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE))
        return featureRequest
    }

}
