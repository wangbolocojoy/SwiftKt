package com.btm.swiftkt.ui.ac.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.btm.swiftkt.R
import com.btm.swiftkt.base.BaseActivity

class RegisterActivity : BaseActivity(){


    /**
     *  加载布局
     */
    override fun layoutId() =R.layout.activity_register


    /**
     * 初始化数据
     */
    override fun initData() {
    }

    /**
     * 初始化 View
     */
    override fun initView() {
        title = "注册"
    }


    /**
     * 开始请求
     */
    override fun start() {
    }
}