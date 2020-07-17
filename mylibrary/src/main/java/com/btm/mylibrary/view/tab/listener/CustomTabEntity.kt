package com.btm.mylibrary.view.tab.listener

import androidx.annotation.DrawableRes

/**
 * @author hero
 */
interface CustomTabEntity {
    val tabTitle: String?

    @get:DrawableRes
    val tabSelectedIcon: Int

    @get:DrawableRes
    val tabUnselectedIcon: Int
}