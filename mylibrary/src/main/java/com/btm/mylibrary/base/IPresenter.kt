
import IBaseView


/**
 * @author wb
 * created: 2018/10/25
 * desc: Presenter 基类
 */


interface IPresenter<in V: IBaseView> {

    fun attachView(mRootView: V)

    fun detachView()

}
