package zone.com.zrefreshturnpage

import android.util.Log
import androidx.annotation.UiThread

import com.zone.adapter3kt.QuickAdapter
import ezy.ui.layout.LoadingLayout

import retrofit2.Call
import retrofit2.Response
import zone.com.okhttplib.java.callwrapper.DialogCall
import zone.com.autoturnpage.BasePullView
import zone.com.zadapter3kt.helper.OnScrollRcvListenerExZRefresh
import zone.com.zrefreshlayout.ZRefreshLayout

/**
 * [2017] by Zone
 * LoadingLayout 仅仅考虑第一次  第一次之后 则不考虑了
 */

abstract class ZonePullView<E>(pullView: ZRefreshLayout, adapter: QuickAdapter<*>) :
        BasePullView<ZRefreshLayout, QuickAdapter<*>, E>(pullView, adapter) {

    init {
        refreshViewSetListener()
    }

    //自己写的方法 用于第一次网络请求 不是刷新操作的时候 三中布局状态 loading success error带重试
    fun firstLoading(offset: Int, mLoadingLayout: LoadingLayout) {
        firstNumber = offset
        this.offset.set(firstNumber)
        val callBack = request(this.offset, limit)
        val dialogCall: DialogCall<E>
        if (callBack is DialogCall<E>)
            dialogCall = DialogCall(callBack)
        else
            dialogCall = callBack as DialogCall<E>
        bindRefreshEngine(dialogCall.loadingLayout(mLoadingLayout))
    }

    private fun refreshViewSetListener() {
        adapter.loadOnScrollListener = OnScrollRcvListenerExZRefresh(pullView)
        pullView.pullListener = object : ZRefreshLayout.PullListener {
            override fun refresh(zRefreshLayout: ZRefreshLayout) {
                setCanLoadMore(true)
                adapter.loadMoreCompleteForce()
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
            pullView.refreshComplete()
        forceEnd()
    }

    private fun forceEnd() {
        if (offset.isEnd()) {
            adapter.loadMoreEndForce()
            setCanLoadMore(false)
        }
    }

    override fun onLoadMoreComplete() {
        if (pullView.isLoadMore) {
            if (offset.isEnd()) {
                onLoadMoreEnd()
                setCanLoadMore(false)
            } else {
                pullView.postDelayed({
                    adapter.loadMoreComplete()
                }, 16)

            }
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
            forceEnd()
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


