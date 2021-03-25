package com.example.lab11

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DrawMySurface : SurfaceView, SurfaceHolder.Callback {

    companion object {
        private var color: Int = 0
        fun changeColor() {
            if (color == 0) {
                color = 1
            } else if (color == 1) {
                color = 0
            }
        }
    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    private var speed: Float = 10f
    private var radius: Float = 50f
    private var cx = radius
    private var cy = radius
    private var dx = speed
    private var dy = 0f
    private var direction: Direction = Direction.RightTop

    private var paint = Paint()
    private lateinit var job: Job

    override fun surfaceChanged(holder: SurfaceHolder, format: Int,
                                width: Int, height: Int) {    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        job = GlobalScope.launch {
            var canvas: Canvas?
            while (true) {
                canvas = holder.lockCanvas(null)
                if (canvas != null) {
                    canvas.drawColor(Color.WHITE)
                    paint.color = if (color == 0) Color.MAGENTA else Color.CYAN
                    canvas.drawCircle(cx, cy, radius, paint)
                    holder.unlockCanvasAndPost(canvas)
                }

                cx += dx
                cy += dy

                if (cx + radius > width && direction == Direction.RightTop) {
                    dx = -speed
                    dy = speed
                    direction = Direction.DownLeft
                } else if (cx - radius < 0 && direction == Direction.DownLeft) {
                    dx = speed
                    dy = 0f
                    direction = Direction.RightBottom
                } else if (cx + radius > width && direction == Direction.RightBottom) {
                    dx = -speed
                    dy = -speed
                    direction = Direction.UpLeft
                } else if (cx - radius < 0 && direction == Direction.UpLeft) {
                    dx = speed
                    dy = 0f
                    direction = Direction.RightTop
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