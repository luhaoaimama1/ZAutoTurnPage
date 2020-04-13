# ZAutoTurnPage
#### [中文版文档](./README-cn.md)
# ZAutoTurnPage

An automatic page-turning library that can change adapter and refresh the loading frame at will

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

*  Reference address
    * implementation 'com.github.luhaoaimama1.ZAutoTurnPage:autoturnpage:[Latest release](https://github.com/luhaoaimama1/ZAutoTurnPage/releases)'

    * implementation 'com.github.luhaoaimama1.ZAutoTurnPage:zrefreshTurnPage:[Latest release](https://github.com/luhaoaimama1/ZAutoTurnPage/releases)'

# Function Description

## autoturnpage

 An abstract class that turns pages is used to wrap the binding of abstract adapter refresh box frame data.

>  A network request is retrofit by default

why adapter, refresh box frame abstracted? Regular opening of new app, means refreshing or adapter the framework may change. Then inherit this class to implement the corresponding function can be replaced

## zrefreshTurnPage

 This is a complete implementation. Both adapter and refresh the framework with my own

# Easy use:

 Automatic completion of drop-down refresh and pull-up loading

```
   zonePullView = object : ZonePullView<MeiZiData>(refresh, adapter!!) {
            override fun request(offset: OffsetWarpper, limit: Int): Call<MeiZiData> {
                // What to request for a page This changes itself
                return BaiduImpl().getPics(limit.toString(), (offset.get() * limit).toString())
            }

            override fun handleData(offset: OffsetWarpper, body: MeiZiData?) {
                //Used to process returned data
                this@ListActivity.adapter?.add(data)
                setTurnPageTv(offset, data.size)
            }
        }
```