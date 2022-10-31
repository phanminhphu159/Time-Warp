package com.thuanpx.mvvm_architecture.feature.scan

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.widget.CompoundButton
import com.thuanpx.ktext.context.launchAndRepeatWithViewLifecycle
import com.thuanpx.mvvm_architecture.base.BaseActivity
import com.thuanpx.mvvm_architecture.databinding.ActivityScanBinding
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class ScanActivity : BaseActivity<ScanViewModel, ActivityScanBinding>(ScanViewModel::class) {

    private var scanLine: ScanLine? = null

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityScanBinding {
        return ActivityScanBinding.inflate(inflater)
    }

    override fun initialize() {
        scanLine = ScanLine(this)
        viewBinding.btnTakePhoto.setOnClickListener { onScan() }
        viewBinding.btnChangeCamera.setOnClickListener { switchScan() }
        setOnClickListener()
    }

    private fun setOnClickListener() {
        viewBinding.btnTakePhoto.setOnClickListener {
            onScan()
            setVisibleView(viewBinding.cameraView.isScanVideo)
        }

        viewBinding.btnChangeCamera.setOnClickListener {
            switchScan()
        }

        viewBinding.btnTakeVideo.setOnCheckedChangeListener(object : View.OnClickListener,
            CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
            }

            override fun onClick(p: View?) {

            }
        })
    }

    override fun onSubscribeObserver() {
        launchAndRepeatWithViewLifecycle {
            with(viewBinding.cameraView) {
                coordinator.observe(this@ScanActivity) {
                    viewBinding.clCameraView.removeView(scanLine)
                    viewBinding.clCameraView.addView(
                        scanLine
                    )
                    if (viewBinding.cameraView.directionScan == Camera2SurfaceView.directionVertical) {
                        scanLine!!.setCoordinatorVerticalLine(it)
                    } else {
                        scanLine!!.setCoordinatorHorizontalLine(it)
                    }
                }
            }
        }

        super.onSubscribeObserver()
    }

    private fun onScan() {
        with (viewBinding.cameraView){
            isScanVideo = !viewBinding.cameraView.isScanVideo
            if (isScanVideo){
                scanLineCoordinator = 0f
                coordinator.postValue(scanLineCoordinator)
            }
        }
    }

    private fun switchScan() {
        if (viewBinding.cameraView.directionScan == Camera2SurfaceView.directionVertical) {
            viewBinding.cameraView.directionScan = Camera2SurfaceView.directionHorizontal
        } else {
            viewBinding.cameraView.directionScan = Camera2SurfaceView.directionVertical
        }
    }

    private fun setVisibleView(isShow: Boolean) {
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
}
