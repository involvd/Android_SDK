package com.involvd.sdk.ui.app_list

import android.content.Context
import com.involvd.R
import com.involvd.sdk.data.models.BaseReport
import com.involvd.sdk.data.models.BaseVote
import com.involvd.sdk.networking.retrofit.ApiClient
import com.robj.radicallyreusable.base.mvp.fragment.BaseMvpPresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

abstract class BaseReportListPresenter<T: BaseReport, VT : BaseVote, V: BaseReportListView<T, VT>>(val appId: String) : BaseMvpPresenter<V>() {

    private var loadFromId: String? = null

    open fun refresh(context: Context) {
        loadFromId = null
        loadReports(context, true)
    }

    fun loadReports(context: Context) {
        loadReports(context, false)
    }

    fun loadReports(context: Context, refresh: Boolean) {
        view?.showProgress()
        getReports(context, loadFromId, refresh)
                .doOnNext {
                    if(!it.isEmpty() && it.size == getLimit())
                        loadFromId = it.get(it.size - 1).getId()
                    else
                        loadFromId = null
                }
                .map { it as List<Any> }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if(!it.isEmpty()) {
                        view?.addResults(it)
                        view?.hideProgress()
                    } else
                        view?.showError(getEmptyResId())
                    view?.hasMoreToLoad(loadFromId != null)
                }, {
                    it.printStackTrace()
                    view?.hideProgress()
                    view?.showError(getErrorMsg(it)) //TODO
                })
    }

    open fun getErrorMsg(it: Throwable): Int {
        return if(it is ApiClient.ApiException)
            it.errorResId
        else
            R.string.error_sdk_unknown
    }

    fun voteOn(context: Context, t: T, voteUp: Boolean?) {
        val vote = createVote(context, t, voteUp)
        submitVote(context, vote)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view?.adjustVoteCount(t, vote)
                    view?.hideProgress()
                    view?.showToast(R.string.thanks_for_vote)
                }, {
                    it.printStackTrace()
                    view?.hideProgress()
                    view?.clearInProgress(t)
                    view?.showToast(R.string.error_sdk_unknown) //TODO
                })
    }

    abstract fun createVote(context: Context, t: T, voteUp: Boolean?) : VT

    abstract fun submitVote(context: Context, t: VT): Observable<Boolean>

    abstract fun getLimit(): Int

    abstract fun getEmptyResId(): Int

    abstract fun getReports(context: Context, loadFromId: String?, refresh: Boolean) : Observable<MutableList<T>>

}
