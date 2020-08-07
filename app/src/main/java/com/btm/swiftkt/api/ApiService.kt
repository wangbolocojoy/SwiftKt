
import com.btm.swiftkt.bean.LoginModel
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.*

/**
 * Created by wb on 2018/11/16.
 * Api 接口
 */

interface ApiService {

    /**
     * 登录
     */
    @POST("/swiftTemplate/User/login")
    fun login(@Body requestBody: RequestBody): Observable<BaseResponse<LoginModel>>


    /**
     * 注册
     */
    @POST("/swiftTemplate/User/register")
    fun register(@Body requestBody: RequestBody): Observable<BaseResponse<LoginModel>>

    /**
     * 发消息
     */
    @POST("/swiftTemplate/User/sendMsg")
    fun sendMsg(@Body requestBody: RequestBody): Observable<BaseResponse<LoginModel>>

    /**
     * 首页
     */
    @POST("/myApplication/cas/getPageNovelList")
    fun tabHome(@Body requestBody: RequestBody): Observable<BaseResponse<LoginModel>>

    /**
     * 上传头像
     */
    @POST("/swiftTemplate/User/uploadusericon")
    fun updateUserIcon(@Body requestBody: RequestBody): Observable<BaseResponse<LoginModel>>

    /**
     * 更新用户信息
     */
    @POST("/swiftTemplate/User/updateUser")
    fun updateUserInfo(@Body requestBody: RequestBody): Observable<BaseResponse<LoginModel>>

    /**
     * 关注用户
     */
    @POST("/swiftTemplate/Follow/followuser")
    fun followUser(@Body requestBody: RequestBody): Observable<BaseResponse<LoginModel>>

    /**
     * 取消关注
     */
    @POST("/swiftTemplate/Follow/unfollowuser")
    fun unFollowUser(@Body requestBody: RequestBody): Observable<BaseResponse<LoginModel>>

    /**
     * 获取关注列表
     */
    @POST("/swiftTemplate/Follow/getfollowlist")
    fun getFollowList(@Body requestBody: RequestBody): Observable<BaseResponse<LoginModel>>

    /**
     * 获取粉丝列表
     */
    @POST("/swiftTemplate/Follow/getfancelist")
    fun getFanceList(@Body requestBody: RequestBody): Observable<BaseResponse<LoginModel>>

    /**
     * 查找用户
     */
    @POST("/swiftTemplate/User/searchfollow")
    fun searchUser(@Body requestBody: RequestBody): Observable<BaseResponse<LoginModel>>



}