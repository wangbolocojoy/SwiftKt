
import ApiService
import com.btm.mylibrary.utils.Constants
import com.btm.mylibrary.utils.KeyUtils
import com.btm.swiftkt.app.MyApplication
import com.btm.swiftkt.utils.NetworkUtil
import com.btm.swiftkt.utils.Preference
import com.orhanobut.logger.Logger
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.net.ssl.*
import okhttp3.RequestBody
import okio.Buffer
import java.io.IOException
import java.util.*


/**
 * Created by wb on 2018/11/16.
 *
 */

object RetrofitManager {

    val service: ApiService by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        getRetrofit().create(ApiService::class.java)
    }
    private var isbind by Preference(Constants.ISBINDID, false)
    private val devicecode by Preference(Constants.DEVICECODE, "")
    private var token: String by Preference("token", "")
    private val privatekey: String by Preference(Constants.KEY_PRIVATE_KEY, "")
    /**
     * 动态切换服务器地址
     */
    private fun addQueryParameterInterceptor(): Interceptor {
        var api by Preference(Constants.APISERVICE, "UrlConstant.BASE_URL")
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val request: Request
            var oldUrl = originalRequest.url();
            //获取originalRequest的创建者builder
            var builder = originalRequest.newBuilder();
            //获取头信息的集合如：manage,mdffx
            var baseURL: HttpUrl? = null
            //根据头信息中配置的value,来匹配新的base_url地址
            if (isbind) {
                chain.proceed(builder.url(oldUrl).build())
            } else {
                baseURL = HttpUrl.parse(api)
                if(baseURL!=null){
                    //重建新的HttpUrl，需要重新设置的url部分
                    val newHttpUrl = oldUrl.newBuilder()
                        .scheme(baseURL.scheme())//http协议如：http或者https
                        .host(baseURL.host())//主机地址
                        .port(baseURL.port())//端口
                        .build()
                    //获取处理后的新newRequest
                    val newRequest = builder.url(newHttpUrl).build()
                    chain.proceed(newRequest)
                }else{
                    chain.proceed(builder.url(oldUrl).build())
                }

            }

        }
    }


    /**
     * 设置头
     */
    private fun addHeaderInterceptor(): Interceptor {
        var api by Preference(Constants.APISERVICE, UrlConstant.BASE_URL)
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val time = ""+Date().time
            Logger.i(time)
            val requestBuilder = originalRequest.newBuilder()
                // Provide your custom header here
                .header("Content-type", "application/json")
                .header("devicecode", devicecode)
                .header("deviceType", "android")
                .header("signature", getSignature(originalRequest.body()))
                .header("CurrentTime", time)
                .header("SignatureTime", getSignaTime(time))
                .method(originalRequest.method(), originalRequest.body())
            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }


    private fun getSignature(request: RequestBody?): String {
        return KeyUtils.sign(bodyToString(request), privatekey)
    }
    private fun getSignaTime(time:String):String{
        return KeyUtils.sign(time, privatekey)
    }


    private fun bodyToString(request: RequestBody?): String {
        try {
            val buffer = Buffer()
            if (request != null)
                request.writeTo(buffer)
            else
                return ""
            return buffer.readUtf8()
        } catch (e: IOException) {
            return "获取参数失败"
        }

    }

    /**
     * 设置缓存
     */
    private fun addCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()
            if (!NetworkUtil.isNetworkAvailable(MyApplication.instance())) {
                request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build()
            }
            val response = chain.proceed(request)
            if (NetworkUtil.isNetworkAvailable(MyApplication.instance())) {
                val maxAge = 0
                // 有网络时 设置缓存超时时间0个小时 ,意思就是不读取缓存数据,只对get有用,post没有缓冲
                response.newBuilder()
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .removeHeader("Retrofit")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .build()
            } else {
                // 无网络时，设置超时为4周  只对get有用,post没有缓存
                val maxStale = 60 * 60 * 24 * 28
                response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .removeHeader("nyn")
                    .build()
            }
            response
        }
    }

    fun getRetrofit(): Retrofit {
        // 获取retrofit的实例
        val api by Preference(Constants.APISERVICE, UrlConstant.BASE_URL)
        return Retrofit.Builder()
            .baseUrl(api)  //自己配置
            .client(getOkHttpClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }


    public //信任所有服务器地址
    //设置为true
    //创建管理器
    //为OkHttpClient设置sslSocketFactory
    val httpsClient: OkHttpClient
        get() {
            val okhttpClient = OkHttpClient().newBuilder()
            okhttpClient.hostnameVerifier { s, sslSession -> true }
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                @Throws(java.security.cert.CertificateException::class)
                override fun checkClientTrusted(
                    x509Certificates: Array<java.security.cert.X509Certificate>,
                    s: String
                ) {
                }

                @Throws(java.security.cert.CertificateException::class)
                override fun checkServerTrusted(
                    x509Certificates: Array<java.security.cert.X509Certificate>,
                    s: String
                ) {
                }

                override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                    return arrayOf()
                }
            })
            try {
                val sslContext = SSLContext.getInstance("TLS")
                sslContext.init(null, trustAllCerts, java.security.SecureRandom())
                okhttpClient.sslSocketFactory(sslContext.socketFactory)

            } catch (e: Exception) {
                e.printStackTrace()
            }

            return okhttpClient.build()
        }

    private fun getOkHttpClient(): OkHttpClient {
        //添加一个log拦截器,打印所有的log
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        //可以设置请求过滤的水平,body,basic,headers
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        //设置 请求的缓存的大小跟位置
        val cacheFile = File(MyApplication.instance().cacheDir, "cache")
        val cache = Cache(cacheFile, 1024 * 1024 * 50) //50Mb 缓存的大小

        return OkHttpClient.Builder()
            .hostnameVerifier(object : HostnameVerifier {
                override fun verify(p0: String?, p1: SSLSession?): Boolean {
                    return true
                }

            })
            .sslSocketFactory(httpsClient.sslSocketFactory())

            .addInterceptor(addQueryParameterInterceptor())  //参数添加
            .addInterceptor(addHeaderInterceptor()) // token过滤
            .addInterceptor(httpLoggingInterceptor) //日志,所有的请求响应度看到
            .cache(cache)  //添加缓存
            .connectTimeout(15L, TimeUnit.SECONDS)
            .readTimeout(15L, TimeUnit.SECONDS)
            .writeTimeout(15L, TimeUnit.SECONDS)
            .build()
    }


}
