package com.involvd.sdk.ui.bug_list

import android.content.Context
import com.involvd.R
import com.involvd.sdk.data.models.BaseReport
import com.involvd.sdk.data.models.FeatureRequest
import com.involvd.sdk.networking.retrofit.ApiClient
import com.involvd.sdk.utils.SdkUtils
import com.robj.involvd.ui.app_list.BaseReportListPresenter
import com.robj.radicallyreusable.base.mvp.fragment.BaseMvpPresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FeatureRequestListPresenter(appId: String) : BaseReportListPresenter<FeatureRequest, FeatureRequestListView>(appId) {

    override fun getEmptyResId(): Int {
        return R.string.empty_feature_requests
    }

    override fun getReports(context: Context): Observable<MutableList<FeatureRequest>> {
        val apiKey = SdkUtils.getApiKeyForPackage(context, context.packageName)
        val sigHash = SdkUtils.getCertificateSHA1Fingerprint(context, context.packageName)
        return ApiClient.getInstance(context).getFeatureRequests(context.packageName, apiKey, sigHash, null, null).toObservable() //TODO: Params
    }

}
