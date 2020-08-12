package com.btm.swiftkt.mvp.presenter

import BasePresenter
import com.btm.swiftkt.mvp.contract.LoginViewContract
import com.btm.swiftkt.mvp.model.LoginViewModel
import okhttp3.RequestBody

/**
 * @Auther: hero
 * @datetime: 2020/7/17 23:52
 * @desc:
 * @项目: SwiftKt
 */
class LoginViewPresenter:BasePresenter<LoginViewContract.View>(),LoginViewContract.Presenter{
    private val model by lazy {
        LoginViewModel()
    }
    override fun requestLogin(body: RequestBody) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.mdLogin(body)?.subscribe({
            mRootView?.apply {
            dismissLoading()
                loginResult(it)
            }
        },{
            mRootView?.apply {
                dismissLoading()
                showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
            }
        })
        disposable?.let { addSubscription(it) }
    }

    override fun requestRegister(body: RequestBody) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.mdRegister(body)?.subscribe({
            mRootView?.apply {
                dismissLoading()
                registerResult(it)
            }
        },{
            mRootView?.apply {
                dismissLoading()
                showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
            }
        })
        disposable?.let { addSubscription(it) }
    }

    override fun requestChangePwd(body: RequestBody) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.mdChangePwd(body)?.subscribe({
            mRootView?.apply {
                dismissLoading()
                changePwdResult(it)
            }
        },{
            mRootView?.apply {
                dismissLoading()
                showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
            }
        })
        disposable?.let { addSubscription(it) }
    }

}