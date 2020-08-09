package com.btm.swiftkt.utils

import android.view.ViewGroup
import android.widget.ImageView
import com.btm.swiftkt.bean.PostImage
import com.bumptech.glide.Glide
import com.youth.banner.adapter.BannerAdapter

/**
 * @Auther: hero
 * @datetime: 2020/8/9 19:40
 * @desc:
 * @项目: SwiftKt
 */
/**
 * 自定义布局，图片
 */
class ImageAdapter(mDatas: List<PostImage?>?) :
    BannerAdapter<PostImage?, ImageHolder>(mDatas) {
    //更新数据
    fun updateData(data: List<PostImage?>?) {
        //这里的代码自己发挥，比如如下的写法等等
        mDatas.clear()
        mDatas.addAll(data!!)
        notifyDataSetChanged()
    }

    //创建ViewHolder，可以用viewType这个字段来区分不同的ViewHolder
    override fun onCreateHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val imageView = ImageView(parent.context)
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        val params = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.layoutParams = params
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        return ImageHolder(imageView)
    }

    override fun onViewRecycled(holder: ImageHolder) {
        super.onViewRecycled(holder)
    }



    /**
     * 绑定布局数据
     *
     * @param holder   XViewHolder
     * @param data     数据实体
     * @param position 当前位置
     * @param size     总数
     */
    override fun onBindView(holder: ImageHolder?, data: PostImage?, position: Int, size: Int) {
        holder?.imageView?.let { Glide.with(it).load(data?.fileUrl).into(it) }
    }
}