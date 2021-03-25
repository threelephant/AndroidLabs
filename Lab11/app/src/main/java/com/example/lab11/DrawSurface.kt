package com.example.lab11

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DrawSurface : SurfaceView, SurfaceHolder.Callback {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    private var radius: Float = 50f
    private var cx = radius
    private var cy = radius
    private var dx = 10
    private var dy = 10

    private var paint = Paint()
    private lateinit var job: Job

    override fun surfaceChanged(holder: SurfaceHolder, format: Int,
                                width: Int, height: Int) {
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        paint.color = Color.WHITE
        job = GlobalScope.launch {
            var canvas: Canvas?
            while (true) {
                canvas = holder.lockCanvas(null)
                if (canvas != null) {
                    canvas.drawColor(Color.argb(255, 0, 192, 192))
                    canvas.drawCircle(cx, cy, radius, paint)
                    holder.unlockCanvasAndPost(canvas)
                }

                cx += dx
                cy += dy
                if (cx > width - radius || cx < radius) {
                    dx = -dx
                }
                if (cy > height - radius || cy < radius) {
                    dy = -dy
                }
            }
        }
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        job.cancel()
    }

    init {
        holder.addCallback(this)
    }
}