/*
 * Copyright 2017 GcsSloop
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Last modified 2017-03-08 01:01:18
 *
 * GitHub:  https://github.com/GcsSloop
 * Website: http://www.gcssloop.com
 * Weibo:   http://weibo.com/GcsSloop
 */

package zone.com.retrofitlisthelper.api.gank;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import zone.com.okhttplib.BaseOKHttpClient;
import zone.com.okhttplib.Config;
import zone.com.okhttplib.java.cookie.CookieJarImpl;
import zone.com.okhttplib.java.cookie.store.SPCookieStore;

/**
 * 实现类，具体实现在此处
 *
 * @param <Service>
 */
public class BaseImpl<Service> extends BaseOKHttpClient<Service> {


    @Override
    protected void onCreate() {
        if (Config.isAPP) {
            if (context == null) {
                throw new IllegalStateException("Please use method Config.getInstance().setContext(context)");
            }
        }
    }

    @Override
    protected Retrofit initRetrofit() {

        boolean isDebug = Config.getInstance().isDebug();

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(getHttpLoggingInterceptor(isDebug))  // 设置拦截器
                .addNetworkInterceptor(getStethoInterceptor(isDebug)) //stetho
                .retryOnConnectionFailure(true)             // 是否重试
                .connectTimeout(5, TimeUnit.SECONDS)        // 连接超时事件
                .readTimeout(5, TimeUnit.SECONDS)           // 读取超时时间
                .connectTimeout(15, TimeUnit.SECONDS);       // 连接超时时间 默认10秒

        if (context != null) {
            builder.cache(getCache()) //缓存存储目录
                    .cookieJar(new CookieJarImpl(new SPCookieStore(context)));//使用sp保持cookie，如果cookie不过期，则一直有效
//                  .cookieJar(new CookieJarImpl(new MemoryCookieStore()));   //使用内存保持cookie，app退出后，cookie消失
        }

        // 配置 client
        OkHttpClient client = builder.build();
        // 配置 Retrofit
        return new Retrofit.Builder()
                //todo  url debug模式  在之类修改 不能用拦截器修改  因为url不同的话 拦截器会报错
                .baseUrl("http://image.baidu.com")                         // 设置 base url
//                .baseUrl(ConstantURL.BASE_URL)                         // 设置 base url
                .client(client)                                     // 设置 client
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()) // 设置 Json 转换工具
                .build();
    }

}



