//package zone.com.sdk;
//
//import java.lang.reflect.InvocationHandler;
//import java.lang.reflect.Proxy;
//import java.util.ArrayList;
//import java.util.List;
//
//import zone.com.sdk.API.gank.api.GankAPI;
//import zone.com.sdk.API.gank.api.GankImpl;
//import zone.com.sdk.API.gank2.api.Gank2API;
//import zone.com.sdk.API.gank2.api.Gank2Impl;
//
//public class Diycode$$Api {
//    static zone.com.sdk.Diycode$$Injector mAPICall;
//
//    public static synchronized zone.com.sdk.Diycode$$Injector getInstance() {
//        if (mAPICall == null) {
//            synchronized (Diycode$$Api.class) {
//                if (mAPICall == null) {
//                    mAPICall = generate();
//                }
//            }
//        }
//        return mAPICall;
//    }
//
//    ;
//    public static List<Diycode.Entity> entityList = new ArrayList<>();
//
//    static {
//        entityList.add(new Diycode.Entity(GankAPI.class, new GankImpl()));
//        entityList.add(new Diycode.Entity(Gank2API.class, new Gank2Impl()));
//    }
//
//    private static zone.com.sdk.Diycode$$Injector generate() {
//        Diycode$$Api aPIController = new Diycode$$Api();
//        InvocationHandler handler = new DyHandler2(aPIController);
//        Class<?>[] interfaces = new Class[entityList.size()];
//        for (int i = 0; i < entityList.size(); i++) {
//            interfaces[i] = entityList.get(i).interfaceClass;
//        }
//        zone.com.sdk.Diycode$$Injector proxy = (zone.com.sdk.Diycode$$Injector) Proxy.newProxyInstance(
//                aPIController.getClass().getClassLoader(),
//                new Class<?>[]{Diycode$$Api.class}, handler);
//        return proxy;
//    }
//}