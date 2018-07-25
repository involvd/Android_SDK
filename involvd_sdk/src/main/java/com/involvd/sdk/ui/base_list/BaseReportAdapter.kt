package com.involvd.sdk.ui.base_list;

import android.content.Context
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.involvd.R
import com.involvd.databinding.InvolvdRowReportBinding
import com.involvd.sdk.data.models.BaseReport
import com.robj.radicallyreusable.base.BaseSearchAdapter
import com.robj.radicallyreusable.base.Searchable
import com.robj.radicallyreusable.base.mvp.BaseViewHolder

abstract class BaseReportAdapter<T : BaseReport>(context: Context) : BaseSearchAdapter<Searchable, BaseViewHolder>(context) {

    private var onClickListener: OnClickListener<T>? = null

    override fun createVH(parent: ViewGroup?, viewType: Int): BaseViewHolder? {
        return when(viewType) {
            TYPE_REPORT -> createBugReportViewHolder(parent)
            else -> null
        }
    }

    private fun createBugReportViewHolder(parent: ViewGroup?): BaseViewHolder {
        val binding = DataBindingUtil.inflate<InvolvdRowReportBinding>(LayoutInflater.from(context), R.layout.involvd_row_report, parent, false)
        val viewHolder = BaseReportViewHolder(binding)
        viewHolder.itemView.setOnClickListener {
            getOnClickListener()?.let {
                val bugReport = getItemAtPosition(viewHolder.adapterPosition) as T
                it.onClick(bugReport)
            }
        }
        val voteOnClickListener = View.OnClickListener {
            val voteUp: Boolean = it.id == viewHolder.binding.voteUpBtn.id
            getOnClickListener()?.let {
                val bugReport = getItemAtPosition(viewHolder.adapterPosition) as T
                it.onVoteClick(bugReport, voteUp)
            }
        }
        viewHolder.binding.voteUpBtn.setOnClickListener(voteOnClickListener)
        viewHolder.binding.voteDownBtn.setOnClickListener(voteOnClickListener)
        return viewHolder
    }

    override fun onBindViewHolder(viewHolder: BaseViewHolder?, position: Int, viewType: Int) {
        when(viewType) {
            TYPE_REPORT -> {
                val viewModel = getItemAtPosition(position) as T

                (viewHolder as BaseReportViewHolder).setViewModel(object : BaseReportViewModel {
                    override fun getIsFollowing(): Boolean {
                        return this@BaseReportAdapter.getIsFollowing(viewModel)
                    }

                    override fun getIsVotedOn(): Boolean {
                        return this@BaseReportAdapter.getIsVotedOn(viewModel)
                    }

                    override fun getFollowerCount(): Int {
                        return viewModel.followerList.size
                    }

                    override fun getVoteCount(): Int {
                        return viewModel.upvotes - viewModel.downvotes
                    }

                    override fun getUpVotes(): Int {
                        return viewModel.upvotes
                    }

                    override fun getDownVotes(): Int {
                        return viewModel.downvotes
                    }

                    override fun getDescription(): String? {
                        return viewModel.description
                    }

                    override fun getStatusLabelResId(): Int {
                        return this@BaseReportAdapter.getStatusLabelResId(viewModel)
                    }

                    override fun getName(): String {
                        return viewModel.name
                    }
                })
            }
        }
    }

    abstract fun getStatusLabelResId(viewModel: T): Int

    override fun getViewType(position: Int): Int {
        val o = getItemAtPosition(position)
        if(o is BaseReport)
            return TYPE_REPORT
        else
            return super.getViewType(position)
    }

    companion object {
        const val TYPE_REPORT = 0
    }

    open fun getIsFollowing(viewModel: T) : Boolean {
        return false
    }

    open fun getIsVotedOn(viewModel: T): Boolean {
        return false
    }

    class BaseReportViewHolder(val binding: InvolvdRowReportBinding) : BaseViewHolder(binding.root) {
        fun setViewModel(viewModel: BaseReportViewModel) {
            binding.viewModel = viewModel
        }
    }

    interface BaseReportViewModel : Searchable {

        fun getIsFollowing() : Boolean

        fun getIsVotedOn() : Boolean

        fun getFollowerCount() : Int;

        fun getVoteCount() : Int;

        fun getUpVotes(): Int

        fun getDownVotes(): Int

        fun getDescription() : String?

        fun getStatusLabelResId(): Int

    }


    fun setOnClickListener(onClickListener: OnClickListener<T>) {
        this.onClickListener = onClickListener
    }

    protected fun getOnClickListener() : OnClickListener<T>? {
        return onClickListener
    }

    interface OnClickListener<T : BaseReport> {
        fun onClick(t : T)
        fun onVoteClick(t : T, voteUp: Boolean?)
    }

}
