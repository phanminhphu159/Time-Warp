package com.thuanpx.mvvm_architecture.feature.scan

import android.annotation.SuppressLint
import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import com.skydoves.bundler.intentOf
import com.thuanpx.mvvm_architecture.base.BaseActivity
import com.thuanpx.mvvm_architecture.databinding.ActivitySplashBinding
import com.thuanpx.mvvm_architecture.databinding.FragmentScanBinding
import com.thuanpx.mvvm_architecture.feature.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by ThuanPx on 16/09/2021.
 */
@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class ScanActivity : BaseActivity<ScanViewModel, FragmentScanBinding>(ScanViewModel::class) {
    private var cameraView: Camera2SurfaceView? = null

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentScanBinding {
        return FragmentScanBinding.inflate(inflater)
    }

    override fun initialize() {
        cameraView = viewBinding.cameraView;
        viewBinding.btnScan.setOnClickListener { onScan() }
    }

    fun onScan(){
        cameraView?.isScanVideo  = !cameraView?.isScanVideo!!
        if (cameraView!!.isScanVideo) {
            viewBinding.btnScan.text = "Stop"
        } else {
            viewBinding.btnScan.text = "Scan"
        }
    }
}
