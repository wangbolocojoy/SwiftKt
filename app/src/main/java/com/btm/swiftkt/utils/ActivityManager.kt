package com.btm.swiftkt.utils

import android.app.Activity
import android.os.Bundle

import java.util.Stack



class ActivityManager private constructor() {

    private val activityStack: Stack<ActivityData> = Stack()

    /**
     * get current activity
     * @return may be null
     */
    val currentActivity: Activity?
        get() = if (activityStack.empty()) {
            null
        } else activityStack.lastElement().activity

    /**
     * add activity to stack
     * @param activity the activity
     * @param bundle the bundle data
     */
    fun addActivity(activity: Activity, bundle: Bundle, createTime: Long) {
        activityStack.add(ActivityData(activity, bundle, createTime))
    }

    /**
     * exit app and finish all activity
     */
    fun Exit() {
        if (activityStack.size > 0) {
            finishAllActivity()
        }
    }

    /**
     * finish all activity
     */
    fun finishAllActivity() {
        if (activityStack.empty()) {
            return
        }
        for (i in activityStack.indices) {
            val data = activityStack[i]
            data?.finish()
        }
        activityStack.clear()
    }

    /**
     * finish current activity
     * @param createTime the create time of activity
     */
    fun finishActivity(createTime: Long) {
        if (activityStack.empty()) {
            return
        }
        val activity = activityStack.lastElement().activity
        finishActivity(activity, createTime)
    }

    /**
     * finish activity
     * @param cls the activity class
     */
    fun finishActivity(cls: Class<*>) {
        if (activityStack.empty()) {
            return
        }
        for (data in activityStack) {
            if (data.activity!!.javaClass == cls) {
                data.activity!!.finish()
                break
            }
        }
    }

    /**
     * finish the activity
     * @param activity the activity
     * @param createTime the create time of activity
     */
    fun finishActivity(activity: Activity?, createTime: Long): Boolean {
        if (activity != null) {
            removeActivity(activity, createTime)
            activity.finish()
            return true
        }
        return false
    }

    /**
     * finish other activity except this
     * @param activity this activity
     * @param createTime the create time of activity
     */
    fun finishOtherActivity(activity: Activity?, createTime: Long) {
        if (null == activity || activityStack.empty()) {
            return
        }
        var bak: ActivityData? = null
        for (data in activityStack) {
            if (data.activity!!.javaClass != activity.javaClass || createTime != data.createTime) {
                data.activity!!.finish()
            } else {
                bak = data
            }
        }
        activityStack.clear()
        activityStack.add(bak)
    }

    /**
     * remove the activity
     * @param activity the activity
     * @param createTime the create time of activity
     */
    fun removeActivity(activity: Activity?, createTime: Long) {
        if (null == activity || activityStack.empty()) {
            return
        }
        for (i in activityStack.indices) {
            if (null != activityStack[i]
                && null != activityStack[i].activity
                && activityStack[i].activity!!.javaClass.name == activity.javaClass.name
                && createTime == activityStack[i].createTime
            ) {
                activityStack.removeAt(i)
                break
            }
        }
    }

    /**
     * find the activity in activityStack
     * @param activity activity
     * @return ActivityData
     */
    fun findActivityData(activity: Activity?): ActivityData? {
        if (activity == null || activityStack.empty()) {
            return null
        }
        for (i in activityStack.indices) {
            if (null != activityStack[i]
                && null != activityStack[i].activity
                && activityStack[i].activity!!.javaClass.name == activity.javaClass.name
            ) {
                return activityStack[i]
            }
        }
        return null
    }

    class ActivityData(var activity: Activity?, var bundle: Bundle, var createTime: Long) {

        fun finish(): Boolean {
            if (null != activity) {
                activity!!.finish()
                return true
            }
            return false
        }
    }

    companion object {

        private var instance: ActivityManager? = null
        fun getInstance(): ActivityManager {
            if (instance == null) {
                instance = ActivityManager()
            }
            return instance as ActivityManager
        }
    }
}
