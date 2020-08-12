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
        fun loginResult(model: BaseResponse<LoginModel>)
        fun registerResult(model: BaseResponse<LoginModel>)
        fun changePwdResult(model: BaseResponse<LoginModel>)
        /**
         * 获取错误信息
         */
        fun showError(errorMsg: String, errorCode: Int)
    }

    interface Presenter {
        fun requestLogin(body: RequestBody)
        fun requestRegister(body: RequestBody)
        fun requestChangePwd(body: RequestBody)
    }
}