package zone.com.retrofitlisthelper

import android.app.Application
import android.graphics.Color
import android.os.Build
import com.facebook.stetho.Stetho
import com.zone.adapter3kt.QuickConfig
import com.zone.adapter3kt.loadmore.LoadingSetting
import zone.com.okhttplib.Config
import zone.com.zrefreshlayout.ZRefreshLayout
import zone.com.zrefreshlayout.footer.MeterialFooter
import zone.com.zrefreshlayout.header.MeterialHead
import zone.com.zrefreshlayout.resistance.DampingHalf

class App : Application() {
    companion object {
        var instance: App? = null
            private set
    }

    override fun onCreate() {
        super.onCreate()
        initRefreshConfig()
        QuickConfig.build().apply {
            loadingSetting = LoadingSetting().apply {
                threshold = 8
            }
            perform()
        }
        Config.getInstance().context = this
        instance = this
        Stetho.initialize(Stetho
                .newInitializerBuilder(this) //                        .enableDumpapp( Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build())
    }

    private fun initRefreshConfig() {
        val colors_red_green_yellow = intArrayOf(Color.parseColor("#ffF44336"), Color.parseColor("#ff4CAF50"), Color.parseColor("#ffFFEB3B"))
        zone.com.zrefreshlayout.Config.build()
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