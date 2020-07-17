package com.btm.swiftkt.ui.ac

import BaseActivity
import TabEntity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.btm.mylibrary.view.tab.listener.CustomTabEntity
import com.btm.mylibrary.view.tab.listener.OnTabSelectListener
import com.btm.swiftkt.R
import com.btm.swiftkt.ui.fm.HomeFullscreenFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : BaseActivity(){
    private var mIndex = 0
    private val mIconUnSelectIds =
        intArrayOf(R.mipmap.untab1)
    // 被选中的图标
    private val mTitles = arrayOf("足迹")
    private val mIconSelectIds = intArrayOf(R.mipmap.tab1)
    private val mTabEntities = ArrayList<CustomTabEntity>()
    private var homeFullscreenFragment:HomeFullscreenFragment? = null
    /**
     *  加载布局
     */
    override fun layoutId()= R.layout.activity_main

    /**
     * 初始化数据
     */
    override fun initData() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            mIndex = savedInstanceState.getInt("currTabIndex")
        }
        super.onCreate(savedInstanceState)
        initTab()
        tab_layout.currentTab = mIndex
        switchFragment(mIndex)
    }
    private fun initTab() {
        (mTitles.indices)
            .mapTo(mTabEntities) {
                TabEntity(
                    mTitles[it],
                    mIconSelectIds[it],
                    mIconUnSelectIds[it]
                )
            }
        //为Tab赋值
        tab_layout.setTabData(mTabEntities)
        tab_layout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                //切换Fragment
                switchFragment(position)
            }

            override fun onTabReselect(position: Int) {
            }
        })
    }
    private fun hideFragments(transaction: FragmentTransaction) {
//        mConvFragment?.let { transaction.hide(it) }
        homeFullscreenFragment?.let { transaction.hide(it) }


    }
    private fun switchFragment(position: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        hideFragments(transaction)
        when (position) {
//            0 -> mConvFragment?.let {
//                transaction.show(it)
//            } ?: TabConversationFragment.getInstance(mTitles[position]).let {
//                mConvFragment = it
//                transaction.add(R.id.fl_container, it, "session")
//            }
            0 -> homeFullscreenFragment?.let {
                transaction.show(it)
            } ?: HomeFullscreenFragment.getInstance(mTitles[position]).let {
                homeFullscreenFragment = it
                transaction.add(R.id.fl_container, it, "foot")
            }

            else -> {

            }
        }
        mIndex = position
        tab_layout.currentTab = mIndex
        transaction.commitAllowingStateLoss()
    }
    /**
     * 初始化 View
     */
    override fun initView() {
    }

    /**
     * 开始请求
     */
    override fun start() {
    }
}