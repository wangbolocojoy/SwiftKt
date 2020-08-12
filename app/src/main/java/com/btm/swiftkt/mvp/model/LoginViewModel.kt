package com.btm.swiftkt.mvp.model

import BaseResponse
import com.btm.swiftkt.bean.LoginModel
import com.btm.swiftkt.mvp.contract.LoginViewContract
import io.reactivex.Observable
import okhttp3.RequestBody
import java.util.*

/**
 * @Auther: hero
 * @datetime: 2020/7/17 23:52
 * @desc:
 * @项目: SwiftKt
 */
class LoginViewModel {
    fun mdLogin(b:RequestBody): Observable<BaseResponse<LoginModel>>?{
        return RetrofitManager.service.login(b)
            .compose(SchedulerUtils.ioToMain())
    }
    fun mdRegister(b:RequestBody): Observable<BaseResponse<LoginModel>>?{
        return RetrofitManager.service.register(b)
            .compose(SchedulerUtils.ioToMain())
    }
    fun mdChangePwd(b:RequestBody): Observable<BaseResponse<LoginModel>>?{
        return RetrofitManager.service.changePwd(b)
            .compose(SchedulerUtils.ioToMain())
    }
}