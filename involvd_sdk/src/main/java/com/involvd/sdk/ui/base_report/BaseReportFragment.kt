package com.involvd.sdk.ui.create_bug_report

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import com.involvd.R
import com.involvd.sdk.data.models.BaseReport
import com.robj.radicallyreusable.base.mvp.fragment.BaseMvpFragment
import kotlinx.android.synthetic.main.involvd_fragment_create_report.*

abstract class BaseReportFragment<T : BaseReport, V : BaseReportView, P : BaseReportPresenter<T, V>> : BaseMvpFragment<V, P>(), BaseReportView {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val appId = getAppId()
        val packageName = getPackageName()
        if(appId != null && packageName != null) {
            initListeners();
            getPresenter().appId = appId
            getPresenter().packageName = packageName
            fab_submit.isEnabled = false
        }
        //TODO: Else??
    }

    open fun getAppId(): String? {
        return activity!!.packageName
    }

    open fun getPackageName(): String? {
        return activity!!.packageName
    }

    override fun onSubmittedSuccess(reportId: String) {
        showToast(getSuccessResId())
        finishWithSuccess(reportId)
    }

    protected fun finishWithSuccess(reportId: String) {
        val i = Intent()
        i.putExtra(BaseReport.FIELD_ID, reportId)
        activity?.setResult(Activity.RESULT_OK, i)
        activity?.finish()
    }

    abstract fun getSuccessResId() : Int

    private fun initListeners() {
        title.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) { }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkIsReadyToSubmit()
            }
        })
        description.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) { }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkIsReadyToSubmit()
            }
        })
        fab_submit.setOnClickListener {
            if(canSubmit())
                getPresenter().submitBugReport(activity!!, title.text.toString().trim(),description.text.toString().trim())
        }
    }

    protected open fun canSubmit(): Boolean {
        return true
    }

    private fun checkIsReadyToSubmit() {
        fab_submit.isEnabled = !TextUtils.isEmpty(title.text.toString().trim()) && !TextUtils.isEmpty(description.text.toString().trim())
    }

    override fun getLayoutResId(): Int {
        return R.layout.involvd_fragment_create_report
    }

}