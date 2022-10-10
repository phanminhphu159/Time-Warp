package com.thuanpx.mvvm_architecture.feature.scan

import android.view.LayoutInflater
import android.view.ViewGroup
import com.thuanpx.mvvm_architecture.base.fragment.BaseFragment
import com.thuanpx.mvvm_architecture.databinding.FragmentScanBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScanFragment : BaseFragment<ScanViewModel, FragmentScanBinding>(ScanViewModel::class) {
    private var cameraView: Camera2SurfaceView? = null

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentScanBinding {
        return FragmentScanBinding.inflate(inflater, container, false)
    }

    override fun initialize() {
        cameraView = viewBinding.cameraView;
        viewBinding.btnScan.setOnClickListener { onScan() }
    }

    override fun onSubscribeObserver() {
        super.onSubscribeObserver()
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
