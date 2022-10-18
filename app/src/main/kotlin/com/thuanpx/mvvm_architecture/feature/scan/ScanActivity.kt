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

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityScanBinding {
        return ActivityScanBinding.inflate(inflater)

    }

    override fun initialize() {
        setOnClickListener()
    }

    fun setOnClickListener() {
        viewBinding.ibtnTakePhoto.setOnClickListener {
            onScan()
            setVisibleView(viewBinding.cameraView.isScanVideo)
        }

        viewBinding.ibtnChangeCamera.setOnClickListener {
            switchScan()
        }

        viewBinding.ibtnTakeVideo.setOnCheckedChangeListener(object : View.OnClickListener,
            CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
            }

            override fun onClick(p: View?) {

            }
        })
    }

    fun onScan() {
        viewBinding.cameraView.isScanVideo = !viewBinding.cameraView.isScanVideo
    }

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
        } else {
            viewBinding.ibtnTakePhoto.setBackgroundResource(com.thuanpx.mvvm_architecture.R.drawable.btn_take_photo)
        }
        this.isVideo = !isVideo

    }
}
