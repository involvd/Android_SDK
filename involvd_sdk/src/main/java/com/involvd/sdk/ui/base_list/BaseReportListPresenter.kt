package com.robj.involvd.ui.app_list

import android.content.Context
import com.involvd.R
import com.robj.radicallyreusable.base.mvp.fragment.BaseMvpPresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

abstract class BaseReportListPresenter<T, V: BaseReportListView> : BaseMvpPresenter<V>() {

    lateinit var appId: String

    fun loadReports(context: Context, appId: String) {
        view?.showProgress()
        getReports(context)
                //TODO: Get bugs we're following
                .map { it as List<Any> }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view?.hideProgress()
                    if(!it.isEmpty())
                        view?.addResults(it)
                    else
                        view?.showError(R.string.empty_bug_reports)
                }, {
                    it.printStackTrace()
                    view?.hideProgress()
                    view?.showError(R.string.error_unknown) //TODO
                })
    }

    abstract fun getReports(context: Context) : Observable<MutableList<T>>

}
