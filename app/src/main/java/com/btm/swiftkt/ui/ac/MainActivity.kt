package com.btm.swiftkt.ui.ac

import BaseActivity
import TabEntity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.btm.mylibrary.view.tab.listener.CustomTabEntity
import com.btm.mylibrary.view.tab.listener.OnTabSelectListener
import com.btm.swiftkt.R
import com.btm.swiftkt.ui.fm.tab.TabDiscoverFragment
import com.btm.swiftkt.ui.fm.tab.TabHomeFullscreenFragment
import com.btm.swiftkt.ui.fm.tab.TabMineFragment
import com.btm.swiftkt.ui.fm.tab.TabSearchFriendFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : BaseActivity(){
    private var mIndex = 0
    private val mIconUnSelectIds =
        intArrayOf(R.mipmap.untab1,R.mipmap.untab1,R.mipmap.untab1,R.mipmap.untab1)
    // 被选中的图标
    private val mTitles = arrayOf("首页","发现","推荐","我的")
    private val mIconSelectIds = intArrayOf(R.mipmap.tab1,R.mipmap.tab1,R.mipmap.tab1,R.mipmap.tab1)
    private val mTabEntities = ArrayList<CustomTabEntity>()
    private var tabhomeFullscreenFragment: TabHomeFullscreenFragment? = null
    private var tabSearchFriendFragment: TabSearchFriendFragment? = null
    private var tabDiscoverFragment: TabDiscoverFragment? = null
    private var tabMineFragment: TabMineFragment? = null
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
        tabhomeFullscreenFragment?.let { transaction.hide(it) }
        tabDiscoverFragment?.let { transaction.hide(it) }
        tabSearchFriendFragment?.let { transaction.hide(it) }
        tabMineFragment?.let { transaction.hide(it) }


    }
    private fun switchFragment(position: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        hideFragments(transaction)
        when (position) {

            0 -> tabhomeFullscreenFragment?.let {
                transaction.show(it)
            } ?: TabHomeFullscreenFragment.getInstance(mTitles[position]).let {
                tabhomeFullscreenFragment = it
                transaction.add(R.id.fl_container, it, "home")
            }
            1 -> tabDiscoverFragment?.let {
                transaction.show(it)
            } ?: TabDiscoverFragment.getInstance(mTitles[position]).let {
            tabDiscoverFragment = it
                transaction.add(R.id.fl_container, it, "discover")
            } 2 -> tabSearchFriendFragment?.let {
                transaction.show(it)
            } ?: TabSearchFriendFragment.getInstance(mTitles[position]).let {
            tabSearchFriendFragment = it
                transaction.add(R.id.fl_container, it, "search")
            }3 -> tabMineFragment?.let {
                transaction.show(it)
            } ?: TabMineFragment.getInstance(mTitles[position]).let {
            tabMineFragment = it
                transaction.add(R.id.fl_container, it, "mine")
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