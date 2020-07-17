package com.btm.mylibrary.rx

import BaseScheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**

 * @Auther: hero

 * @datetime: 2020/7/17 23:41

 * @desc:

 * @项目:  SwiftKt

 */
object Schedulers {
    class TrampolineMainScheduler<T> private constructor() : BaseScheduler<T>(Schedulers.trampoline(), AndroidSchedulers.mainThread())
    class SingleMainScheduler<String> internal constructor() : BaseScheduler<kotlin.String>(Schedulers.single(), AndroidSchedulers.mainThread())
    class NewThreadMainScheduler<T> private constructor() : BaseScheduler<T>(Schedulers.newThread(), AndroidSchedulers.mainThread())
    class IoMainScheduler<T> : BaseScheduler<T>(Schedulers.io(), AndroidSchedulers.mainThread())
    class ComputationMainScheduler<T> private constructor() : BaseScheduler<T>(Schedulers.computation(), AndroidSchedulers.mainThread())


}