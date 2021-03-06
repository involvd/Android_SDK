package com.involvd.sdk.ui.base_report

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import com.involvd.R
import com.involvd.sdk.data.PrefManager
import com.involvd.sdk.data.models.BaseReport
import com.involvd.sdk.ui.create_bug_report.BaseCreatePresenter
import com.involvd.sdk.ui.create_bug_report.BaseReportFragment
import com.involvd.sdk.ui.create_bug_report.BaseReportView
import kotlinx.android.synthetic.main.involvd_dialog_edittext.view.*
import kotlinx.android.synthetic.main.involvd_fragment_create_report.*

abstract open class BaseCreateReportFragment<T : BaseReport, V : BaseReportView, P : BaseCreatePresenter<T, V>> :  BaseReportFragment<T, V, P>() {

    private var wasDialogShown = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(arguments?.containsKey(USER_ID) == true)
            getPresenter().userIdentifier = arguments!!.getString(USER_ID)
    }

    override fun onSubmittedSuccess(reportId: String) {
        AlertDialog.Builder(activity!!)
                .setTitle(getSuccessResId())
                .setMessage(R.string.dialog_create_success)
                .setPositiveButton(android.R.string.ok) { _, _ -> finishWithSuccess(reportId)}
                .setCancelable(false)
                .show()
    }

    /**
     * @userIdentifier null show dialog
     * @userIdentifier "" dialog shown & rejected
     */
    override fun canSubmit(): Boolean {
        if(!wasDialogShown && TextUtils.isEmpty(getPresenter().userIdentifier)) {
            val v = layoutInflater.inflate(R.layout.involvd_dialog_edittext, null)
            val editText = v.edittext
            val listener = DialogInterface.OnClickListener { dialog, which ->
                wasDialogShown = true
                getPresenter().setAndCacheUserIdentifier(editText.text.toString().trim())
                fab_submit.performClick()
            }
            val dialog = AlertDialog.Builder(activity!!)
                    .setTitle(R.string.dialog_req_email)
                    .setMessage(R.string.dialog_req_email_bug_msg)
                    .setView(v)
                    .setNeutralButton(R.string.cancel, null)
                    .setNegativeButton(R.string.skip, listener)
                    .setPositiveButton(R.string.done, listener)
                    .show()
            val positiveBtn = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveBtn.isEnabled = false
            editText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) { }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    positiveBtn.isEnabled = !TextUtils.isEmpty(s?.trim()) && Patterns.EMAIL_ADDRESS.matcher(s!!.trim()).matches()
                }
            })
            val uniqueIdentifier = PrefManager.getUniqueId(activity!!)
            if(!TextUtils.isEmpty(uniqueIdentifier) && Patterns.EMAIL_ADDRESS.matcher(uniqueIdentifier).matches())
                editText.setText(uniqueIdentifier)
            return false
        }
        return true
    }

    companion object {
        const val USER_ID = "userIdentifier"
    }

}