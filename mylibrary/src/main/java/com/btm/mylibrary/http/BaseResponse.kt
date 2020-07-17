
/**
 * Created by wb on 2018/11/16.
 * 封装返回的数据
 */
class BaseResponse<T>(val code :Int,
                      val msg:String,
                      val data:T)