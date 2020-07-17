package com.hazz.kotlinmvp.mvp.model.bean

/**
 * Created by wb on 2018/11/30.
 * desc: 热门的 tabInfo
 */

data class TabInfoBean(val tabInfo: TabInfo) {
    data class TabInfo(val tabList: ArrayList<Tab>)

    data class Tab(val id: Long, val name: String, val apiUrl: String)
}
