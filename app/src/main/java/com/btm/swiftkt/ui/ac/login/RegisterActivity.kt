package com.btm.swiftkt.ui.ac.login

import BaseResponse
import android.view.View
import com.btm.swiftkt.R
import com.btm.swiftkt.base.BaseActivity
import com.btm.swiftkt.bean.LoginModel
import com.btm.swiftkt.mvp.contract.LoginViewContract
import com.btm.swiftkt.mvp.presenter.LoginViewPresenter
import com.btm.swiftkt.utils.StatusBarUtil
import com.btm.swiftkt.utils.checkPhone
import com.btm.swiftkt.utils.checkPwd
import com.btm.swiftkt.utils.checkYzm
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.icloud_toolbar.*

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
        toolbar_name.text = "注册"
        StatusBarUtil.darkMode(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        btn_register.setOnClickListener {

        }
        btn_yzm.setOnClickListener {

        }
    }
    private fun register(){
        val phone = ev_phone.checkPhone("手机号格式不正确")?:return
        val password = ev_pwd.checkPwd("密码格式不正确")?:return
        val yzm = ev_yzm.checkYzm("验证码位数不正确")?:return


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
        regloding.visibility = View.VISIBLE
    }

    override fun dismissLoading() {
        regloding.visibility = View.GONE
    }

    override fun onDestroy() {
        mParent.detachView()
        super.onDestroy()
    }
}