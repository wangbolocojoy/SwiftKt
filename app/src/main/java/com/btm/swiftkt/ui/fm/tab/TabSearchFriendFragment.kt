package com.btm.swiftkt.ui.fm.tab

import BaseFragment
import android.os.Bundle

import com.btm.swiftkt.R
import kotlinx.android.synthetic.main.icloud_toolbar.*

/**
 * 推荐
 */

class TabSearchFriendFragment :BaseFragment() {
    lateinit var mTitle: String
    companion object {
        fun getInstance(title: String): TabSearchFriendFragment {
            val fragment = TabSearchFriendFragment()
            val bundle = Bundle()
            fragment.mTitle = title
            fragment.arguments = bundle
            return fragment
        }

    }

    /**
     * 加载布局
     */
    override fun getLayoutId() = R.layout.fragment_search_friend

    /**
     * 初始化 ViewI
     */
    override fun initView() {
        toolbar_name.text = "推荐"
    }

    /**
     * 懒加载
     */
    override fun lazyLoad() {
    }
}