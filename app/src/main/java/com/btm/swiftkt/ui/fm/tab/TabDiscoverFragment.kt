package com.btm.swiftkt.ui.fm.tab

import BaseFragment
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.btm.swiftkt.R
import com.btm.swiftkt.adapter.DiscoverAdapter
import com.btm.swiftkt.bean.Data
import com.btm.swiftkt.bean.HomeModel
import com.btm.swiftkt.mvp.contract.TabHomeContract
import com.btm.swiftkt.mvp.presenter.TabHomePresenter
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.layout_tab.*
import kotlinx.android.synthetic.main.myrecycleviewlayout.*
import okhttp3.MediaType
import okhttp3.RequestBody

/**

 * @Auther: hero

 * @datetime: 2020/8/2 23:30

 * @desc: 发现页面

 * @项目:  SwiftKt

 */
class TabDiscoverFragment():BaseFragment() , TabHomeContract.View{
    private val mParent by lazy { TabHomePresenter() }
    private var itemList = ArrayList<Data>()
    private val adapter by lazy { DiscoverAdapter(itemList) }
    lateinit var mTitle: String
    companion object {
        fun getInstance(title: String): TabDiscoverFragment {
            val fragment = TabDiscoverFragment()
            val bundle = Bundle()
            fragment.mTitle = title
            fragment.arguments = bundle
            return fragment
        }
    }
    init {
        mParent.attachView(this)
    }
    /**
     * 加载布局
     */
    override fun getLayoutId() = R.layout.fragment_discover

    /**
     * 初始化 ViewI
     */
    override fun initView() {
        tv_tab_title.text = "发现"
        recyclerview.layoutManager = GridLayoutManager(context,3)
        recyclerview.adapter = adapter


    }

    /**
     * 懒加载
     */
    override fun lazyLoad() {
        val json = JsonObject()
        json.addProperty("page",1)
        json.addProperty("pageSize",21)
        json.addProperty("userId",0)
        val body: RequestBody =
            RequestBody.create(MediaType.parse("application/json"), json.toString())
        mParent.requestHomeData(body)
    }

    /**
     * 获取首页结果
     */
    override fun homeDataResult(model: HomeModel?) {
        itemList = model?.data as ArrayList<Data>
        adapter.notifyDataSetChanged()
    }

    /**
     * 获取点赞结果
     */
    override fun startResult(model: Data?) {
    }

    /**
     * 收藏结果
     */
    override fun collectionResult(model: Data?) {
    }

    /**
     * 获取错误信息
     */
    override fun showError(errorMsg: String, errorCode: Int) {
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }
    override fun onDestroy() {
        mParent.detachView()
        super.onDestroy()
    }
}