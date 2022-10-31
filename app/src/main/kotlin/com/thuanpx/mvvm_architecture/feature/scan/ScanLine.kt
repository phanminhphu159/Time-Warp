package com.thuanpx.mvvm_architecture.feature.scan

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.view.View

class ScanLine internal constructor(context: Context?) : View(context) {
    private val paint: Paint = Paint()
    var coordX : Float? = null
    var coordY : Float? = null

    fun setCoordinatorVerticalLine(coordinator : Float){
        coordX = null
        coordY = coordinator
    }

    fun setCoordinatorHorizontalLine(coordinator : Float){
        coordY = null
        coordX = coordinator
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
//        paint.setStyle(Paint.Style.STROKE)
        paint.color = Color.BLUE
        paint.strokeWidth = 5F
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)

        if (coordX != null){
            canvas.drawLine(coordX!!, 0F, coordX!!, height.toFloat(), paint)
        }
        if (coordY != null){
            canvas.drawLine(0F, coordY!!, width.toFloat(), coordY!!, paint)
        }
    }
}