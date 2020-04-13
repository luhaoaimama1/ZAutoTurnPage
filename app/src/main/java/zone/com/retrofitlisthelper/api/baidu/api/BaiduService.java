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

package zone.com.retrofitlisthelper.api.gank.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import zone.com.retrofitlisthelper.api.gank.bean.MeiZiData;

interface GankService {

    //--- Token ------------------------------------------------------------------------------------

    /**
     * 获取 Token (一般在登录时调用)
     *
     * @return Token 实体类
     * 百度这个pageNumber 是开始的偏移数量而不是页数 假如每页30个那么第三页就是这个就是90
     */

    @GET("/search/acjson?tn=resultjson_com&ipn=rj&ct=201326592&fp=result&queryWord=%E7%A7%80%E5%90%89&ie=utf-8&oe=utf-8&st=-1&word=%E7%A7%80%E5%90%89&face=0&istype=2&nc=1&gsm=78")
    Call<MeiZiData> getPics(@Query("rn") String limit, @Query("pn") String pageNumber);

}
