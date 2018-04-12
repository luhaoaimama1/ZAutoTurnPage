//package zone.com.sdk;
//
//import java.lang.reflect.InvocationHandler;
//import java.lang.reflect.Proxy;
//import java.util.ArrayList;
//import java.util.List;
//import zone.com.sdk.API.gank.api.GankAPI;
//import zone.com.sdk.API.gank.api.GankImpl;
//import zone.com.sdk.API.gank2.api.Gank2API;
//import zone.com.sdk.API.gank2.api.Gank2Impl;
//
//public class DiycodeInner {
//
//    static APICall mAPICall;
//
//    public static synchronized APICall getInstance() {
//
//        if (mAPICall == null) {
//            synchronized (DiycodeInner.class) {
//                if (mAPICall == null) {
//                    mAPICall = generate();
//                }
//            }
//        }
//        return mAPICall;
//    }
//
//
//    public static List<Diycode.Entity> entityList = new ArrayList<>();
//
//
//    static {
//        entityList.add(new Diycode.Entity(GankAPI.class, new GankImpl()));
//        entityList.add(new Diycode.Entity(Gank2API.class, new Gank2Impl()));
//    }
//
//
//    /**
//     * 在这里，生成一个APIcall 动态,通过entityList。
//     *
//     * @return
//     */
//    private static APICall generate() {
//        DiycodeInner aPIController = new DiycodeInner();
//        InvocationHandler handler = new DyHandler(aPIController);
//
//        Class<?>[] interfaces = new Class[entityList.size()];
//        for (int i = 0; i < entityList.size(); i++) {
//            interfaces[i] = entityList.get(i).interfaceClass;
//        }
//        //创建动态代理对象
//        APICall proxy = (APICall) Proxy.newProxyInstance(
//                aPIController.getClass().getClassLoader(),
//                new Class<?>[]{APICall.class}, handler);
//        return proxy;
//    }
//
//}
