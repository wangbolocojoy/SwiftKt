package com.btm.swiftkt.adapter

import com.btm.swiftkt.R
import com.btm.swiftkt.bean.Data
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * @Auther: hero
 * @datetime: 2020/8/10 23:16
 * @desc:
 * @项目:  SwiftKt
 */
class Adapter_Friend(data:ArrayList<Data>):BaseQuickAdapter<Data,BaseViewHolder>(R.layout.adapter_discover) {
    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    override fun convert(helper: BaseViewHolder?, item: Data?) {

    }
}