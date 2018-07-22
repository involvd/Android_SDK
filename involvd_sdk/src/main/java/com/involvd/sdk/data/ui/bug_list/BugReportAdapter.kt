package com.robj.involvd.ui.bugReport_list

import android.content.Context
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.involvd.R
import com.involvd.databinding.RowReportBinding
import com.robj.involvd.data.models.BaseReport
import com.robj.involvd.data.viewmodels.BaseReportViewModel
import com.robj.radicallyreusable.base.mvp.BaseViewHolder

class BugReportAdapter(context: Context) : BaseReportAdapter<BaseReport>(context) {

    override fun createVH(parent: ViewGroup?, viewType: Int): BaseViewHolder? {
        return when(viewType) {
            TYPE_REPORT -> createBugReportViewHolder(parent)
            else -> null
        }
    }

    private fun createBugReportViewHolder(parent: ViewGroup?): BaseViewHolder {
        val binding = DataBindingUtil.inflate<RowReportBinding>(LayoutInflater.from(context), R.layout.row_report, parent, false)
        val viewHolder = BugReportViewHolder(binding)
        return viewHolder
    }

    override fun onBindViewHolder(viewHolder: BaseViewHolder?, position: Int, viewType: Int) {
        when(viewType) {
            TYPE_REPORT -> (viewHolder as BugReportViewHolder).setViewModel(getItemAtPosition(position) as BaseReportViewModel)
        }
    }

    override fun getViewType(position: Int): Int {
        val o = getItemAtPosition(position)
        if(o is BaseReportViewModel)
            return TYPE_REPORT
        else
            return super.getViewType(position)
    }

    companion object {
        const val TYPE_REPORT = 0
    }

    class BugReportViewHolder(val binding: RowReportBinding) : BaseViewHolder(binding.root) {
        fun setViewModel(bugReport: BaseReportViewModel) {
            binding.viewModel = bugReport
        }
    }

}
