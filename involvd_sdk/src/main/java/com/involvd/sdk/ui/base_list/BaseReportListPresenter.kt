package com.involvd.sdk.ui.app_list

import android.content.Context
import com.involvd.R
import com.involvd.sdk.data.models.BaseReport
import com.robj.radicallyreusable.base.mvp.fragment.BaseMvpPresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

abstract class BaseReportListPresenter<T: BaseReport, V: BaseReportListView>(val appId: String) : BaseMvpPresenter<V>() {

    private var loadFromId: String? = null

    open fun refresh(context: Context) {
        loadFromId = null
        loadReports(context)
    }

    fun loadReports(context: Context) {
        view?.showProgress()
        getReports(context, loadFromId)
                .doOnNext {
                    if(!it.isEmpty())
                        loadFromId = it.get(it.size - 1).getId()
                }
                .map { it as List<Any> }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if(!it.isEmpty()) {
                        view?.addResults(it)
                    } else
                        view?.showError(getEmptyResId())
                    view?.hideProgress()
                }, {
                    it.printStackTrace()
                    view?.hideProgress()
                    view?.showError(R.string.error_unknown) //TODO
                })
    }

    abstract fun getEmptyResId(): Int

    abstract fun getReports(context: Context, loadFromId: String?) : Observable<MutableList<T>>

}
