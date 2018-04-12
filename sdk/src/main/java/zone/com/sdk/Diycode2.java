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
///**
// * [2017] by Zone
// * 这个是通过编译的时候 而不是运行时，所以编译的时候就需要知道， 写入的代码字符串;
// * 通过注解： GankAPI  APIList添加注解 GankImpl 加注解，检测包名相同
// *  APIList完成后 -> APICall
// * GankImpl完成后 -> entityList.add(new Entity(GankAPI.class, new GankImpl()));
// */
//
//public class Diycode2 {
//
//    public static List<Entity> entityList = new ArrayList<>();
//
//    public static class Entity {
//        public final Class interfaceClass;
//        public final Object instance;
//
//        public Entity(Class interfaceClass, Object instance) {
//            this.interfaceClass = interfaceClass;
//            this.instance = instance;
//        }
//    }
//
//
//    static {
//        entityList.add(new Entity(GankAPI.class, new GankImpl()));
//        entityList.add(new Entity(Gank2API.class, new Gank2Impl()));
//    }
//
//    static APICall mAPICall;
//
//    public static synchronized APICall getInstance() {
//
//        if (mAPICall == null) {
//            synchronized (Diycode2.class) {
//                if (mAPICall == null) {
//                    mAPICall = generate();
//                }
//            }
//        }
//        return mAPICall;
//    }
//
//
//    /**
//     * 在这里，生成一个APIcall 动态,通过entityList。
//     * @return
//     */
//    private static APICall generate() {
//        Diycode2 aPIController = new Diycode2();
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
//
//    public static void main(String[] args) {
//
//        Diycode2 aPIController = new Diycode2();
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
//
////        proxy.getPics("5", "5", 2);
////        proxy.getSites()
////                .enqueueObservable()
////                .subscribe(new Consumer<List<Sites>>() {
////                    @Override
////                    public void accept(List<Sites> sites) throws Exception {
////                        System.out.println(sites);
////                    }
////                });
//    }
//
//}
