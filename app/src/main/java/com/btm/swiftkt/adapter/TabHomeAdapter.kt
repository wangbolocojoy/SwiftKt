package com.btm.swiftkt.adapter

import com.btm.swiftkt.R
import com.btm.swiftkt.bean.Data
import com.btm.swiftkt.bean.PostImage
import com.btm.swiftkt.utils.ImageAdapter
import com.btm.swiftkt.view.CircleImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.youth.banner.Banner
import com.youth.banner.adapter.BannerAdapter
import com.youth.banner.adapter.BannerImageAdapter
import kotlinx.android.synthetic.main.adapter_tab_home.*

/**
 * @Auther: hero
 * @datetime: 2020/8/8 20:17
 * @desc:
 * @项目: SwiftKt
 */
 class TabHomeAdapter(data:ArrayList<Data>):BaseQuickAdapter<Data,BaseViewHolder>(R.layout.adapter_tab_home,data){

        override fun convert(helper: BaseViewHolder?, item: Data?) {
        helper?.setText(R.id.textView2,item?.author?.nickName ?: "")
        helper?.setText(R.id.textView4,item?.postAddress?: "")
        helper?.setText(R.id.textView5,item?.postStarts.toString() ?: "")
        helper?.setText(R.id.textView7,item?.author?.nickName ?: "")
        helper?.setText(R.id.textView9,item?.postDetail ?: "")
        helper?.setText(R.id.textView10,item?.postMessageNum.toString() ?: "")
        helper?.setText(R.id.textView8,item?.creatTime?: "")
       val banner = helper?.getView<Banner<PostImage,BannerImageAdapter<PostImage>>>(R.id.banner)
            val adapter by lazy {ImageAdapter(item?.postImages)}
        banner?.setAdapter(adapter)
        helper?.getView<CircleImageView>(R.id.circleImageView)?.let { Glide.with(mContext).load(item?.author?.icon ?: "").into(it) };

        }


}