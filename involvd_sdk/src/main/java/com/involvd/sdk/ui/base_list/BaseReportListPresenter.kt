package com.robj.involvd.ui.app_list

import android.content.Context
import com.involvd.R
import com.involvd.sdk.data.models.BaseReport
import com.robj.radicallyreusable.base.mvp.fragment.BaseMvpPresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

abstract class BaseReportListPresenter<T: BaseReport, V: BaseReportListView>(val appId: String) : BaseMvpPresenter<V>() {

    fun loadReports(context: Context) {
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
                        view?.showError(getEmptyResId())
                }, {
                    it.printStackTrace()
                    view?.hideProgress()
                    view?.showError(R.string.error_unknown) //TODO
                })
    }

    abstract fun getEmptyResId(): Int

    abstract fun getReports(context: Context) : Observable<MutableList<T>>

}
