package com.involvd.sdk.ui.app

import android.content.Intent
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import android.view.View
import com.involvd.R
import com.involvd.sdk.data.PrefManager
import com.involvd.sdk.networking.retrofit.ApiClient
import com.involvd.sdk.ui.create_bug_report.CreateBugReportActivity
import com.involvd.sdk.ui.create_feature_request.CreateFeatureRequestActivity
import com.involvd.sdk.ui.main_activity.AppPagerAdapter
import com.robj.radicallyreusable.base.base_list.BaseListFragment
import com.robj.radicallyreusable.base.mvp.fragment.BaseMvpFragment
import com.robj.radicallyreusable.base.mvp.fragment.BaseMvpPresenter
import com.robj.radicallyreusable.base.mvp.fragment.BaseMvpView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.involvd_fragment_main.*

class InvolvdFragment : BaseMvpFragment<BaseMvpView, BaseMvpPresenter<BaseMvpView>>() {

    private var userIdentifier: String? = null;

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userIdentifier = arguments?.getString(USER_ID)
        initViewPager()
        initFab()
        subscribeToApiAuth()
        if(PrefManager.isValid(activity!!))
            fab_create.visibility = View.VISIBLE
    }

    private fun subscribeToApiAuth() {
        ApiClient.getAuthPublishSubect()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    fab_create.visibility = if(it) View.VISIBLE else View.GONE
                }, {
                    it.printStackTrace()
                })
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
        val i = CreateBugReportActivity.getLaunchIntent(activity, userIdentifier)
        startActivity(i)
    }

    private fun submitFeatureRequest() {
        val i = CreateFeatureRequestActivity.getLaunchIntent(activity, userIdentifier)
        startActivity(i)
    }

    private lateinit var viewPagerAdapter: AppPagerAdapter

    private fun initViewPager() {
        viewPagerAdapter = AppPagerAdapter(activity, childFragmentManager, userIdentifier)
        view_pager.adapter = viewPagerAdapter
        view_pager.addOnPageChangeListener(object : androidx.viewpager.widget.ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {}
            override fun onPageScrollStateChanged(state: Int) {}
        })
        view_pager_tabs.visibility = View.VISIBLE
        view_pager_tabs.setupWithViewPager(view_pager)
    }

    override fun getLayoutResId(): Int {
        return R.layout.involvd_fragment_main
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

    companion object {
        const val USER_ID = "userIdentifier"
    }

}