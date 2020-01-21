package zone.com.retrofitlisthelper

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zone.adapter3kt.QuickAdapter
import com.zone.adapter3kt.QuickConfig
import java.util.ArrayList
import com.zone.adapter3kt.data.DataWarp
import com.zone.adapter3kt.loadmore.LoadingSetting
import ezy.ui.layout.LoadingLayout
import kotlinx.android.synthetic.main.activity_list.*
import retrofit2.Call
import zone.com.retrofit.BasePullView
import zone.com.retrofitlib.utils.HandlerUiUtil
import zone.com.sdk.API.gank.api.GankImpl
import zone.com.sdk.API.gank.bean.MeiZiData
import zone.com.sdk.Diycode
import zone.com.zadapter3kt.adapterimpl.HolderExDemoImpl
import zone.com.zadapter3kt.adapterimpl.ViewDelegatesDemo
import zone.com.zhelper.ZonePullView
import zone.com.zrefreshlayout.Config
import zone.com.zrefreshlayout.ZRefreshLayout
import zone.com.zrefreshlayout.footer.MeterialFooter
import zone.com.zrefreshlayout.header.MeterialHead
import zone.com.zrefreshlayout.resistance.DampingHalf

class ListActivity : AppCompatActivity() {

    private var adapter: QuickAdapter<MeiZiData.ResultsBean>? = null
    lateinit var  zonePullView  :ZonePullView<MeiZiData>
    lateinit var rv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        initRefreshConfig()
        QuickConfig.build().apply {
            loadingSetting = LoadingSetting().apply {
                threshold = 4
            }
            perform()
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        loading.setNum(12)
        loading.hostRadius = 60
        loading.radius = 13
        rv = findViewById(R.id.rv)
        rv.layoutManager = LinearLayoutManager(this)
        adapter = QuickAdapter<MeiZiData.ResultsBean>(this).apply {
            enableHistory(true)
//            registerEmpytDelegate(R.layout.item_empty)
            registerDelegate(object : ViewDelegatesDemo<MeiZiData.ResultsBean>() {
                override fun onBindViewHolder(position: Int, item: DataWarp<MeiZiData.ResultsBean>, baseHolder: HolderExDemoImpl, payloads: List<*>) {
                    item.data?.let {
                        Glide.with(this@ListActivity).load(it.url).into(baseHolder.getView(R.id.id_num) as ImageView)
                    }
                }

                override val layoutId: Int = R.layout.item

            })
            rv.adapter = this
        }

        zonePullView = object : ZonePullView<MeiZiData>(refresh, adapter!!) {
            override fun request(offset: OffsetWarpper, limit: Int): Call<MeiZiData> {
                return Diycode.getInstance().getPics(offset.get().toString(), limit.toString())
            }

            override fun handleData(offset: OffsetWarpper, body: MeiZiData?) {
                this@ListActivity.adapter?.add(body!!.results)
            }
        }
        //刷新的offset开始
        zonePullView.firstNumber = 5

        HandlerUiUtil.postDelay({
            //                refresh.autoRefresh(true);
            //第一次请求
            zonePullView.firstLoading(5, LoadingLayout.wrap(ll_root!!))
        }, 500)

    }

    override fun onResume() {
        super.onResume()
    }

    private fun initRefreshConfig() {
        val colors_red_green_yellow = intArrayOf(Color.parseColor("#ffF44336"), Color.parseColor("#ff4CAF50"), Color.parseColor("#ffFFEB3B"))
        Config.build()
                .setHeader(MeterialHead(colors_red_green_yellow))
                .setHeadPin(ZRefreshLayout.HeadPin.PIN)
                .setFooter(MeterialFooter())
                .setResistance(DampingHalf())
//              .setHeader(new  CircleRefresh())
//              .setResistance(new Damping2Head8per())
                .writeLog(true)
                .perform()
    }

}
