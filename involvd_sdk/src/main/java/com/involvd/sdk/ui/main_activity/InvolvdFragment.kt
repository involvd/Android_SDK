package com.robj.involvd.ui.app

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.View
import com.involvd.R
import com.involvd.sdk.ui.create_bug_report.CreateBugReportActivity
import com.involvd.sdk.ui.create_feature_request.CreateFeatureRequestActivity
import com.involvd.sdk.ui.main_activity.AppPagerAdapter
import com.robj.radicallyreusable.base.base_list.BaseListFragment
import com.robj.radicallyreusable.base.mvp.fragment.BaseMvpFragment
import com.robj.radicallyreusable.base.mvp.fragment.BaseMvpPresenter
import com.robj.radicallyreusable.base.mvp.fragment.BaseMvpView
import kotlinx.android.synthetic.main.fragment_involvd.*

class InvolvdFragment : BaseMvpFragment<BaseMvpView, BaseMvpPresenter<BaseMvpView>>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
        initFab()
    }

    private fun getAppId(): String {
        return activity!!.packageName
    }

    private fun initFab() {
        fab_create.setOnClickListener {
            if(view_pager.currentItem == AppPagerAdapter.POS_BUGS)
                submitBugReport();
            else if(view_pager.currentItem == AppPagerAdapter.POS_FEATURES)
                submitFeatureRequest()
        }
    }

    private fun submitBugReport() {
        val i = Intent(activity, CreateBugReportActivity::class.java)
        startActivity(i)
    }

    private fun submitFeatureRequest() {
        val i = Intent(activity, CreateFeatureRequestActivity::class.java)
        startActivity(i)
    }

    private lateinit var viewPagerAdapter: AppPagerAdapter

    private fun initViewPager() {
        viewPagerAdapter = AppPagerAdapter(activity, childFragmentManager)
        view_pager.adapter = viewPagerAdapter
        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {}
            override fun onPageScrollStateChanged(state: Int) {}
        })
        view_pager_tabs.visibility = View.VISIBLE
        view_pager_tabs.setupWithViewPager(view_pager)
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_involvd
    }

    override fun createPresenter(): BaseMvpPresenter<BaseMvpView> {
        return object : BaseMvpPresenter<BaseMvpView>() { }
    }

    override fun isBackPressed(): Boolean {
        val o = viewPagerAdapter.instantiateItem(view_pager, view_pager.getCurrentItem())
        if (o is BaseMvpFragment<*,*> && o.isBackPressed())
            return true
        if (o is BaseListFragment<*,*,*,*> && o.isBackPressed())
            return true
        return super.isBackPressed()
    }

}