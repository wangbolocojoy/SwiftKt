package com.btm.swiftkt.ui.ac.login


import BaseResponse
import android.content.Intent
import android.view.View
import com.btm.swiftkt.R
import com.btm.swiftkt.base.BaseActivity
import com.btm.swiftkt.bean.LoginModel
import com.btm.swiftkt.http.MyRequestBody
import com.btm.swiftkt.mvp.contract.LoginViewContract
import com.btm.swiftkt.mvp.presenter.LoginViewPresenter
import com.btm.swiftkt.ui.ac.MainActivity
import com.btm.swiftkt.utils.*
import kotlinx.android.synthetic.main.activity_login_view.*
import kotlinx.android.synthetic.main.icloud_toolbar.*
import okhttp3.MediaType
import okhttp3.RequestBody

class LoginViewActivity : BaseActivity(), LoginViewContract.View {
    private val mParent by lazy {
        LoginViewPresenter()
    }
    private var user by Preference(Constants.USER, "")
    private var token by Preference(Constants.TOKEN, "")

    /**
     *  加载布局
     */
    override fun layoutId() = R.layout.activity_login_view

    /**
     * 初始化数据
     */
    override fun initData() {
    }

    init {
        mParent.attachView(this)
    }

    /**
     * 初始化 View
     */
    override fun initView() {
        toolbar_name.text = "登录"

        StatusBarUtil.darkMode(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
        toolbar.navigationIcon = null
        btn_login.setOnClickListener {
            login()
        }
        btn_fgpwd.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            intent.putExtra("title","修改密码")
            startActivity(intent)
        }
        btn_toregsister.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            intent.putExtra("title","注册")
            startActivity(intent)
        }
        btn_ys.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            intent.putExtra("title","注册")
            startActivity(intent)
        }
    }

    private fun login() {
        val phone = editTextPhone2.checkPhone("手机号格式不正确") ?: return
        val password = editTextTextPassword2.checkPwd("密码格式不正确") ?: return
        val json = MyRequestBody()
        json.phone = phone
        json.password = password.getMD5Str()
        val body: RequestBody =
            RequestBody.create(MediaType.parse("application/json"), GsonUtil.gsonString(json))
        mParent.requestLogin(body)
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
        if (model?.data != null){
            user = model.data.toString()
            token = model.data?.token.toString()
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }


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
        loginloding.visibility = View.VISIBLE
    }

    override fun dismissLoading() {
//        progressBar.visibility = View.GONE
        loginloding.visibility = View.GONE
    }

    override fun onDestroy() {
        mParent.detachView()
        super.onDestroy()
    }
}