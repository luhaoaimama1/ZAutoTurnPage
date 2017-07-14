package zone.com.retrofitlisthelper;

import zone.com.retrofit.retrofit.CallWrapper;
import zone.com.retrofitlisthelper.gank.GankAPI;
import zone.com.retrofitlisthelper.gank.GankImpl;

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
    public CallWrapper<MeiZiData> getPics(String limit, String pageNumber) {
        return mGankImpl.getPics(limit, pageNumber);
    }
}
