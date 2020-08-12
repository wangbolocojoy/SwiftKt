package com.btm.swiftkt.ui.fm.tab

import BaseFragment
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.btm.swiftkt.R
import com.btm.swiftkt.adapter.DiscoverAdapter
import com.btm.swiftkt.bean.Data
import com.btm.swiftkt.bean.HomeModel
import com.btm.swiftkt.mvp.contract.TabHomeContract
import com.btm.swiftkt.mvp.presenter.TabHomePresenter
import com.google.gson.JsonObject
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.adapter_tab_home.*
import kotlinx.android.synthetic.main.icloud_toolbar.*
import kotlinx.android.synthetic.main.myrecycleviewlayout.*
import okhttp3.MediaType
import okhttp3.RequestBody

/**

 * @Auther: hero

 * @datetime: 2020/8/2 23:30

 * @desc: 发现页面

 * @项目:  SwiftKt

 */
class TabDiscoverFragment() : BaseFragment(), TabHomeContract.View {
    private val mParent by lazy { TabHomePresenter() }
    private var itemList = ArrayList<Data>()
    private val adapter by lazy { DiscoverAdapter(itemList) }
    lateinit var mTitle: String
    private var page = 0

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
        toolbar_name.text = "发现"
        recyclerview.layoutManager = GridLayoutManager(context, 3)
        recyclerview.adapter = adapter
        initRefresh()

    }

    /**
     * 懒加载
     */
    override fun lazyLoad() {
       getListData(page,1)
    }
    fun getListData(page:Int,type: Int){
        val json = JsonObject()
        json.addProperty("page",page)
        json.addProperty("pageSize",21)
        json.addProperty("userId",0)
        val body: RequestBody =
            RequestBody.create(MediaType.parse("application/json"), json.toString())
        mParent.requestHomeData(body,type)
    }
    fun initRefresh(){
        myRefreshLayout.setOnRefreshListener {
            page = 0
            getListData(page,1)
            myRefreshLayout.finishRefresh(500)
        }
        myRefreshLayout.setOnLoadMoreListener {
            page+=1
            getListData(page,2)
            myRefreshLayout.finishLoadMore(500)
        }

    }
    /**
     * 获取首页结果
     */
    override fun homeDataResult(model: HomeModel?,type: Int) {
        if (type == 1){
            itemList = model?.data as ArrayList<Data>
            if (model.data.size  <20){
                myRefreshLayout.finishLoadMoreWithNoMoreData()
            }
            adapter.setNewData(itemList)
            adapter.notifyDataSetChanged()
        }else{
            Logger.d("数量"+itemList.size)
            model?.data?.let { itemList.addAll(itemList.size-1,it) }

            if (model?.data?.size ?:0 <20){
                myRefreshLayout.finishLoadMoreWithNoMoreData()
            }

            adapter.notifyDataSetChanged()
        }

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