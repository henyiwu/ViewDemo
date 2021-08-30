package com.henyiwu.henyiwu.wave

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.Transformation

/**
 * 播放按钮波浪背景控件
 * @Author svenj
 * @Date 2020/12/11
 * @Email svenjzm@gmail.com
 */
@SuppressLint("AppCompatCustomView")
class AudioButtonWaveView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var mRectList = arrayListOf<RectF>()

    init {
        for (index in 0..2) {
            mRectList.add(RectF())
        }
    }

    private var progress = 0.0F

    private var mAnimation: Animation? = null

    private val mPaint = Paint().apply {
        color = Color.parseColor("#E284EE")
        alpha = 255
    }

    fun startWaveAnimation() {
        clearAnimation()
        if (mAnimation == null) {
            mAnimation = object : Animation() {
                override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                    setAnimationProgress(interpolatedTime)
                }
            }.apply {
                interpolator = DecelerateInterpolator()
                duration = 1000
                repeatCount = Int.MAX_VALUE
            }
        }

        mAnimation?.let {
            startAnimation(it)
        }
    }

    fun stopWaveAnimation() {
        clearAnimation()
        setAnimationProgress(0.0F)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        updateBounds()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        for (item in mRectList) {
            canvas?.drawRoundRect(item, measuredHeight / 2f,measuredHeight / 2f, mPaint)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        clearAnimation()
    }

    private fun setAnimationProgress(progress: Float) {
        this.progress = progress
        mPaint.alpha = (255 * (1 - progress)).toInt()
        updateBounds()

        invalidate()
    }

    private fun updateBounds() {
        val mMaxRadius = measuredWidth
        val mMinRadius = measuredWidth * 0.9f
        val offset = (mMaxRadius - mMinRadius) * (1 - progress)

        for ((index, item) in mRectList.withIndex()) {
            val distance = index * 8
            item.left = offset + distance
            item.top = offset + distance
            item.right = measuredWidth - offset -  distance
            item.bottom = measuredHeight - offset - distance
        }
    }
}