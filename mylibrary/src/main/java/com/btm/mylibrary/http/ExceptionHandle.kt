
import com.google.gson.JsonParseException
import com.orhanobut.logger.Logger

import org.json.JSONException
import retrofit2.HttpException

import java.net.ConnectException

import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException

/**
 * Created by wb on 2018/12/5.
 * desc: 异常处理类
 */

class ExceptionHandle {


    companion object {
         var errorCode = ErrorStatus.UNKNOWN_ERROR
         var errorMsg = "请求失败，请稍后重试"
        fun handleException(e: Throwable): String {
            e.printStackTrace()
            Logger.e(e.fillInStackTrace().stackTrace.iterator().toString())
            if (e is SocketTimeoutException) {//网络超时
                Logger.e("TAG", "网络连接异常: " + e.message)
                errorMsg = "网络连接超时,请稍后重试"
                errorCode = ErrorStatus.NETWORK_ERROR
            } else if (e is ConnectException ) { //均视为网络错误
                Logger.e("TAG", "网络连接异常: " + e.message)
                errorMsg = "网络连接异常,请稍后重试"
                errorCode = ErrorStatus.NETWORK_ERROR
            } else if (e is JsonParseException
                    || e is JSONException
                    || e is ParseException) {   //均视为解析错误
                Logger.e("TAG", "数据解析异常: " + e.message)
                errorMsg = "数据解析异常,请稍后重试"
                errorCode = ErrorStatus.SERVER_ERROR
            } else if (e is ApiException) {//服务器返回的错误信息
                errorMsg = e.message.toString()
                errorCode = ErrorStatus.SERVER_ERROR
            } else if (e is UnknownHostException) {
                Logger.e("TAG", "网络连接异常: " + e.message)
                errorMsg = "网络连接异常,请稍后重试"
                errorCode = ErrorStatus.NETWORK_ERROR
            } else if (e is IllegalArgumentException) {
                errorMsg = "参数错误,请稍后重试"
                errorCode = ErrorStatus.SERVER_ERROR
            } else if(e is InternalError ){
                errorCode = ErrorStatus.API_SINGERROR
                errorMsg = "未知错误..."

            } else if(e is HttpException ){
                errorCode = ErrorStatus.NotFound
                errorMsg = "接口地址错误404"

            } else{//未知错误
                try {
                    Logger.e("TAG", "错误: " + e.message)
                } catch (e1: Exception) {
                    Logger.e("TAG", "未知错误Debug调试 ")
                }
                errorMsg = "${e.message}"
                errorCode = ErrorStatus.UNKNOWN_ERROR
            }
            return errorMsg
        }

    }


}
