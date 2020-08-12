package com.btm.swiftkt.utils

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.orhanobut.logger.Logger

/**

 * @Auther: hero

 * @datetime: 2020/8/12 21:40

 * @desc:

 * @项目:  SwiftKt

 */
object GsonUtil {
    private var gson: Gson? = null
    private const val TAG = "GsonUtil"
    init {
        if (gson == null) {
            gson = Gson()
        }
    }

    /**
     * 对象转成json
     *
     * @param object
     * @return json
     */
    @Throws(JsonSyntaxException::class)
    fun gsonString(`object`: Any): String? {
        var gsonString: String? = null
        if (gson != null) {
            try {
                gsonString = gson!!.toJson(`object`)
            }catch (e: JsonSyntaxException){
                Logger.e(TAG,e.message)
            }

        }
        return gsonString
    }

    /**
     * Json转成对象
     *
     * @param gsonString
     * @param cls
     * @return 对象
     */
    @Throws(JsonSyntaxException::class)
    fun <T> gsonToBean(gsonString: String, cls: Class<T>): T?   {
        var t: T? = null
        if (gson != null) {
            try {
                t = gson!!.fromJson(gsonString, cls)
            }catch (e: JsonSyntaxException){
                Logger.e(TAG,"解析异常"+e.toString())
            }

        }
        return t
    }

    /**
     * json转成list<T>
     *
     * @param gsonString
     * @param cls
     * @return list<T>
    </T></T> */
    @Throws(JsonSyntaxException::class)
    fun <T> gsonToList(gsonString: String): List<T>? {
        var list: List<T>? = null
        if (gson != null) {
            try {
                list = gson!!.fromJson<List<T>>(gsonString, object : TypeToken<List<T>>() {

                }.type)
            }catch (e: JsonSyntaxException){
                Logger.e(TAG,e.message)
            }

        }
        return list
    }

    /**
     * json转成list中有map的
     *
     * @param gsonString
     * @return List<Map></Map><String></String>, T>>
     */
    @Throws(JsonSyntaxException::class)
    fun <T> gsonToListMaps(gsonString: String): List<Map<String, T>>? {
        var list: List<Map<String, T>>? = null
        if (gson != null) {
            try {
                list = gson!!.fromJson<List<Map<String, T>>>(gsonString, object : TypeToken<List<Map<String, T>>>() {

                }.type)

            }catch (e: JsonSyntaxException){
                Logger.e(TAG,e.message)
            }

        }
        return list
    }

    /**
     * json转成map的
     *
     * @param gsonString
     * @return Map<String></String>, T>
     */
    @Throws(JsonSyntaxException::class)
    fun <T> gsonToMaps(gsonString: String): Map<String, T>? {
        var map: Map<String, T>? = null
        if (gson != null) {
            try {
                map = gson!!.fromJson<Map<String, T>>(gsonString, object : TypeToken<Map<String, T>>() {

                }.type)

            }catch (e: JsonSyntaxException){
                Logger.e(TAG,e.message)
            }

        }
        return map
    }

}
