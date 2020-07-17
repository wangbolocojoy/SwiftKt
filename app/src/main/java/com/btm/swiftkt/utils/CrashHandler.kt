
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.pm.PackageManager.NameNotFoundException
import android.os.Build
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.btm.swiftkt.R
import com.btm.swiftkt.utils.ActivityManager
import com.sun.mail.util.MailSSLSocketFactory

import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
import java.io.*
import java.lang.Thread.UncaughtExceptionHandler
import java.security.GeneralSecurityException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Properties

/**
 *
 *
 * The handler about UncaughtException, will write the error info to file,
 * and send the debug info to email, need the follow permission.
 *
 * "android.permission.WRITE_EXTERNAL_STORAGE"
 *
 * "android.permission.MOUNT_UNMOUNT_FILESYSTEMS"
 *
 * "android.permission.INTERNET"
 *
 *
 */

/**
 * Created by wb
 * on 2017/11/21
 * on CrashHandler
 */
class CrashHandler private constructor() : UncaughtExceptionHandler {

    /**
     * the SSL encryption
     */
    private var MAIL_SMTP_SSL_ENABLE: Boolean = false
    /**
     * whether to open the email debug logs
     */
    private var MAIL_DEBUG: Boolean = false
    /**
     * the authentication is required
     */
    private var MAIL_SMTP_AUTH: Boolean = false
    /**
     * the mail transport protocol
     */
    private var MAIL_TRANSPORT_PROTOCOL: String? = null
    /**
     * the host name
     */
    private var MAILE_HOST: String? = null
    /**
     * the account
     */
    private var MAILE_USER: String? = null
    /**
     * the authorization password
     */
    private var MAILE_PASSWORD: String? = null
    /**
     * the sender
     */
    private var MAILE_FROM: String? = null
    /**
     * the receiver
     */
    private var MAILE_TO: String? = null
    /**
     * the subject of an email
     */
    private var MAILE_SUBBJECT: String? = null

    /**
     * the default handler of UncaughtException
     */
    private var mDefaultHandler: UncaughtExceptionHandler? = null
    /**
     * the file directory path of error information
     */
    private var dirPath: String? = null
    /**
     * the file file name of error information
     */
    private var fileName: String? = null
    /**
     * whether send email when crash
     */
    private var sendEmail: Boolean = false
    /**
     * the error description
     */
    private var errorDesc: String? = null
    /**
     * the context
     */
    private var mContext: Context? = null

    /**
     * get the debug file path
     *
     * @return the debug file path
     */
    val filePath: String?
        get() {
            if (!createDir(dirPath)) {
                return null
            }

            val filePath = dirPath + File.separator + fileName
            return if (!createFile(filePath)) {
                null
            } else filePath
        }

    /**
     * do something about init
     *
     * @param context
     * the context
     */
    fun init(context: Context, dirPath: String, fileName: String, sendEmail: Boolean) {
        this.mContext = context
        this.dirPath = dirPath
        this.fileName = fileName
        this.sendEmail = sendEmail
        /** get the default handler of UncaughtException  */
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        /** set this CrashHandler to the default handler of UncaughtException  */
        Thread.setDefaultUncaughtExceptionHandler(this)

        val rs = context.resources
        MAIL_SMTP_SSL_ENABLE = rs.getBoolean(R.bool.MAIL_SMTP_SSL_ENABLE)
        MAIL_DEBUG = rs.getBoolean(R.bool.MAIL_DEBUG)
        MAIL_SMTP_AUTH = rs.getBoolean(R.bool.MAIL_SMTP_AUTH)
        MAIL_TRANSPORT_PROTOCOL = mContext!!.getString(R.string.MAIL_TRANSPORT_PROTOCOL)
        MAILE_HOST = mContext!!.getString(R.string.MAILE_HOST)
        MAILE_USER = mContext!!.getString(R.string.MAILE_USER)
        MAILE_PASSWORD = mContext!!.getString(R.string.MAILE_PASSWORD)
        MAILE_FROM = mContext!!.getString(R.string.MAILE_FROM)
        MAILE_TO = mContext!!.getString(R.string.MAILE_TO)
        MAILE_SUBBJECT = mContext!!.getString(R.string.MAILE_SUBBJECT)
    }

    /**
     * set the error description
     *
     * @param errorDesc
     * the error description when crash
     */
    fun setErrorDesc(errorDesc: String) {
        this.errorDesc = errorDesc
    }

    /**
     * called when UncaughtException happen
     */
    override fun uncaughtException(thread: Thread, ex: Throwable) {
        if (!handleException(ex) && mDefaultHandler != null) {
            /**
             * If the user does not handle the exception handler to allow the
             * system to handle the default
             */
            mDefaultHandler!!.uncaughtException(thread, ex)
        } else {
            try {
                Thread.sleep(2000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            // exit app
            ActivityManager.getInstance().Exit()
            android.os.Process.killProcess(android.os.Process.myPid())
            System.exit(1)
        }
    }

    /**
     * collecting device parameter information and save error information to
     * file
     *
     * @param ex
     * the throwable
     * @return true:handled; false:not handled
     */
    private fun handleException(ex: Throwable?): Boolean {
        if (ex == null) {
            return false
        }
        /** save error information to file  */
        saveCrashInfo2File(ex)

        try {
            object : Thread() {
                override fun run() {
                    Looper.prepare()
                    Toast.makeText(
                        mContext,
                        if (TextUtils.isEmpty(errorDesc))
                            "很抱歉，程序出现异常，即将退出"
                        else
                            errorDesc, Toast.LENGTH_LONG
                    ).show()
                    Looper.loop()
                }
            }.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return true
    }

    /**
     * save error information to file
     *
     * @param ex
     * the throwable
     * @return the file name
     */
    private fun saveCrashInfo2File(ex: Throwable): String? {

        val sb = StringBuffer()
        val sdf = SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss",
            Locale.getDefault()
        )
        sb.append("DATE=" + sdf.format(Date(System.currentTimeMillis())) + "\n")

        val path = filePath
        Log.i(TAG, "path = " + path!!)
        sb.append("FILE_PATH=$path\n")

        val info = mContext!!.applicationInfo
        val isDebugVersion = info.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
        sb.append("IS_DEBUG_VERSION=$isDebugVersion\n")

        val packageName = mContext!!.packageName
        sb.append("PACKAGE_NAME=$packageName\n")
        sb.append("APP_NAME=" + mContext!!.getString(R.string.app_name) + "\n")

        try {
            val pm = mContext!!.packageManager
            val pi = pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
            if (pi != null) {
                sb.append("VERSION_NAME=" + pi.versionName + "\n")
                sb.append("VERSION_CODE=" + pi.versionCode + "\n")
            }
        } catch (e: NameNotFoundException) {
            Log.e(TAG, "an error occured when collect package info", e)
        }

        val fields = Build::class.java.declaredFields
        for (field in fields) {
            try {
                field.isAccessible = true
                sb.append(field.name + "=" + field.get(null)!!.toString() + "\n")
                Log.e(TAG, field.name + " : " + field.get(null))
            } catch (e: Exception) {
                Log.e(TAG, "an error occured when collect crash info", e)
            }

        }

        val writer = StringWriter()
        val printWriter = PrintWriter(writer)
        ex.printStackTrace(printWriter)
        var cause: Throwable? = ex.cause
        while (cause != null) {
            cause.printStackTrace(printWriter)
            cause = cause.cause
        }
        printWriter.close()
        val result = writer.toString()
        sb.append(result)
        Log.e(TAG, result, ex)

        val fos: FileOutputStream
        try {
            fos = FileOutputStream(path)
            fos.write(sb.toString().toByteArray())
            fos.close()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "error when write file.", e)
        }

        if (sendEmail) {
            object : Thread() {
                override fun run() {
                    Log.i(TAG, "begain send email, MAILE_TO = " + MAILE_TO!!)
                    try {
                        if (sendEmail(sb.toString())) {
                            Log.d(TAG, "send email success")
                        } else {
                            Log.d(TAG, "send email failed")
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, e.message, e)
                    }

                }
            }.start()
        }
        return path
    }

    /**
     * create directory
     *
     * @param dirPath
     * the directory path
     * @return true success, false failed
     */
    private fun createDir(dirPath: String?): Boolean {
        if (TextUtils.isEmpty(dirPath)) {
            return false
        }
        val file = File(dirPath!!)
        return if (!file.exists()) {
            file.mkdirs()
        } else true
    }

    /**
     * create file
     *
     * @param filePath
     * the file path
     * @return true success, false failed
     */
    private fun createFile(filePath: String): Boolean {
        if (TextUtils.isEmpty(filePath)) {
            return false
        }
        val file = File(filePath)
        if (!file.exists()) {
            try {
                return file.createNewFile()
            } catch (e: IOException) {
                Log.e(TAG, "filePath$filePath", e)
                return false
            }

        }
        return true
    }

    /**
     * send debug info to email
     *
     * @param debugInfo
     * the debug info
     * @return true send success
     */
    private fun sendEmail(debugInfo: String): Boolean {
        try {
            val props = Properties()
            props["mail.debug"] = MAIL_DEBUG
            props["mail.smtp.auth"] = MAIL_SMTP_AUTH
            props["mail.transport.protocol"] = MAIL_TRANSPORT_PROTOCOL
            try {
                val sf = MailSSLSocketFactory()
                sf.isTrustAllHosts = true
                props["mail.smtp.ssl.enable"] = MAIL_SMTP_SSL_ENABLE
                props["mail.smtp.ssl.socketFactory"] = sf
            } catch (e: GeneralSecurityException) {
                Log.e(TAG, e.message, e)
                return false
            }

            val session = Session.getInstance(props)
            val msg = MimeMessage(session)
            msg.subject = MAILE_SUBBJECT + "(" + Build.MANUFACTURER + "-" + Build.MODEL + ")"
            msg.setText(debugInfo)
            msg.setFrom(InternetAddress(MAILE_FROM!!))

            val transport = session.transport
            transport.connect(MAILE_HOST, MAILE_USER, MAILE_PASSWORD)
            transport.sendMessage(
                msg, arrayOf<Address>(
                    InternetAddress(
                        MAILE_TO!!
                    )
                )
            )
            transport.close()
        } catch (e: MessagingException) {
            Log.e(TAG, e.message, e)
            return false
        }

        return true
    }

    companion object {

        /**
         * the log tag
         */
        private val TAG = "CrashHandler"


        private var instance: CrashHandler? = null
        fun getInstance(): CrashHandler {
            if (instance == null)
                instance = CrashHandler()
            return instance as CrashHandler
        }
    }
}
