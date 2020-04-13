package zone.com.retrofitlisthelper

import android.os.Bundle
import android.widget.ImageView
import androidx.annotation.UiThread
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zone.adapter3kt.QuickAdapter
import com.zone.adapter3kt.data.DataWarp
import ezy.ui.layout.LoadingLayout
import kotlinx.android.synthetic.main.activity_list.*
import retrofit2.Call
import zone.com.okhttplib.android.utils.HandlerUiUtil
import zone.com.retrofitlisthelper.api.baidu.api.BaiduImpl
import zone.com.retrofitlisthelper.api.baidu.bean.MeiZiData
import zone.com.zadapter3kt.adapterimpl.HolderExDemoImpl
import zone.com.zadapter3kt.adapterimpl.ViewDelegatesDemo
import zone.com.zrefreshturnpage.ZonePullView

//都改成用预加载了 谁还用回弹啊

class ListActivity : AppCompatActivity() {

    private var adapter: QuickAdapter<MeiZiData.DataBean>? = null
    lateinit var zonePullView: ZonePullView<MeiZiData>
    lateinit var rv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        loading.setNum(12)
        loading.hostRadius = 60
        loading.radius = 13

        val hasLoadingLayout = intent.getBooleanExtra("isLoadingLayout", false)


        rv = findViewById(R.id.rv)
        rv.layoutManager = LinearLayoutManager(this)
        adapter = QuickAdapter<MeiZiData.DataBean>(this).apply {
            enableHistory(true)
//            registerEmpytDelegate(R.layout.item_empty)
            registerDelegate(object : ViewDelegatesDemo<MeiZiData.DataBean>() {
                override fun onBindViewHolder(position: Int, item: DataWarp<MeiZiData.DataBean>, baseHolder: HolderExDemoImpl, payloads: List<*>) {
                    item.data?.let {
                        Glide.with(this@ListActivity).load(it.middleURL).into(baseHolder.getView(R.id.id_num) as ImageView)
                    }
                }

                override val layoutId: Int = R.layout.item

            })
            rv.adapter = this
        }

        zonePullView = object : ZonePullView<MeiZiData>(refresh, adapter!!) {
            override fun request(offset: OffsetWarpper, limit: Int): Call<MeiZiData> {
                return BaiduImpl().getPics(limit.toString(), (offset.get() * limit).toString())
            }

            override fun handleData(offset: OffsetWarpper, body: MeiZiData?) {
                val data = body!!.data
                for (datum in data) {
                    if (datum.width == 0) {
                        data.remove(datum)
                    }
                }
                if (offset.get() != 10) {
                    this@ListActivity.adapter?.add(data)
                }
                setTurnPageTv(offset, data.size)
            }

            @UiThread
            private fun setTurnPageTv(offset: OffsetWarpper, dataSize: Int) {
                tv.text = "offset:${offset.get()}\t limt:$limit \tnowPageDatasize:$dataSize \n " +
                        "总数量：${this@ListActivity.adapter?.getContentDatas()?.size ?: 0}"
            }
        }
        zonePullView.limit = 10

        if (hasLoadingLayout) {
            HandlerUiUtil.postDelay({
                //                refresh.autoRefresh(true);
                //第一次请求
                zonePullView.firstLoading(0, LoadingLayout.wrap(ll_root!!))
            }, 500)
        } else {
            //刷新的offset开始
            zonePullView.firstNumber = 3
        }

    }
}
