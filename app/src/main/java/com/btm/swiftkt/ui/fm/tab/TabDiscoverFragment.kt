package com.btm.swiftkt.ui.fm.tab

import BaseFragment
import android.os.Bundle
import com.btm.swiftkt.R

/**

 * @Auther: hero

 * @datetime: 2020/8/2 23:30

 * @desc: 发现页面

 * @项目:  SwiftKt

 */
class TabDiscoverFragment():BaseFragment() {

    lateinit var mTitle: String


    companion object {
        fun getInstance(title: String): TabDiscoverFragment {
            val fragment = TabDiscoverFragment()
            val bundle = Bundle()
            fragment.mTitle = title
            fragment.arguments = bundle
            return fragment
        }

    }
    /**
     * 加载布局
     */
    override fun getLayoutId() = R.layout.fragment_discover

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