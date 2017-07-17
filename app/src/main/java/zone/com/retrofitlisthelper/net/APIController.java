package zone.com.retrofitlisthelper.net;

import zone.com.retrofit.callwrapper.DialogCall;
import zone.com.retrofitlisthelper.net.API.gank.api.GankAPI;
import zone.com.retrofitlisthelper.net.API.gank.api.GankImpl;
import zone.com.retrofitlisthelper.net.API.gank.bean.MeiZiData;

/**
 * [2017] by Zone
 */

public class APIController implements GankAPI {

    private static GankImpl mGankImpl;
    private static APIController instance;

    static {
        instance = new APIController();
        mGankImpl = new GankImpl();
    }

    public static APIController getSingleInstance() {
        return instance;
    }


    @Override
    public DialogCall<MeiZiData> getPics(String limit, String pageNumber) {
        return mGankImpl.getPics(limit, pageNumber);
    }
}
