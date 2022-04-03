package com.henyiwu.customview.sticker.lib

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class StickerLayout : View, View.OnTouchListener{

    private lateinit var mPaint: Paint
    private var mStickerList: ArrayList<Sticker> = arrayListOf()
    private var mSticker: Sticker? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        setOnTouchListener(this)
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when(event?.action?.and(MotionEvent.ACTION_MASK)) {
            MotionEvent.ACTION_POINTER_DOWN, MotionEvent.ACTION_DOWN -> {
                mSticker = getReverseButton(event.x, event.y)
                if (mSticker != null && mSticker?.isFocus() == true) {
                    mSticker?.reverse()
                    mSticker = null
                }
                mSticker = getDeleteBtn(event.x, event.y)
                if (mSticker != null && mSticker?.isFocus() == true) {
                    mStickerList.remove(mSticker!!)
                    mSticker?.delete()
                    mSticker = null
                }
                mSticker = getSticker(event.x, event.y)
                // 处理双指触摸屏幕，但第一指没有触摸到贴纸，第二指触摸到贴纸的情况
                if (mSticker == null) {
                    if (event.pointerCount == 2) {
                        mSticker = getSticker(event.getX(1), event.getY(1))
                    }
                }
                // 设置为焦点图片
                if (mSticker != null) {
                    setFocusSticker(mSticker)
                }
            }
        }
        if (mSticker != null) {
            mSticker?.onTouch(event)
        } else {
            clearAllFocus()
        }
        invalidate()
        return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        var focusSticker: Sticker? = null
        for (sticker in mStickerList) {
            if (sticker.isFocus()) {
                focusSticker = sticker
            } else {
                sticker.onDraw(canvas, getPaint())
            }
        }
        focusSticker?.let {
            focusSticker.onDraw(canvas, getPaint())
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec))
        val childWidth = measuredWidth
        val heightSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY)
        val widthSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY)
        super.onMeasure(widthSpec, heightSpec)
    }

    fun addSticker(sticker: Sticker) {
        addSticker(sticker, true)
    }

    /**
     * @param centerInLayout 添加的图片放在布局中间
     */
    fun addSticker(sticker: Sticker, centerInLayout: Boolean = true) {
        mStickerList.add(sticker)
        setFocusSticker(sticker)
        if (centerInLayout) {
            translateSticker(sticker)
            scaleSticker(sticker)
        }
        invalidate()
    }

    /**
     * 初始化图片时候平移到父布局中间
     */
    private fun translateSticker(sticker: Sticker) {
        val bmpWidth = sticker.bitmap.width
        val bmpHeight = sticker.bitmap.height
        val layoutWidth = this.width
        val layoutHeight = this.height
        val tranX: Float = (layoutWidth - bmpWidth) / 2.toFloat()
        val tranY: Float = (layoutHeight - bmpHeight) / 2.toFloat()
        sticker.translate(tranX, tranY)
    }

    private fun scaleSticker(sticker: Sticker) {
        if (sticker.bitmap.height > sticker.bitmap.width) {
            val btmHeight = sticker.bitmap.height
            val layoutHeight = this.height.toFloat()
            val targetHeight = layoutHeight * 3 / 4
            val rate: Float = targetHeight / btmHeight
            sticker.scale(rate, rate)
        } else {
            val btmWidth = sticker.bitmap.width.toFloat()
            val layoutWidth = this.width.toFloat()
            val targetWidth = layoutWidth * 3 / 4
            val rate: Float = targetWidth / btmWidth
            sticker.scale(rate, rate)
        }
    }

    private fun setFocusSticker(targetSticker: Sticker?) {
        for (sticker in mStickerList) {
            if (sticker == targetSticker) {
                sticker.setFocus(true)
            } else {
                sticker.setFocus(false)
            }
        }
    }

    private fun clearAllFocus() {
        for (sticker in mStickerList) {
            sticker.setFocus(false)
        }
    }

    private fun getSticker(x: Float, y: Float) : Sticker? {
        val dstPoints = FloatArray(2)
        val srcPoints = floatArrayOf(x, y)
        for (sticker in mStickerList.reversed()) {
            val matrix = Matrix()
            sticker.getMatrix().invert(matrix)
            matrix.mapPoints(dstPoints, srcPoints)
            if (sticker.getBound().contains(dstPoints[0], dstPoints[1])) {
                return sticker
            }
        }
        return null
    }

    /**
     * 手指是否按倒某个图片的反转按钮
     * @return 反转按钮所在的图片
     */
    private fun getReverseButton(x: Float, y: Float): Sticker? {
        for (sticker in mStickerList.reversed()) {
            val matrix = Matrix()
            sticker.getMatrix().invert(matrix)
            val rect = Rect(
                    (x - sticker.getReverseBmpBound().width()).toInt(),
                    (y - sticker.getReverseBmpBound().height()).toInt(),
                    (x + sticker.getReverseBmpBound().width()).toInt(),
                    (y + sticker.getReverseBmpBound().height()).toInt()
            )
            if (rect.contains(sticker.mDstPoints[6].toInt(), sticker.mDstPoints[7].toInt())) {
                return sticker
            }
        }
        return null
    }

    /**
     * 手指是否按到某个图片的删除按钮
     * @return 图片删除按钮
     */
    private fun getDeleteBtn(x: Float, y: Float): Sticker? {
        for (sticker in mStickerList.reversed()) {
            val matrix = Matrix()
            sticker.getMatrix().invert(matrix)
            val rect = Rect(
                    (x - sticker.getDeleteBmpBound().width()).toInt(),
                    (y - sticker.getDeleteBmpBound().height()).toInt(),
                    (x + sticker.getDeleteBmpBound().width()).toInt(),
                    (y + sticker.getDeleteBmpBound().height()).toInt()
            )
            if (rect.contains(sticker.mDstPoints[2].toInt(), sticker.mDstPoints[3].toInt())) {
                return sticker
            }
        }
        return null
    }

    private fun getPaint(): Paint {
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint.color = Color.GRAY
        mPaint.isAntiAlias = true
        mPaint.strokeWidth = 2f
        return mPaint
    }
}