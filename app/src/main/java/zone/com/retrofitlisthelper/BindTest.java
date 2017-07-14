package zone.com.retrofitlisthelper;

import android.view.View;
import android.widget.PopupWindow;
import java.lang.reflect.Proxy;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zone.com.retrofit.retrofit.BaseImpl;
import zone.com.retrofit.retrofit.CallWrapper;
import zone.com.retrofitlisthelper.gank.GankAPI;
import zone.com.retrofitlisthelper.gank.GankImpl;

/**
 * [2017] by Zone
 *
 * 测试各种泛型
 */

public class BindTest {
    Call call;
    private Callback callBack;

    public <T> BindTest bindEngine(Call<T> call) {
        this.call = call;
        callBack = new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                T body = response.body();
                System.out.println(body);
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                System.out.println();
            }
        };
        return this;
    }

    public void bindEngine2() {
        call.enqueue(callBack);
    }

    public <T> void enqueue(Call<T> call, Callback<T> callBack) {
        callBack = new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, retrofit2.Response<T> response) {
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
            }
        };
        call.enqueue(callBack);
    }

    public static BindTest a() {
        return new BindTest();

    }

    public static void main(String[] args) {

//        new GankImpl()
//                .getPics("10", "2")
//                .enqueue(new Callback<MeiZiData>() {
//                    @Override
//                    public void onResponse(Call<MeiZiData> call, Response<MeiZiData> response) {
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<MeiZiData> call, Throwable t) {
//
//                    }
//                }, new CallWrapper.OnLoadingListener() {
//                    @Override
//                    public void isLoading(boolean isLoading) {
//
//                    }
//                });

    }


}
