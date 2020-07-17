
import android.content.res.Resources

/**
 * Created by wb on 2018/12/5.
 * desc:
 */
object ErrorStatus {
    /**
     * 响应成功
     */
    @JvmField
    val SUCCESS = 0

    /**
     * 未知错误
     */
    @JvmField
    val UNKNOWN_ERROR = 1002

    /**
     * 服务器内部错误
     */
    @JvmField
    val SERVER_ERROR = 1003

    /**
     * 请求错误
     */
    @JvmField
    val NETWORKERROR = 400
    /**
     * 请求错误
     */
    @JvmField
    val NotFound = 404
    /**
     * 网络连接超时
     */
    @JvmField
    val NETWORK_ERROR = 1004
    /**
     * API解析异常（或者第三方数据结构更改）等其他异常
     */
    @JvmField
    val API_ERROR = 1005

    /**
     * API解析异常（或者第三方数据结构更改）等其他异常
     */
    @JvmField
    val API_SINGERROR = 500

}