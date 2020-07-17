package com.ztsr.housekeeper.utils

import android.os.Environment

/**
 * @Auther: hero
 * @datetime: 2019-06-25 20:05
 * @desc:
 * @项目: com.btm.swiftkt.app.MyApplication
 */
object FileUtils {
    /**
     * whether has SDcard
     * @return true had
     */
    fun hasSdcard(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

}
