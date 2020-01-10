package zone.com.retrofitlisthelper

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zone.adapter3kt.QuickAdapter
import java.util.ArrayList
import butterknife.ButterKnife
import com.zone.adapter3kt.data.DataWarp
import ezy.ui.layout.LoadingLayout
import kotlinx.android.synthetic.main.activity_list.*
import retrofit2.Call
import zone.com.retrofit.BasePullView
import zone.com.retrofitlib.utils.HandlerUiUtil
import zone.com.sdk.API.gank.bean.MeiZiData
import zone.com.sdk.Diycode
import zone.com.zadapter3kt.adapterimpl.HolderExDemoImpl
import zone.com.zadapter3kt.adapterimpl.ViewDelegatesDemo
import zone.com.zhelper.ZonePullView
import zone.com.zrefreshlayout.Config
import zone.com.zrefreshlayout.footer.MeterialFooter
import zone.com.zrefreshlayout.header.MeterialHead
import zone.com.zrefreshlayout.resistance.DampingHalf

class ListActivity : AppCompatActivity() {

    private val datas = ArrayList<MeiZiData.ResultsBean>()
    private var adapter: QuickAdapter<MeiZiData.ResultsBean>? = null
    lateinit var rv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        initRefreshConfig()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        ButterKnife.bind(this)
        loading.setNum(12)
        loading.hostRadius = 60
        loading.radius = 13
        rv = findViewById(R.id.rv)
        rv.layoutManager = LinearLayoutManager(this)
        adapter = QuickAdapter<MeiZiData.ResultsBean>(this).apply{
            registerDelegate(object : ViewDelegatesDemo<MeiZiData.ResultsBean>() {
                override fun onBindViewHolder(position: Int, item: DataWarp<MeiZiData.ResultsBean>, baseHolder: HolderExDemoImpl, payloads: List<*>) {
                    item.data?.let {
                        Glide.with(this@ListActivity).load(it.url).into(baseHolder.getView(R.id.id_num) as ImageView)
                    }

                }

                override val layoutId: Int=R.layout.item

            })
            add(datas)
        }

        val zonePullView = object : ZonePullView<MeiZiData>(refresh, adapter!!) {
            protected override fun request(offset: BasePullView.OffsetWarpper, limit: Int): Call<MeiZiData> {
                return Diycode.getInstance().getPics(offset.get() + "", limit.toString() + "")
            }

            protected override fun handleData(offset: BasePullView.OffsetWarpper, body: MeiZiData?) {
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


    private fun initRefreshConfig() {
        val colors_red_green_yellow = intArrayOf(Color.parseColor("#ffF44336"), Color.parseColor("#ff4CAF50"), Color.parseColor("#ffFFEB3B"))
        Config.build()
                .setHeader(MeterialHead(colors_red_green_yellow))
                .setPinContent(true)
                .setFooter(MeterialFooter())
                .setResistance(DampingHalf())
                //                .setHeader(new  CircleRefresh())
                //                .setResistance(new Damping2Head8per())
                .writeLog(true)
                .perform()
    }

}
