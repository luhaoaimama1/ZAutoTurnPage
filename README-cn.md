# ZAutoTurnPage

一个可以随意更换 adapter与刷新加载框架的自动翻页库

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
    * compile 'com.github.luhaoaimama1.ZAutoTurnPage:autoturnpage:[Latest release](https://github.com/luhaoaimama1/ZAutoTurnPage/releases)'

    * compile 'com.github.luhaoaimama1.ZAutoTurnPage:zrefreshTurnPage:[Latest release](https://github.com/luhaoaimama1/ZAutoTurnPage/releases)'

# 功能介绍

## autoturnpage

翻页的抽象类 用于包装抽象的 adapter 刷新框框架 数据的绑定。

> 网络请求默认用retrofit了

为什么adapter，刷新框框架 抽象出来？ 如果经常开新的app,意味着刷新或者adapter框架可能会改变。那么继承这个类 实现对应的功能即可更换

## zrefreshTurnPage

这个是一个完整的实现。adapter和刷新框架都是用我自己的

# Easy use:


下拉刷新与上拉加载的自动完成

```
   zonePullView = object : ZonePullView<MeiZiData>(refresh, adapter!!) {
            override fun request(offset: OffsetWarpper, limit: Int): Call<MeiZiData> {
                //对应某个页面用什么请求 这个可以自己更改
                return BaiduImpl().getPics(limit.toString(), (offset.get() * limit).toString())
            }

            override fun handleData(offset: OffsetWarpper, body: MeiZiData?) {
                //用于处理 返回回来的数据
                this@ListActivity.adapter?.add(data)
                setTurnPageTv(offset, data.size)
            }
        }
```