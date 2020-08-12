
import com.btm.swiftkt.bean.HomeModel
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
    @POST("/back-1/swiftTemplate/User/login")
    fun login(@Body requestBody: RequestBody): Observable<BaseResponse<LoginModel>>


    /**
     * 注册
     */
    @POST("/back-1/swiftTemplate/User/register")
    fun register(@Body requestBody: RequestBody): Observable<BaseResponse<LoginModel>>

    /**
     * 修改密码
     */
    @POST("/back-1/swiftTemplate/User/register")
    fun changePwd(@Body requestBody: RequestBody): Observable<BaseResponse<LoginModel>>

    /**
     * 发消息
     */
    @POST("/back-1/swiftTemplate/User/sendMsg")
    fun sendMsg(@Body requestBody: RequestBody): Observable<BaseResponse<LoginModel>>

    /**
     * 首页
     */
    @POST("/back-1/swiftTemplate/Post/getPosts")
    fun tabHome(@Body requestBody: RequestBody): Observable<HomeModel>

    /**
     * 上传头像
     */
    @POST("/back-1/swiftTemplate/User/uploadusericon")
    fun updateUserIcon(@Body requestBody: RequestBody): Observable<BaseResponse<LoginModel>>

    /**
     * 更新用户信息
     */
    @POST("/back-1/swiftTemplate/User/updateUser")
    fun updateUserInfo(@Body requestBody: RequestBody): Observable<BaseResponse<LoginModel>>

    /**
     * 关注用户
     */
    @POST("/back-1/swiftTemplate/Follow/followuser")
    fun followUser(@Body requestBody: RequestBody): Observable<BaseResponse<LoginModel>>

    /**
     * 取消关注
     */
    @POST("/back-1/swiftTemplate/Follow/unfollowuser")
    fun unFollowUser(@Body requestBody: RequestBody): Observable<BaseResponse<LoginModel>>

    /**
     * 获取关注列表
     */
    @POST("/back-1/swiftTemplate/Follow/getfollowlist")
    fun getFollowList(@Body requestBody: RequestBody): Observable<BaseResponse<LoginModel>>

    /**
     * 获取粉丝列表
     */
    @POST("/back-1/swiftTemplate/Follow/getfancelist")
    fun getFanceList(@Body requestBody: RequestBody): Observable<BaseResponse<LoginModel>>

    /**
     * 查找用户
     */
    @POST("/back-1/swiftTemplate/User/searchfollow")
    fun searchUser(@Body requestBody: RequestBody): Observable<BaseResponse<LoginModel>>



}