package com.robj.involvd.ui.bugReport_list

import android.content.Context
import com.robj.involvd.data.models.BaseReport
import com.robj.radicallyreusable.base.BaseSearchAdapter
import com.robj.radicallyreusable.base.Searchable
import com.robj.radicallyreusable.base.mvp.BaseViewHolder

abstract class BaseReportAdapter<T : BaseReport>(context: Context) : BaseSearchAdapter<Searchable, BaseViewHolder>(context) {

    private var onClickListener: OnClickListener<T>? = null

    fun setOnClickListener(onClickListener: OnClickListener<T>) {
        this.onClickListener = onClickListener
    }

    protected fun getOnClickListener() : OnClickListener<T>? {
        return onClickListener
    }

    interface OnClickListener<T> {
        fun onClick(t : T)
    }

}
