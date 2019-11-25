package com.example.timeviewdemo.clockView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import java.util.*

/**
 * Created by anchaoguang on 2019-11-21.
 * 时钟自定义view
 */
class TimeView : View {

    private lateinit var mContext: Context
    private lateinit var mPaint: Paint
    private var mSecond = 0f
    private var mMin = 0f
    private var mHour = 0f

    constructor(context: Context) : super(context) {
        mContext = context
        initPaint()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        initPaint()
    }

    fun getTime(): String {
        val totalSecond: Float = mHour * 120
        val currentHour = (totalSecond / 3600).toInt()
        val currentMin = ((totalSecond - currentHour * 3600) / 60).toInt()
        val currentSecond = ((totalSecond - currentHour * 3600 - currentMin * 60) * 2).toInt()
        return "当前时间为 $currentHour:$currentMin:$currentSecond"
    }

    private fun initPaint() {
        mPaint = Paint()
        // 抗锯齿
        mPaint.isAntiAlias = true
        mPaint.color = Color.BLACK
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = 3f  // 圆边缘线宽
    }

    fun setTimer(hour: Int, min: Int, second: Int) {
        mHour = 30f * hour + min * 0.5f + second * 1 / 240f
        mMin = min * 6f + second * 0.1f
        mSecond = second * 6f
    }

    // 每隔一秒需要做的事情
    private val task = object : TimerTask() {
        override fun run() {
            if (mSecond == 360f) {
                mSecond = 0f
            }
            if (mMin == 360f) {
                mMin = 0f
            }
            if (mHour == 360f) {
                mHour = 0f
            }
            mSecond += 6f
            mMin += 0.1f
            mHour += 1f / 240
            postInvalidate()
        }
    }

    fun startTimer() {
        val timer = Timer()
        // 三个参数分别为 定时器task， 启动多少秒后开始工作， 任务执行的时间间隔
        timer.purge()
        timer.schedule(task, 0, 1000)
    }

    override fun onDraw(canvas: Canvas?) {
        if (canvas == null) {
            return
        }
        // 前两个是坐标，第三个是圆的半径
        canvas.drawCircle(width / 2f, height / 2f, width / 3f, mPaint)
        mPaint.strokeWidth = 10f  // 圆心线宽
        canvas.drawPoint(width / 2f, height / 2f, mPaint)
        mPaint.strokeWidth = 1f  // 刻度线线宽
        // 把坐标系移到圆心(0,0)
        canvas.translate(width / 2f, height / 2f)
        for (i in 1..360) {
            when {
                // 刻度线长度为25
                i % 30 == 0 ->
                    canvas.drawLine(width / 3f - 25, 0f, width / 3f, 0f, mPaint)
                i % 6 == 0 ->
                    canvas.drawLine(width / 3f - 14, 0f, width / 3f, 0f, mPaint)
                else -> canvas.drawLine(width / 3f - 9, 0f, width / 3f, 0f, mPaint)
            }
            canvas.rotate(1f)
        }
        mPaint.textSize = 25f
        mPaint.style = Paint.Style.FILL
        for (i in 0..11) {
            drawNum(canvas, i * 30f, if (i == 0) "12" else i.toString(), mPaint)
        }
        setHands(canvas, mPaint)
    }

    private fun drawNum(canvas: Canvas, degree: Float, text: String, paint: Paint) {
        val textBound = Rect()
        paint.getTextBounds(text, 0, text.length, textBound)
        canvas.rotate(degree)
        canvas.translate(0f, 50f - width / 3f)
        canvas.rotate(-degree)
        canvas.drawText(text, -textBound.width() / 2f, textBound.height() / 2f, paint)
        canvas.rotate(degree)
        canvas.translate(0f, width / 3f - 50f)
        canvas.rotate(-degree)
    }

    // 设置三个时针的指向度数
    private fun setHands(canvas: Canvas, paint: Paint) {
        // 秒针
        canvas.save()
        paint.color = Color.RED
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 2f
        canvas.rotate(mSecond)
        canvas.drawLine(0f, 0f, 0f, -250f, paint)
        canvas.restore()
        // 分针
        canvas.save()
        paint.color = Color.BLUE
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 4f
        canvas.rotate(mMin)
        canvas.drawLine(0f, 0f, 0f, -180f, paint)
        canvas.restore()
        // 时针
        canvas.save()
        paint.color = Color.BLACK
        paint.strokeWidth = 6f
        paint.style = Paint.Style.STROKE
        canvas.rotate(mHour)
        canvas.drawLine(0f, 0f, 0f, -120f, paint)
        canvas.restore()
    }
}