package com.btm.swiftkt.app
import CrashHandler
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.os.Environment
import android.util.Log
import com.btm.swiftkt.BuildConfig
import com.btm.swiftkt.utils.AppUtils
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.ztsr.housekeeper.utils.FileUtils
import java.io.File
import kotlin.properties.Delegates
class MyApplication : Application(){
    private val TAG = "com.btm.swiftkt.app.MyApplication"
    companion object{
        private var instance: MyApplication by Delegates.notNull()
        fun instance() = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initConfig()
        registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks)
        Logger.d(TAG,"初始化MyApplication")
        initCrashHandler()
    }
    /**
     * 初始化配置
     */
    private fun initConfig() {

        val formatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(true)  // 隐藏线程信息 默认：显示
            .methodCount(2)         // 决定打印多少行（每一行代表一个方法）默认：2
            .methodOffset(3)        // (Optional) Hides internal method calls up to offset. Default 5
            .tag("MyApp")
            .build()
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }


    private val mActivityLifecycleCallbacks = object : ActivityLifecycleCallbacks {
        @SuppressLint("LongLogTag")
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            Log.i(TAG, "onCreated: " + activity.componentName.className)
        }

        @SuppressLint("LongLogTag")
        override fun onActivityStarted(activity: Activity) {
            Log.i(TAG, "onStart: " + activity.componentName.className)
        }

        @SuppressLint("LongLogTag")
        override fun onActivityResumed(activity: Activity) {
            Log.i(TAG, "onActivityResumed: " + activity.componentName.className)

        }

        @SuppressLint("LongLogTag")
        override fun onActivityPaused(activity: Activity) {
            Log.i(TAG, "onActivityPaused: " + activity.componentName.className)

        }

        @SuppressLint("LongLogTag")
        override fun onActivityStopped(activity: Activity) {
            Log.i(TAG, "onActivityStopped: " + activity.componentName.className)

        }

        @SuppressLint("LongLogTag")
        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            Log.i(TAG, "onActivitySaveInstanceState: " + activity.componentName.className)

        }

        @SuppressLint("LongLogTag")
        override fun onActivityDestroyed(activity: Activity) {
            Log.i(TAG, "onDestroy: " + activity.componentName.className)
        }
    }

    /**
     * 初始话崩溃处理器，输出崩溃日志文件到手机内存，方便手机上查看
     */
    private fun initCrashHandler() {
        val dirPath: String = if (FileUtils.hasSdcard()) {
            (Environment.getExternalStorageDirectory().path
                    + File.separator + packageName)
        } else {
            filesDir.path
        }
        CrashHandler.getInstance().init(
            applicationContext, dirPath,
            "debug.log", !AppUtils.isApkDebugVersion(this)
        )
    }

}