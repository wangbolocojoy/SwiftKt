
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


}