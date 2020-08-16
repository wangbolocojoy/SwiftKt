package com.btm.swiftkt.ui.ac

import android.Manifest
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import com.btm.swiftkt.R
import com.btm.swiftkt.base.BaseActivity
import com.btm.swiftkt.utils.*
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_splash.*
import pub.devrel.easypermissions.EasyPermissions

class SplashActivity : BaseActivity() {

    private val ANIMATION_DURATION = 1000
    private val SCALE_END = 1.0f
    private val SCALE_STR = 1.1f
    private var needlock by Preference(Constants.NEEDLOCKPASS, "false")
    private var publicKey by Preference(Constants.KEY_PUBLIC_KEY, "")
    private var privatekey by Preference(Constants.KEY_PRIVATE_KEY, "")
    private var isBind by Preference(Constants.ISBINDID, false)
    private var devicecode by Preference(Constants.DEVICECODE, "")
    private var user by Preference(Constants.USER, "")
    private var token by Preference(Constants.TOKEN, "")

    /**
     *  加载布局
     */
    override fun layoutId() = R.layout.activity_splash

    /**
     * 初始化数据
     */
    override fun initData() {

    }

    /**
     * 初始化 View
     */
    override fun initView() {

    }

    /**
     * 开始请求
     */
    override fun start() {
        checkkey()
        checkPermission()

    }
    private fun checkkey() {
        if (publicKey.isNullOrEmpty()){

            val RSA = KeyUtils.generateKeyPair()
            val puk = RSA.publicKey
            val prk = RSA.privateKey

            publicKey = puk
            privatekey = prk
            Logger.d("初始化生成公私钥")
            Logger.d("publicKey"+publicKey)
            Logger.d("privatekey"+privatekey)

        }else{

            Logger.d("已经有了publicKey"+publicKey)
            Logger.d("已经有了privatekey"+privatekey)
        }

        if (devicecode.isNullOrEmpty()){
            val s = DeviceHelper.getDeviceId(this)
            Logger.d("生成新的devicecode"+s)
            devicecode = s
        }else{
            Logger.d("已经有了devicecode",devicecode)
        }

    }
    /**
     * 6.0以下版本(系统自动申请) 不会弹框
     * 有些厂商修改了6.0系统申请机制，他们修改成系统自动申请权限了
     */
    private fun checkPermission() {
        val perms = arrayOf(
            Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        )
        EasyPermissions.requestPermissions(this, "是否允许ID管家获取权限", 0, *perms)

    }

    private fun animateImage() {
        val animatorX = ObjectAnimator.ofFloat(imageView8, "scaleX", SCALE_STR, SCALE_END)
        val animatorY = ObjectAnimator.ofFloat(imageView8, "scaleY", SCALE_STR, SCALE_END)
        val set = AnimatorSet()
        set.setDuration(ANIMATION_DURATION.toLong()).play(animatorY).with(animatorX)
        set.start()
        set.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                redirectTo()
            }
        })
    }

    fun redirectTo() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        if (requestCode == 0) {
            if (perms.isNotEmpty()) {
                if (perms.contains(Manifest.permission.READ_PHONE_STATE)
                    && perms.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    && perms.contains(Manifest.permission.CAMERA)
                ) {
                    animateImage()
                } else {
                    Logger.d("没有获取权限")
                    showToast("获取权限失败，请手动到设置里打开权限")
                }
            }
        }
    }
}