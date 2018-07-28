package com.involvd.sdk.ui.create_bug_report

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.TextInputLayout
import android.text.method.KeyListener
import android.view.Gravity
import android.view.View
import android.widget.EditText
import com.involvd.R
import com.involvd.sdk.data.models.BaseReport
import com.involvd.sdk.ui.create_bug_report.BaseReportFragment
import com.involvd.sdk.ui.create_bug_report.BaseReportPresenter
import kotlinx.android.synthetic.main.involvd_fragment_create_report.*
import kotlinx.android.synthetic.main.involvd_fragment_view_report.*

abstract class BaseEditReportFragment<T : BaseReport, V : BaseEditReportView<T>, P : BaseEditReportPresenter<T, V>> : BaseReportFragment<T, V, P>(), BaseEditReportView<T> {

    protected lateinit var titleKeyListener: KeyListener
    protected lateinit var descriptionKeyListener: KeyListener

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        getPresenter().load(activity!!, arguments)
    }

    private fun initViews(view: View) {
        titleKeyListener = title.keyListener
        descriptionKeyListener = description.keyListener
        description.gravity = Gravity.LEFT or Gravity.TOP
        description.setLines(1)
        setEditable(false)
        cancel_btn.setOnClickListener { activity?.finish() }
    }

    private fun toggleEditable(et: EditText, hint: TextInputLayout, keyListener: KeyListener, isEditable: Boolean) {
        et.isActivated = isEditable //for background drawable selector
        et.isCursorVisible = isEditable
        et.keyListener = if (isEditable) keyListener else null
    }

    protected fun setEditable(isEditable: Boolean) {
        fab_submit.visibility = if(isEditable) View.VISIBLE else View.GONE
        toggleEditable(title, title_hint, titleKeyListener, isEditable)
        toggleEditable(description, description_hint, descriptionKeyListener, isEditable)
    }



    override fun showProgress() {
        progress_container.visibility = View.VISIBLE
        progress_label.visibility = View.VISIBLE
        progress.visibility = View.VISIBLE
        setFieldVisibility(View.GONE)
    }

    override fun hideProgress() {
        progress_container.visibility = View.GONE
        setFieldVisibility(View.VISIBLE)
    }

    override fun populate(report: T) {
        title.setText(report.title)
        description.setText(report.description)
        title_hint.hint = getString(R.string.title)
        description_hint.hint = getString(R.string.description)
    }

    override fun showError(errorResId: Int) {
        progress_container.visibility = View.VISIBLE
        progress_label.visibility = View.VISIBLE
        progress_label.setText(errorResId)
        progress.visibility = View.GONE
        setFieldVisibility(View.GONE)
    }

    private fun setFieldVisibility(visibility : Int) {
        title.visibility = visibility
        description.visibility = visibility
    }

    override fun getLayoutResId(): Int {
        return R.layout.involvd_fragment_view_report
    }

    companion object {
        const val JSON = "JSON"
    }

}