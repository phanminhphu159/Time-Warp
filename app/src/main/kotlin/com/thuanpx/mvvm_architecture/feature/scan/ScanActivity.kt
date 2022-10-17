package com.thuanpx.mvvm_architecture.feature.scan

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.BounceInterpolator
import android.view.animation.ScaleAnimation
import android.widget.CompoundButton
import com.thuanpx.mvvm_architecture.base.BaseActivity
import com.thuanpx.mvvm_architecture.databinding.ActivityScanBinding
import dagger.hilt.android.AndroidEntryPoint


@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class ScanActivity : BaseActivity<ScanViewModel, ActivityScanBinding>(ScanViewModel::class) {

    private var isVideo = false
    val scaleAnimation = ScaleAnimation(
        0.7f,
        1.0f,
        0.7f,
        1.0f,
        Animation.RELATIVE_TO_SELF,
        0.7f,
        Animation.RELATIVE_TO_SELF,
        0.7f
    )
    val bounceInterpolator = BounceInterpolator()

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityScanBinding {
        return ActivityScanBinding.inflate(inflater)

    }

    override fun initialize() {
        setOnClickListener()
    }

    fun setOnClickListener() {

        scaleAnimation.duration = 500
        scaleAnimation.interpolator = bounceInterpolator

        viewBinding.ibtnTakePhoto.setOnClickListener {
            onScan()

            setVisibleView(viewBinding.cameraView.isScanVideo)


        }

        viewBinding.ibtnChangeCamera.setOnClickListener {
            switchScan()
        }


//        viewBinding.buttonFavorite.setOnClickListener { onChangeCamera(isVideo) }

        viewBinding.buttonFavorite.setOnCheckedChangeListener(object : View.OnClickListener,
            CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                p0?.startAnimation(scaleAnimation)
            }

            override fun onClick(p: View?) {

            }
        })


    }

    fun onScan() {
        viewBinding.cameraView.isScanVideo = !viewBinding.cameraView.isScanVideo
    }

    //
    fun switchScan() {
        if (viewBinding.cameraView.directionScan == Camera2SurfaceView.directionVertical) {
            viewBinding.cameraView.directionScan = Camera2SurfaceView.directionHorizontal
        } else {
            viewBinding.cameraView.directionScan = Camera2SurfaceView.directionVertical
        }
    }

    fun setVisibleView(isShow: Boolean) {
        if (isShow) {
            viewBinding.viewRemoveAds.visibility = View.INVISIBLE
            viewBinding.viewBright.visibility = View.INVISIBLE
            viewBinding.viewTimer.visibility = View.INVISIBLE
            viewBinding.viewLineSpeed.visibility = View.INVISIBLE
            viewBinding.cvScan.visibility = View.INVISIBLE
            viewBinding.lnVideo.visibility = View.INVISIBLE
        } else {
            viewBinding.viewRemoveAds.visibility = View.VISIBLE
            viewBinding.viewBright.visibility = View.VISIBLE
            viewBinding.viewTimer.visibility = View.VISIBLE
            viewBinding.viewLineSpeed.visibility = View.VISIBLE
            viewBinding.cvScan.visibility = View.VISIBLE
            viewBinding.lnVideo.visibility = View.VISIBLE
        }
    }

    fun onChangeCamera(isVideo: Boolean) {
        if (isVideo) {
            viewBinding.ibtnTakePhoto.setBackgroundResource(com.thuanpx.mvvm_architecture.R.drawable.btn_take_video)
//            viewBinding.buttonFavorite.setBackgroundResource(R.drawable.btn_take_photo)
        } else {
            viewBinding.ibtnTakePhoto.setBackgroundResource(com.thuanpx.mvvm_architecture.R.drawable.btn_take_photo)
//            viewBinding.buttonFavorite.setBackgroundResource(R.drawable.btn_take_video)
        }
        this.isVideo = !isVideo

    }

}
