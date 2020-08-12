package com.btm.swiftkt.ui.ac.login


import BaseResponse
import com.btm.swiftkt.R
import com.btm.swiftkt.base.BaseActivity
import com.btm.swiftkt.bean.LoginModel
import com.btm.swiftkt.mvp.contract.LoginViewContract
import com.orhanobut.logger.Logger

class LoginViewActivity : BaseActivity(),LoginViewContract.View {

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

    override fun loginResult(model: BaseResponse<LoginModel>) {
    }

    override fun registerResult(model: BaseResponse<LoginModel>) {
    }

    override fun changePwdResult(model: BaseResponse<LoginModel>) {
    }

    /**
     * 获取错误信息
     */
    override fun showError(errorMsg: String, errorCode: Int) {
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }
}