package com.btm.swiftkt.ui.ac.login


import com.btm.swiftkt.R
import com.btm.swiftkt.base.BaseActivity

class LoginViewActivity : BaseActivity() {

    /**
     *  加载布局
     */
    override fun layoutId() = R.layout.activity_login_view

    /**
     * 初始化数据
     */
    override fun initData() {
    }

    /**
     * 初始化 View
     */
    override fun initView() {
        title = "登录"
    }

    /**
     * 开始请求
     */
    override fun start() {
    }
}