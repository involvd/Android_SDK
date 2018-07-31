package com.involvd.sdk.ui.create_bug_report

import android.content.Context
import android.os.Bundle
import com.fasterxml.jackson.databind.ObjectMapper
import com.involvd.R
import com.involvd.sdk.data.models.BaseReport
import com.robj.radicallyreusable.base.components.Optional
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

abstract class BaseEditReportPresenter<T : BaseReport, V: BaseEditReportView<T>> : BaseReportPresenter<T, V>() {

    override fun writeReport(report: T): Observable<T> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createReport(context: Context, title: String, description: String): T {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun load(context: Context, bundle: Bundle?) {
        view?.showProgress()
        loadReport(context, bundle)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
            view?.hideProgress()
            if(!it.isEmpty()) {
                view?.populate(it.get())
            } else
                view?.showError(R.string.error_bug_not_found) //TODO
        }, {
            it.printStackTrace()
            view?.hideProgress()
            view?.showError(R.string.error_sdk_unknown) //TODO
        })
    }

    abstract fun getReportClass() : Class<T>

    open fun loadReport(context: Context, bundle: Bundle?): Observable<Optional<T>> {
        return Observable.create<Optional<T>> {
            try {
                var optional: Optional<T>
                if (bundle?.containsKey(BaseEditReportFragment.JSON) == false) {
                    view?.showError(R.string.error_bug_not_found)
                    optional = Optional(null)
                } else {
                    val json = bundle?.getString(BaseEditReportFragment.JSON)
                    val t = ObjectMapper().readValue(json, getReportClass())
                    optional = Optional(t)
                }

                it.onNext(optional)
            } catch (e : Exception) {
                it.onError(e)
            }
            it.onComplete()
        }
    }

}
