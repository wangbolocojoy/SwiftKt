package com.btm.swiftkt.mvp.contract

import BaseResponse
import IBaseView
import com.btm.swiftkt.bean.LoginModel
import com.btm.swiftkt.http.MyRequestBody
import com.sun.mail.imap.protocol.BODY
import okhttp3.RequestBody

/**
 * @author hero
 * @Auther: hero
 * @datetime: 2020/7/17 23:52
 * @desc:
 * @项目: SwiftKt
 */
interface LoginViewContract {
    interface Model
    interface View : IBaseView {
        /**
         * 获取登录结果
         */
        fun loginResult(model: BaseResponse<LoginModel>?)
        /**
         * 获取注册结果
         */
        fun registerResult(model: BaseResponse<LoginModel>?)
        /**
         * 获取修改密码结果
         */
        fun changePwdResult(model: BaseResponse<LoginModel>?)
        /**
         * 获取错误信息
         */
        fun showError(errorMsg: String, errorCode: Int)
    }

    interface Presenter {
        /**
         * 请求登录
         */
        fun requestLogin(body: RequestBody)
        /**
         *请求注册
         */
        fun requestRegister(body: RequestBody)
        /**
         *请求修改密码
         */
        fun requestChangePwd(body: RequestBody)
    }
}