# RetrofitListHelper

# Usage

### JicPack
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
Step 2. Add the dependency
* 总览
    * compile 'com.github.luhaoaimama1.RetrofitListHelper:retrofitLib:[Latest release](https://github.com/luhaoaimama1/ZAdapter3/releases)'

    * compile 'com.github.luhaoaimama1.RetrofitListHelper:retrofitHelper:[Latest release](https://github.com/luhaoaimama1/ZAdapter3/releases)'

    * compile 'com.github.luhaoaimama1.RetrofitListHelper:zhelper:[Latest release](https://github.com/luhaoaimama1/ZAdapter3/releases)'

> 注意:zhelper依赖retrofitHelper,retrofitHelper依赖retrofitLib ,所以你选一个即可!

# 功能介绍

## retrofitLib

-[x] 网络请求库 支持上传下载 https cookies等;
-[x] 支持rxjava2
-[x] 可以与dialog pop view等进行请求关联
-[x] firstLoad那种 网络状态关联;

## retrofitHelper

一个下拉刷新与上拉加载自动完成抽象库；

## zhelper

retrofitHelper的库的具体实现

*   下拉刷新上拉加载采用:ZRefresh
*   adapter采用:ZAdapter3


# Easy use:

使用之前配置
```
 Config.getInstance().setContext(this);
```

1.pop关联范例:

```
   Diycode.getInstance()
                 .getPics("5", "5")
                 .popWindow(new LoadingPopWindow(this))
                 .delayDismiss(5000)
                 .enqueue(new Callback<MeiZiData>() {
                     @Override
                     public void onResponse(Call<MeiZiData> call, Response<MeiZiData> response) {
                         //UI线程
                         System.out.println("pop==>onResponse");
                     }

                     @Override
                     public void onFailure(Call<MeiZiData> call, Throwable t) {
                         System.out.println("pop==>onFailure");
                     }
                 });
```

2.rxjava2支持
```
  Diycode.getInstance()
                .getPics("5", "2",2)
                .popWindow(new LoadingPopWindow(this))
                .delayDismiss(5000)//想让进度条显示的久一点
                .enqueueObservable()
                .subscribe(o -> System.out.println("Sync 妹子==>：" + GsonUtils.toJson(o))
                        , throwable -> System.out.println("Sync 异常==>" + throwable)
                        , () -> System.out.println("Sync 成功==>"));
```

3.firstLoad状态与界面关联
```
    Diycode.getInstance()
                .getPics("5", "5")
                .firstLoading(LoadingLayout.wrap(llRoot))
                .enqueue(new Callback<MeiZiData>() {
                    @Override
                    public void onResponse(Call<MeiZiData> call, Response<MeiZiData> response) {
                        System.out.println("onFristLoadingClick==>onResponse");
                    }

                    @Override
                    public void onFailure(Call<MeiZiData> call, Throwable t) {
                        System.out.println("onFristLoadingClick==>onFailure");
                    }
                });
```

4.下拉刷新与上拉加载的自动完成
```

        final ZonePullView zonePullView = new ZonePullView<MeiZiData>(refresh, adapter) {
            @NonNull
            @Override
            protected Call<MeiZiData> request(int offset, int limit) {
                return Diycode.getInstance()
                        .getPics(offset + "", limit + "");
            }

            @Override
            protected void handleData(int offset, MeiZiData body) {
                datas.addAll(body.getResults());
            }
        };
```


# Update log

>由于每个版本更新的东西较多，所以从现在开始每个版本都会贴上更新日志.

## 1.0.2

  * 1.初始完成


# Reference&Thanks：
https://github.com/GcsSloop/diycode