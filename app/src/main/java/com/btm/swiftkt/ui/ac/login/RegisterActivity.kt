package com.btm.swiftkt.ui.ac.login

import BaseResponse
import com.btm.swiftkt.R
import com.btm.swiftkt.base.BaseActivity
import com.btm.swiftkt.bean.LoginModel
import com.btm.swiftkt.mvp.contract.LoginViewContract
import com.btm.swiftkt.mvp.presenter.LoginViewPresenter

class RegisterActivity : BaseActivity(),LoginViewContract.View{
    private val mParent by lazy {
        LoginViewPresenter()
    }
    /**
     *  加载布局
     */
    override fun layoutId() =R.layout.activity_register

    init {
        mParent.attachView(this)
    }

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

    /**
     * 获取登录结果
     */
    override fun loginResult(model: BaseResponse<LoginModel>?) {
    }

    /**
     * 获取注册结果
     */
    override fun registerResult(model: BaseResponse<LoginModel>?) {
    }

    /**
     * 获取修改密码结果
     */
    override fun changePwdResult(model: BaseResponse<LoginModel>?) {
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

    override fun onDestroy() {
        mParent.detachView()
        super.onDestroy()
    }
}