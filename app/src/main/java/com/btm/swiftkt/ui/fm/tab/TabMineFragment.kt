package com.btm.swiftkt.ui.fm.tab

import BaseFragment
import android.os.Bundle
import com.btm.swiftkt.R

/**
 * 我的
 */

class TabMineFragment : BaseFragment() {

    lateinit var mTitle: String


    companion object {
        fun getInstance(title: String): TabMineFragment {
            val fragment = TabMineFragment()
            val bundle = Bundle()
            fragment.mTitle = title
            fragment.arguments = bundle
            return fragment
        }

    }

    /**
     * 加载布局
     */
    override fun getLayoutId() = R.layout.fragment_tab__mine_
    /**
     * 初始化 ViewI
     */
    override fun initView() {
    }

    /**
     * 懒加载
     */
    override fun lazyLoad() {
    }

}