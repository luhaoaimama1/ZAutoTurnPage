package zone.com.retrofitlisthelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ezy.ui.layout.LoadingLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zone.com.retrofit.retrofit.CallWrapper;
import zone.com.retrofit.utils.HandlerUiUtil;
import zone.com.retrofit.views.LoadingDialog;
import zone.com.retrofit.views.LoadingPopWindow;
import zone.com.retrofitlisthelper.gank.GankImpl;

public class MainActivity extends AppCompatActivity {


    @Bind(R.id.ll_root)
    LinearLayout llRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.bt_pop)
    public void onPopClick() {
        new GankImpl()
                .getPics("5", "5")
                .popWindow(new LoadingPopWindow(this))
                .delay(5000)
                .enqueue(new Callback<MeiZiData>() {
                    @Override
                    public void onResponse(Call<MeiZiData> call, Response<MeiZiData> response) {

                    }

                    @Override
                    public void onFailure(Call<MeiZiData> call, Throwable t) {

                    }
                });
    }

    @OnClick(R.id.bt_Dlg)
    public void onDlgClick() {
        new GankImpl()
                .getPics("5", "5")
                .dialog(new LoadingDialog(this))
                .enqueue(new Callback<MeiZiData>() {
                    @Override
                    public void onResponse(Call<MeiZiData> call, Response<MeiZiData> response) {

                    }

                    @Override
                    public void onFailure(Call<MeiZiData> call, Throwable t) {

                    }
                });
    }

    @OnClick(R.id.bt_DelayDialog)
    public void onDelayDialogClick() {
        final LoadingDialog dialog = new LoadingDialog(this);
        new GankImpl()
                .getPics("5", "5")
                .OnLoadingListener(new CallWrapper.OnLoadingListener() {
                    @Override
                    public void onLoading(CallWrapper.State state) {
                        switch (state) {
                            case Loading:
                                dialog.show();
                                break;
                            case Success:
                            case Error:
                                HandlerUiUtil.runOnUiThreadDelay(new Runnable() {
                                    @Override
                                    public void run() {
                                        dialog.dismiss();
                                    }
                                }, 2000);
                                break;
                        }
                    }
                })
                .enqueue(new Callback<MeiZiData>() {
                    @Override
                    public void onResponse(Call<MeiZiData> call, Response<MeiZiData> response) {

                    }

                    @Override
                    public void onFailure(Call<MeiZiData> call, Throwable t) {

                    }
                });
    }

    @OnClick(R.id.bt_FristLoading)
    public void onFristLoadingClick() {
        new GankImpl()
                .getPics("5", "5")
                .firstLoading(LoadingLayout.wrap(llRoot))
                .enqueue(new Callback<MeiZiData>() {
                    @Override
                    public void onResponse(Call<MeiZiData> call, Response<MeiZiData> response) {

                    }

                    @Override
                    public void onFailure(Call<MeiZiData> call, Throwable t) {

                    }
                });
    }

    @OnClick(R.id.bt_List)
    public void onListClick() {
        startActivity(new Intent(this, ListActivity.class));

    }
}
