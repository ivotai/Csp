package com.unicorn.csp.ui.base

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.unicorn.csp.R
import com.unicorn.csp.app.defaultPaddingPx
import com.unicorn.csp.app.defaultPageSize
import com.unicorn.csp.app.observeOnMain
import com.unicorn.csp.data.model.Page
import com.unicorn.csp.data.model.Response
import com.unicorn.csp.ui.LinearSpanDecoration
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.ui_swipe_recycler.*

abstract class SimplePageFra2<K : BaseViewHolder> : BaseFra() {

    abstract val simpleAdapter: BaseMultiItemQuickAdapter<MultiItemEntity, K>

    abstract fun loadPage(pageNo: Int): Single<Response<Page<MultiItemEntity>>>

    private val total
        get() = simpleAdapter.data.size

    private val pageNo
        get() = total / defaultPageSize + 1

    protected open val mRecyclerView: RecyclerView get() = recyclerView

    protected open val mSwipeRefreshLayout: SwipeRefreshLayout get() = swipeRefreshLayout

    override fun initViews() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary)
        mRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            simpleAdapter.bindToRecyclerView(this)
            simpleAdapter.setEnableLoadMore(true)
            addItemDecoration(LinearSpanDecoration(defaultPaddingPx))
        }
    }

    override fun bindIntent() {
        mSwipeRefreshLayout.setOnRefreshListener { loadFirstPage() }
        simpleAdapter.setOnLoadMoreListener({ loadNextPage() }, mRecyclerView)
        loadFirstPage()
    }

    protected fun loadFirstPage() {
        mSwipeRefreshLayout.isRefreshing = true
        loadPage(1)
            .observeOnMain(this)
            .subscribeBy(
                onSuccess = {
                    mSwipeRefreshLayout.isRefreshing = false
                    simpleAdapter.setNewData(it.data.content)
                    checkIsLoadAll(it.data)
                    // 在这里设置 empty view
                    simpleAdapter.setEmptyView(R.layout.ui_no_data, mRecyclerView)
                },
                onError = {
                    mSwipeRefreshLayout.isRefreshing = false
                }
            )
    }

    private fun loadNextPage() {
        loadPage(pageNo)
            .observeOnMain(this)
            .subscribeBy(
                onSuccess = {
                    simpleAdapter.loadMoreComplete()
                    simpleAdapter.addData(it.data.content)
                    checkIsLoadAll(it.data)
                },
                onError = {
                    simpleAdapter.loadMoreComplete()
                }
            )
    }

    private fun checkIsLoadAll(pageResponse: Page<MultiItemEntity>) {
        val isLoadAll = total >= pageResponse.totalElements.toInt() // more safe but not exact
        if (isLoadAll) simpleAdapter.loadMoreEnd(true)
    }

    override val layoutId = R.layout.ui_swipe_recycler

}