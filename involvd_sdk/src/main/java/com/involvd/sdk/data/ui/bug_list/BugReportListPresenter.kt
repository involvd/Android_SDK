package com.robj.involvd.ui.app_list

import android.content.Context
import com.involvd.R
import com.involvd.sdk.data.networking.retrofit.ApiClient
import com.robj.radicallyreusable.base.mvp.fragment.BaseMvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class BugReportListPresenter : BaseMvpPresenter<BugReportListView>() {

    fun loadAcceptedBugReports(context: Context) {
        view?.showProgress()
        ApiClient.getInstance(context).getBugs(context.packageName, null, null) //TODO: Params
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

}
