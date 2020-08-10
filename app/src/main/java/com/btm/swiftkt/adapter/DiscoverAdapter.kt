package com.btm.swiftkt.adapter

import android.widget.ImageView
import com.btm.swiftkt.R
import com.btm.swiftkt.bean.Data
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * @Auther: hero
 * @datetime: 2020/8/10 22:42
 * @desc:
 * @项目:  SwiftKt
 */
class DiscoverAdapter(data:ArrayList<Data>):BaseQuickAdapter<Data,BaseViewHolder>(R.layout.adapter_discover,data){
    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    override fun convert(helper: BaseViewHolder?, item: Data?) {
        helper?.getView<ImageView>(R.id.imageView4)?.let { Glide.with(it).load(item?.postImages?.get(0)?.fileUrl).into(it) }
    }

}