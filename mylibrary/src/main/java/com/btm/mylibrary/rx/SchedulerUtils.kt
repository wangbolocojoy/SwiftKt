import com.btm.mylibrary.rx.Schedulers

/**
 * Created by wb on 2018/11/17.
 * desc:
 */

object SchedulerUtils {

    fun <T> ioToMain(): Schedulers.IoMainScheduler<T> {
        return Schedulers.IoMainScheduler()
    }
    fun <String> ioToMains(): Schedulers.SingleMainScheduler<kotlin.String> {
        return Schedulers.SingleMainScheduler()
    }

}
