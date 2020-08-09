package com.btm.swiftkt.http

import java.io.Serializable

/**
 * @Auther: hero
 * @datetime: 2020/8/9 17:51
 * @desc:
 * @项目: SwiftKt
 */
class MyRequestBody():Serializable {
    var userId: Int? = null
    var page: Int? = null
    var pageSize: Int? = null

}