package com.btm.swiftkt.mvp.presenter

import BasePresenter
import com.btm.swiftkt.mvp.contract.TabHomeContract
import com.btm.swiftkt.mvp.model.TabHomeModel
import io.reactivex.Observable
import okhttp3.RequestBody

/**
 * @Auther: hero
 * @datetime: 2020/8/9 17:14
 * @desc:
 * @项目: SwiftKt
 */
class TabHomePresenter :BasePresenter<TabHomeContract.View>(),TabHomeContract.Presenter{
    private val model by lazy { TabHomeModel() }
    override fun requestHomeData(body: RequestBody,type:Int) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.mdTabHomeData(body,type)?.subscribe({
            mRootView?.apply {
                dismissLoading()
                homeDataResult(it,type)
            }
        },{
            mRootView?.apply {
                dismissLoading()
                showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
            }
        })
        disposable?.let { addSubscription(it) }
    }

    override fun requestStart(body: RequestBody) {
    }

    override fun requestCollection(body: RequestBody) {
    }

}


