package com.btm.swiftkt.utils

import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.btm.swiftkt.app.MyApplication
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


/**

 * @Auther: hero

 * @datetime: 2020/8/12 22:09

 * @desc:

 * @项目:  SwiftKt

 */
fun Fragment.showToast(content: String): Toast {
    val toast = Toast.makeText(this.activity?.applicationContext, content, Toast.LENGTH_SHORT)
    toast.show()
    return toast
}

fun Fragment.showlongToast(content: String): Toast {
    val toast = Toast.makeText(this.activity?.applicationContext, content, Toast.LENGTH_LONG)
    toast.show()
    return toast
}
fun Context.showToast(content: String): Toast {
    val toast = Toast.makeText(MyApplication.instance(), content, Toast.LENGTH_SHORT)
    toast.show()
    return toast
}
fun Context.showlongToast(content: String): Toast {
    val toast = Toast.makeText(MyApplication.instance(), content, Toast.LENGTH_LONG)
    toast.show()
    return toast
}


fun View.dip2px(dipValue: Float): Int {
    val scale = this.resources.displayMetrics.density
    return (dipValue * scale + 0.5f).toInt()
}

fun View.px2dip(pxValue: Float): Int {
    val scale = this.resources.displayMetrics.density
    return (pxValue / scale + 0.5f).toInt()
}

fun durationFormat(duration: Long?): String {
    val minute = duration!! / 60
    val second = duration % 60
    return if (minute <= 9) {
        if (second <= 9) {
            "0$minute' 0$second''"
        } else {
            "0$minute' $second''"
        }
    } else {
        if (second <= 9) {
            "$minute' 0$second''"
        } else {
            "$minute' $second''"
        }
    }
}


fun TextView.checkPhone(message: String): String? {
    val text = this.text.toString()
    if (text.isBlank() || text.length != 11) {
       MyApplication.instance().showToast(message)
        return null
    }
    return text
}
fun TextView.checkPwd(message: String): String? {
    val text = this.text.toString()
    if (text.isBlank()) {
        MyApplication.instance().showToast(message)
        return null
    }
    return text
}
fun TextView.checkYzm(message: String): String? {
    val text = this.text.toString()
    if (text.isBlank() || text.length != 6) {
        MyApplication.instance().showToast(message)
        return null
    }
    return text
}


/**
 *
 * @param plainText
 * 明文
 * @return 32位密文
 */
fun String.getMD5Str(): String? {
    var reMd5 = String()
    try {
        val md: MessageDigest = MessageDigest.getInstance("MD5")
        md.update(this.toByteArray())
        val b: ByteArray = md.digest()
        var i: Int
        val buf = StringBuffer("")
        for (offset in b.indices) {
            i = b[offset].toInt()
            if (i < 0) i += 256
            if (i < 16) buf.append("0")
            buf.append(Integer.toHexString(i))
        }
        reMd5 = buf.toString()
    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
    }
    return reMd5
}
/**
 * 数据流量格式化
 */
fun Context.dataFormat(total: Long): String {
    var result: String
    var speedReal: Int = (total / (1024)).toInt()
    result = if (speedReal < 512) {
        speedReal.toString() + " KB"
    } else {
        val mSpeed = speedReal / 1024.0
        (Math.round(mSpeed * 100) / 100.0).toString() + " MB"
    }
    return result
}




