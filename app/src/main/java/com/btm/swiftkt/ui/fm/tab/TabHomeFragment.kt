package com.btm.swiftkt.ui.fm.tab

import BaseFragment
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.btm.swiftkt.R
import com.btm.swiftkt.adapter.TabHomeAdapter
import com.btm.swiftkt.bean.Data
import com.btm.swiftkt.bean.HomeModel
import com.btm.swiftkt.mvp.contract.TabHomeContract
import com.btm.swiftkt.mvp.presenter.TabHomePresenter
import com.btm.swiftkt.utils.StatusBarUtil
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.icloud_toolbar.*
import kotlinx.android.synthetic.main.myrecycleviewlayout.*
import okhttp3.MediaType
import okhttp3.RequestBody

/**
 * 首页
 */
class TabHomeFragment() : BaseFragment(),TabHomeContract.View {

    lateinit var mTitle: String
    private var itemList = ArrayList<Data>()
    private val mParent by lazy { TabHomePresenter() }
    private var page = 0
    val adapter = TabHomeAdapter(null)
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
        activity?.let { StatusBarUtil.darkMode(it) }
        activity?.let { StatusBarUtil.setPaddingSmart(it, toolbar) }
        toolbar.navigationIcon?.setTint(resources.getColor(R.color.clean))
        toolbar_name.text = "首页"
        recyclerview.layoutManager = LinearLayoutManager(context)
        recyclerview.adapter = adapter
        initRefresh()
    }

    /**
     * 懒加载
     */
    override fun lazyLoad() {
        getListData(page,1)
    }
    private fun getListData(page:Int, type: Int){
        val json = JsonObject()
        json.addProperty("page",page)
        json.addProperty("pageSize",10)
        json.addProperty("userId",0)
        val body: RequestBody =
            RequestBody.create(MediaType.parse("application/json"), json.toString())
        mParent.requestHomeData(body,type)
    }
    private fun initRefresh(){
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
    override fun homeDataResult(model: HomeModel?,type: Int) {
        if (type == 1){
            adapter.setNewData(model?.data)
            if (model?.data?.size ?:0 < 9){
                myRefreshLayout.finishLoadMoreWithNoMoreData()
            }

        }else{
            model?.data?.let { adapter.addData( it) }
            if (model?.data?.size ?:0 <9){
                myRefreshLayout.finishLoadMoreWithNoMoreData()
            }
        }
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