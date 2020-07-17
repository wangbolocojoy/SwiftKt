import com.btm.mylibrary.view.tab.listener.CustomTabEntity


class TabEntity(var title: String, private var selectedIcon: Int, private var unSelectedIcon: Int

) :
    CustomTabEntity {

    override val tabTitle = title

    override val tabSelectedIcon = selectedIcon
    override val tabUnselectedIcon: Int
        get() = unSelectedIcon

}