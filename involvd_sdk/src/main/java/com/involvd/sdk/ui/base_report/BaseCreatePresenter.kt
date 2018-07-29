package com.involvd.sdk.ui.create_bug_report

import android.content.Context
import android.text.TextUtils
import com.involvd.sdk.data.PrefManager
import com.involvd.sdk.data.models.BaseReport
import com.involvd.sdk.utils.SdkUtils
import io.reactivex.Observable

abstract class BaseCreatePresenter<T : BaseReport, V : BaseReportView>(val context: Context) : BaseReportPresenter<T, V>() {

    var userIdentifier: String? = null

    override fun writeReport(report: T): Observable<T> {
        val apiKey = SdkUtils.getApiKeyForPackage(context, context.packageName)
        val sigHash = SdkUtils.getCertificateSHA1Fingerprint(context, context.packageName)
        if(!TextUtils.isEmpty(userIdentifier))
            report.submittedBy = "__" + SdkUtils.hashString("MD5", userIdentifier!!)
        return getApiCall(apiKey, sigHash, report)
    }

    abstract fun getApiCall(apiKey: String?, sigHash: String?, report: T): Observable<T>

    fun setAndCacheUserIdentifier(userIdentifier: String?) {
        this.userIdentifier = userIdentifier
        PrefManager.setUniqueId(context, userIdentifier)
    }

}
