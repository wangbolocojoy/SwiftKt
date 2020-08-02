package com.btm.swiftkt.ui.fm.tab

import BaseFragment
import android.os.Bundle
import com.btm.swiftkt.R

/**
 * 首页
 */
class TabHomeFullscreenFragment() : BaseFragment() {

    lateinit var mTitle: String


    companion object {
        fun getInstance(title: String): TabHomeFullscreenFragment {
            val fragment = TabHomeFullscreenFragment()
            val bundle = Bundle()
            fragment.mTitle = title
            fragment.arguments = bundle
            return fragment
        }

    }
    /**
     * 加载布局
     */
    override fun getLayoutId() = R.layout.fragment_home_fullscreen

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

    override fun onDestroy() {
        super.onDestroy()

    }

    init {

    }

}