package com.involvd.sdk.ui.create_bug_report

import android.content.Context
import com.involvd.R
import com.involvd.sdk.data.models.BaseReport
import com.robj.radicallyreusable.base.mvp.fragment.BaseMvpPresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

abstract class BaseReportPresenter<T : BaseReport, V : BaseReportView> : BaseMvpPresenter<V>() {

    lateinit var appId: String
    lateinit var packageName: String

    fun submitBugReport(context: Context, title: String, description: String) {
        view?.showProgressDialog(R.string.progress_submitting_bug_report)
        val report = createReport(context, title, description)
        writeReport(report)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view?.hideProgressDialog()
                    onReportSubmitted(it)
                }, {
                    it.printStackTrace()
                    view?.hideProgressDialog()
                    view?.showToast(R.string.error_unknown)
                })

    }

    open fun onReportSubmitted(t: T) {
        view?.onSubmittedSuccess(t.getId())
    }

    abstract fun writeReport(report: T) : Observable<T>
    abstract fun createReport(context: Context, title: String, description: String): T

}
