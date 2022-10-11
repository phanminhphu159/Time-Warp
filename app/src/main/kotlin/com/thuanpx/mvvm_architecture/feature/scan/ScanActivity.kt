package com.thuanpx.mvvm_architecture.feature.scan

import android.annotation.SuppressLint
import android.view.LayoutInflater
import com.thuanpx.mvvm_architecture.base.BaseActivity
import com.thuanpx.mvvm_architecture.databinding.FragmentScanBinding
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class ScanActivity : BaseActivity<ScanViewModel, FragmentScanBinding>(ScanViewModel::class) {

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentScanBinding {
        return FragmentScanBinding.inflate(inflater)
    }

    override fun initialize() {
        viewBinding.ivTakePhoto.setOnClickListener { onScan() }
    }

    override fun onSubscribeObserver() {
        super.onSubscribeObserver()
    }

    fun onScan(){
        viewBinding.cameraView.isScanVideo  = viewBinding.cameraView.isScanVideo
    }
}
