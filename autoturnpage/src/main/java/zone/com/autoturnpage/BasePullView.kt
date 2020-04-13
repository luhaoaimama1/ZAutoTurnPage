package zone.com.autoturnpage

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A listview that package a t
 * BasePullView(context,pullView,listView,adapter,data,ParseDataListener).bindEngine();
 *
 * [2017] by Zone
 * @param <P>
 * @param <A>
 * @param <E> 数据类型
 */
abstract class BasePullView<P, A, E>(
        var pullView: P,
        var adapter: A) {
    companion object {
        var OFFSET_END = -1
    }

    class OffsetWarpper(private var obj: Int) {
        var isAutoDeal = true
        fun set(obj: Int) {
            this.obj = obj
        }

        fun get() = obj
        fun end() {
            obj = OFFSET_END
        }

        fun isEnd() = obj == OFFSET_END
    }

    var firstNumber = 0
        set(firstNumber) {
            field = firstNumber
            offset.set(firstNumber)
        }

    var offset = OffsetWarpper(0)

    var limit = 20

    fun bindRefreshEngine(call: Call<E>) {
        val callback = object : Callback<E> {
            override fun onResponse(call: Call<E>, response: Response<E>) {
                var successful = false
                if (response.isSuccessful) {
                    //第一页的时候 清除数据
                    if (offset.get() == firstNumber)
                        clearData()

                    handleDataBefore()
                    //data 可以想象已经处理完毕  记得处理offset
                    handleData(offset, response.body())
                    handleDataAfter()
                    successful = true
                }
                doLast(successful, call, response, null)
            }

            override fun onFailure(call: Call<E>, t: Throwable) {
                //发生错误 包括转换错误
                doLast(false, call, null, t)
            }

            private fun doLast(successful: Boolean, call: Call<E>, response: Response<E>?, t: Throwable?) {
                this@BasePullView.doLast(successful, call, response, t)
            }
        }
        call.enqueue(callback)
    }

    abstract fun handleDataBefore()
    abstract fun handleDataAfter()

    abstract fun clearData()

    /**
     * 请求数据，并返回请求的 uuid
     * 例如：return mDiycode.getTopicsList(null, mNodeId, offset, limit);
     *
     * @param offset 偏移量  todo 0是刷新 或者 第一次加载,其他加载;
     * @param limit  请求数量
     * @return uuid
     */
    protected abstract fun request(offset: OffsetWarpper, limit: Int): Call<E>

    protected abstract fun handleData(offset: OffsetWarpper, body: E?)


    abstract fun onRefreshComplete()

    /**
     * 这里可以处理 error fail的事情
     *
     * @param successful
     * @param call
     * @param response
     * @param t
     */
    abstract fun doLast(successful: Boolean, call: Call<E>, response: Response<E>?, t: Throwable?)

    abstract fun onLoadMoreComplete()

    abstract fun onLoadMoreFail()

    abstract fun onLoadMoreEnd()

    abstract fun setCanLoadMore(canLoadMore: Boolean)
}
