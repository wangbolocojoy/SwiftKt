package com.btm.swiftkt.mvp.contract

import IBaseView
import com.btm.swiftkt.bean.Data
import com.btm.swiftkt.bean.HomeModel
import okhttp3.RequestBody

/**
 * @Auther: hero
 * @datetime: 2020/8/9 17:14
 * @desc:
 * @项目: SwiftKt
 */
interface TabHomeContract {
    interface Model
    interface View : IBaseView {
        /**
         * 获取首页结果
         */
        fun homeDataResult(model: HomeModel?,type:Int)

        /**
         * 获取点赞结果
         */
        fun startResult(model: Data?)

        /**
         * 收藏结果
         */
        fun collectionResult(model: Data?)
        /**
         * 获取错误信息
         */
        fun showError(errorMsg: String, errorCode: Int)
    }

    interface Presenter {
        fun requestHomeData(body: RequestBody,type:Int)
        fun requestStart(body: RequestBody)
        fun requestCollection(body: RequestBody)
    }

}