package com.thuanpx.mvvm_architecture.feature.scan

import android.opengl.EGL14
import android.opengl.EGLConfig
import android.opengl.EGLContext
import android.opengl.EGLDisplay
import android.view.Surface

class EGLUtils {
    private var eglSurface = EGL14.EGL_NO_SURFACE
    var context = EGL14.EGL_NO_CONTEXT
        private set
    private var eglDis = EGL14.EGL_NO_DISPLAY
    @JvmOverloads
    fun initEGL(surface: Surface?, eglContext: EGLContext? = EGL14.EGL_NO_CONTEXT) {
        eglDis = EGL14.eglGetDisplay(EGL14.EGL_DEFAULT_DISPLAY)
        val version = IntArray(2)
        EGL14.eglInitialize(eglDis, version, 0, version, 1)
        val confAttr = intArrayOf(
            EGL14.EGL_SURFACE_TYPE, EGL14.EGL_WINDOW_BIT,
            EGL14.EGL_RED_SIZE, 8,
            EGL14.EGL_GREEN_SIZE, 8,
            EGL14.EGL_BLUE_SIZE, 8,
            EGL14.EGL_ALPHA_SIZE, 8,
            EGL14.EGL_RENDERABLE_TYPE, EGL14.EGL_OPENGL_ES2_BIT,
            EGL_RECORDABLE_ANDROID, 1,
            EGL14.EGL_SAMPLE_BUFFERS, 1,
            EGL14.EGL_SAMPLES, 4,
            EGL14.EGL_NONE
        )
        configs = arrayOfNulls(1)
        val numConfigs = IntArray(1)
        EGL14.eglChooseConfig(eglDis, confAttr, 0, configs, 0, 1, numConfigs, 0)
        val ctxAttr = intArrayOf(
            EGL14.EGL_CONTEXT_CLIENT_VERSION, 2,  // 0x3098
            EGL14.EGL_NONE
        )
        context = EGL14.eglCreateContext(eglDis, configs[0], eglContext, ctxAttr, 0)
        createSurface(eglDis, configs[0], surface)
    }

    private lateinit var configs: Array<EGLConfig?>
    fun createSurface(
        dpy: EGLDisplay?, config: EGLConfig?,
        win: Any?
    ) {
        if (eglSurface !== EGL14.EGL_NO_SURFACE) {
            EGL14.eglDestroySurface(eglDis, eglSurface)
            eglSurface = EGL14.EGL_NO_SURFACE
        }
        val surfaceAttr = intArrayOf(
            EGL14.EGL_NONE
        )
        EGL14.eglSwapInterval(eglDis, 0)
        eglSurface = EGL14.eglCreateWindowSurface(dpy, config, win, surfaceAttr, 0)
        EGL14.eglMakeCurrent(eglDis, eglSurface, eglSurface, context)
    }

    fun changedSurface(win: Any?) {
        createSurface(eglDis, configs[0], win)
    }

    fun swap() {
        EGL14.eglSwapBuffers(eglDis, eglSurface)
    }

    fun release() {
        if (eglSurface !== EGL14.EGL_NO_SURFACE) {
            EGL14.eglMakeCurrent(
                eglDis,
                EGL14.EGL_NO_SURFACE,
                EGL14.EGL_NO_SURFACE,
                EGL14.EGL_NO_CONTEXT
            )
            EGL14.eglDestroySurface(eglDis, eglSurface)
            eglSurface = EGL14.EGL_NO_SURFACE
        }
        if (context !== EGL14.EGL_NO_CONTEXT) {
            EGL14.eglDestroyContext(eglDis, context)
            context = EGL14.EGL_NO_CONTEXT
        }
        if (eglDis !== EGL14.EGL_NO_DISPLAY) {
            EGL14.eglTerminate(eglDis)
            eglDis = EGL14.EGL_NO_DISPLAY
        }
    }

    companion object {
        private const val EGL_RECORDABLE_ANDROID = 0x3142
    }
}
