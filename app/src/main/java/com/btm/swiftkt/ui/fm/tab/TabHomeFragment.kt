package com.btm.swiftkt.ui.fm.tab

import BaseFragment
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.btm.swiftkt.R
import com.btm.swiftkt.adapter.TabHomeAdapter
import com.btm.swiftkt.bean.Data
import com.btm.swiftkt.bean.HomeModel
import com.btm.swiftkt.http.MyRequestBody
import com.btm.swiftkt.mvp.contract.TabHomeContract
import com.btm.swiftkt.mvp.presenter.TabHomePresenter
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.myrecycleviewlayout.*
import okhttp3.MediaType
import okhttp3.RequestBody

/**
 * 首页
 */
class TabHomeFragment() : BaseFragment(),TabHomeContract.View {

    lateinit var mTitle: String
    private var itemList = ArrayList<Data>()
    private val  adapter by lazy { TabHomeAdapter(itemList) }
    private val mParent by lazy { TabHomePresenter() }
    private var page = 0
    companion object {
        fun getInstance(title: String): TabHomeFragment {
            val fragment = TabHomeFragment()
            val bundle = Bundle()
            fragment.mTitle = title
            fragment.arguments = bundle
            return fragment
        }

    }
    /**
     * 加载布局
     */
    override fun getLayoutId() = R.layout.fragment_home_fullscreen

    /**
     * 初始化 View
     */
    override fun initView() {
        recyclerview.layoutManager = LinearLayoutManager(context)
        recyclerview.adapter = adapter
    }

    /**
     * 懒加载
     */
    override fun lazyLoad() {
        val json = JsonObject()
        json.addProperty("page",1)
        json.addProperty("pageSize",10)
        json.addProperty("userId",0)
        val body: RequestBody =
            RequestBody.create(MediaType.parse("application/json"), json.toString())
        mParent.requestHomeData(body)
    }

    override fun onDestroy() {
        mParent.detachView()
        super.onDestroy()

    }

    init {
        mParent.attachView(this)
    }

    /**
     * 获取首页结果
     */
    override fun homeDataResult(model: HomeModel?) {
        adapter.setNewData(model?.data)
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

}