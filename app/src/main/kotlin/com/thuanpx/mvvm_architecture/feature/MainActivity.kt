package com.thuanpx.mvvm_architecture.feature

import android.content.Intent
import android.view.LayoutInflater
import com.google.android.material.navigation.NavigationBarView
import com.thuanpx.mvvm_architecture.R
import com.thuanpx.mvvm_architecture.base.BaseActivity
import com.thuanpx.mvvm_architecture.databinding.ActivityMainBinding
import com.thuanpx.mvvm_architecture.feature.home.HomeFragment
import com.thuanpx.mvvm_architecture.feature.scan.ScanActivity
import com.thuanpx.mvvm_architecture.feature.search.SearchFragment
import com.thuanpx.mvvm_architecture.utils.navigation.BottomNavigationManager
import com.thuanpx.mvvm_architecture.utils.navigation.createNavigationManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(MainViewModel::class) {

    companion object {
        const val TAB1 = 0
        const val TAB2 = 1
    }

    private val homeFragment = HomeFragment()
    private val searchFragment = SearchFragment()

    val bottomNavigationManager: BottomNavigationManager by lazy {
        createNavigationManager {
            tabs = listOf(TAB1, TAB2)
            mainFragmentManager = supportFragmentManager
            mainContainerViewId = R.id.flMainContainer
        }
    }

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(inflater)
    }

    override fun onBackPressed() {
        if (bottomNavigationManager.isCanPopBack()) {
            return
        }
        if (bottomNavigationManager.getCurrentTab() != TAB1) {
            viewBinding.abMain.bnvMain.selectedItemId = R.id.tab1
            bottomNavigationManager.switchTab(TAB1, homeFragment)
            return
        }
        finish()
    }

    override fun initialize() {
        setOnClickListen()
        bottomNavigationManager.addOrReplaceFragment(fragment = homeFragment)
        viewBinding.abMain.run {
            bnvMain.selectedItemId = R.id.tab1
            bnvMain.setOnItemSelectedListener(
                NavigationBarView.OnItemSelectedListener { item ->
                    when (item.itemId) {
                        R.id.tab1 -> bottomNavigationManager.switchTab(tab = TAB1, fragment = homeFragment)
                        R.id.tab2 -> bottomNavigationManager.switchTab(tab = TAB2, fragment = searchFragment)
                    }
                    return@OnItemSelectedListener true
                }
            )
        }
    }

    override fun onSubscribeObserver() {
        super.onSubscribeObserver()
    }

    private fun setOnClickListen(){
        viewBinding.abMain.flbtnTakePhoto.setOnClickListener {
            val intent = Intent(this@MainActivity, ScanActivity::class.java)
            startActivity(intent)
        }
    }

}
