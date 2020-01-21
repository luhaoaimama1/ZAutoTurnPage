package zone.com.zhelper

import android.util.Log
import androidx.annotation.UiThread

import androidx.recyclerview.widget.RecyclerView

import com.zone.adapter3kt.QuickAdapter

import retrofit2.Call
import retrofit2.Response
import zone.com.retrofit.BasePullView
import zone.com.retrofitlib.utils.HandlerUiUtil
import zone.com.zadapter3kt.helper.OnScrollRcvListenerExZRefresh
import zone.com.zrefreshlayout.ZRefreshLayout

/**
 * [2017] by Zone
 * LoadingLayout 仅仅考虑第一次  第一次之后 则不考虑了
 */

abstract class ZonePullView<E>(pullView: ZRefreshLayout, adapter: QuickAdapter<*>) :
        BasePullView<ZRefreshLayout, QuickAdapter<*>, E>(pullView, adapter) {

    init {
        pullViewSetListener()
    }

    private fun pullViewSetListener() {
        adapter.loadOnScrollListener = OnScrollRcvListenerExZRefresh(pullView)
        pullView.pullListener = object : ZRefreshLayout.PullListener {
            override fun refresh(zRefreshLayout: ZRefreshLayout) {
                setCanLoadMore(true)
                offset.set(firstNumber)
                log("请求 pullListener offset:${offset.get()}")
                bindRefreshEngine(request(offset, limit))
            }

        }
        pullView.setLoadMoreListener(true, object : ZRefreshLayout.LoadMoreListener {
            override fun loadMore(zRefreshLayout: ZRefreshLayout) {
                loadMoreReal()
            }

        })
    }

    @UiThread
    private fun loadMoreReal() {
        log("请求 loadMoreReal   offset:${offset.get()}")
        bindRefreshEngine(request(offset, limit))
    }

    override fun onRefreshComplete() {
        if (pullView.isRefresh)
            if (offset.isEnd())
                adapter.loadMoreEnd()
            else
                pullView.refreshComplete()
    }

    override fun onLoadMoreComplete() {
        if (pullView.isLoadMore) {
            if (offset.isEnd()) {
                onLoadMoreEnd()
                setCanLoadMore(false)
            } else
                adapter.loadMoreComplete()
        }

    }

    override fun onLoadMoreFail() {
        if (pullView.isLoadMore)
            adapter.loadMoreFail()
    }

    override fun onLoadMoreEnd() {
        if (pullView.isLoadMore)
            adapter.loadMoreEnd()
    }


    var contentNumber = -1

    override fun handleDataBefore() {
        if (offset.isAutoDeal) {
            contentNumber = adapter.getContentDatas().size
        }
    }

    override fun handleDataAfter() {
        if (offset.isAutoDeal && contentNumber != -1) {
            val difference = adapter.getContentDatas().size - contentNumber
            if (difference < limit) {
                offset.end()
            } else
                offset.set(offset.get() + 1)
        }
        adapter.getContentDatas()
    }

    override fun clearData() {
        //todo zone clear(Part.HEADER  ...)
        adapter.clearAll()
    }

    override fun doLast(successful: Boolean, call: Call<E>, response: Response<E>?, t: Throwable?) {
        if (pullView.isRefreshOrLoadMore) {
            onRefreshComplete()
            onLoadMoreComplete()
            log(" doLastx isRefreshOrLoadMore")
        } else {
            //考虑第一次loading那种
            if (offset.isEnd()) {
                adapter.loadMoreEnd()
                log(" doLastx isEnd")
            }
        }
    }

    override fun setCanLoadMore(canLoadMore: Boolean) {
        pullView.isCanLoadMore = canLoadMore
    }

    var isDebug = false
    fun log(content: String) {
        if (isDebug) {
            Log.d("ZonePullView", content)
        }
    }
}


