package zone.com.retrofitlisthelper;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.zone.adapter3.QuickRcvAdapter;
import com.zone.adapter3.base.IAdapter;
import com.zone.adapter3.bean.ViewDelegates;
import com.zone.adapter3.helper.Helper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ezy.ui.layout.LoadingLayout;
import retrofit2.Call;
import zone.com.retrofit.base.ZonePullView;
import zone.com.retrofit.utils.HandlerUiUtil;
import zone.com.retrofit.views.LoadingAnimView;
import zone.com.retrofitlisthelper.net.API.gank.api.GankImpl;
import zone.com.retrofitlisthelper.net.API.gank.bean.MeiZiData;
import zone.com.zrefreshlayout.Config;
import zone.com.zrefreshlayout.ZRefreshLayout;
import zone.com.zrefreshlayout.footer.MeterialFooter;
import zone.com.zrefreshlayout.header.MeterialHead;
import zone.com.zrefreshlayout.resistance.DampingHalf;

public class ListActivity extends AppCompatActivity {


    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.refresh)
    ZRefreshLayout refresh;
    @Bind(R.id.loading)
    LoadingAnimView loading;
    @Bind(R.id.ll_root)
    LinearLayout llRoot;
    private List<MeiZiData.ResultsBean> datas = new ArrayList<>();
    private IAdapter<MeiZiData.ResultsBean> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        initRefreshConfig();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
        loading.setNum(12);
        loading.setHostRadius(60);
        loading.setRadius(13);

        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new QuickRcvAdapter<>(this, datas);
        adapter.addViewHolder(new ViewDelegates<MeiZiData.ResultsBean>() {
            @Override
            public int getLayoutId() {
                return R.layout.item;
            }

            @Override
            public void fillData(int postion, MeiZiData.ResultsBean data, Helper helper) {
                Glide.with(context).load(data.getUrl()).into((ImageView) helper.getView(R.id.id_num));
            }

        }).relatedList(rv);


        final ZonePullView zonePullView = new ZonePullView<MeiZiData>(refresh, adapter) {
            @NonNull
            @Override
            protected Call<MeiZiData> request(int offset, int limit) {
                return new GankImpl().getPics(offset + "", limit + "");
            }

            @Override
            protected void handleData(int offset, MeiZiData body) {
                datas.addAll(body.getResults());
            }
        };
        zonePullView.setFirstNumber(5);


        HandlerUiUtil.postDelay(new Runnable() {
            @Override
            public void run() {
//                refresh.autoRefresh(true);
                zonePullView.firstLoading(5, LoadingLayout.wrap(llRoot));
            }
        }, 500);

    }


    private void initRefreshConfig() {
        int[] colors_red_green_yellow = new int[]{
                Color.parseColor("#ffF44336"),
                Color.parseColor("#ff4CAF50"),
                Color.parseColor("#ffFFEB3B")
        };
        Config.build()
                .setHeader(new MeterialHead(colors_red_green_yellow))
                .setPinContent(true)
                .setFooter(new MeterialFooter())
                .setResistance(new DampingHalf())
//                .setHeader(new  CircleRefresh())
//                .setResistance(new Damping2Head8per())
                .writeLog(true)
                .perform();
    }

}
