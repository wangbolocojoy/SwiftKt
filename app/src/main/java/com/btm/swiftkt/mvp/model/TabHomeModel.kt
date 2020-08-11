package com.btm.swiftkt.mvp.model

import com.btm.swiftkt.bean.HomeModel
import com.btm.swiftkt.mvp.contract.TabHomeContract
import io.reactivex.Observable
import io.reactivex.Scheduler
import okhttp3.RequestBody

/**
 * @Auther: hero
 * @datetime: 2020/8/9 17:14
 * @desc:
 * @项目: SwiftKt
 */
class TabHomeModel {
    fun mdTabHomeData(b: RequestBody,type:Int): Observable<HomeModel>?{
        return RetrofitManager.service.tabHome(b)
            .compose(SchedulerUtils.ioToMain())
    }
    fun mdStart(b: RequestBody): Observable<HomeModel>?{
        return RetrofitManager.service.tabHome(b)
            .compose(SchedulerUtils.ioToMain())
    }
    fun mdCollection(b: RequestBody): Observable<HomeModel>?{
        return RetrofitManager.service.tabHome(b)
            .compose(SchedulerUtils.ioToMain())
    }
}