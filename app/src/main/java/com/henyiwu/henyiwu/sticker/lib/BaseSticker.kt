package com.henyiwu.henyiwu.sticker.lib

import android.graphics.*
import androidx.annotation.IntDef
import com.example.baselib.utils.LogUtils

abstract class BaseSticker(var bitmap: Bitmap) : IOperation{

    private var mMatrix: Matrix = Matrix()
    protected val mPadding = 5
    private var isFocus: Boolean = false
    private var mMidPointF: PointF = PointF()
    // 矩阵变换前的坐标
    private var mSrcPoints = floatArrayOf(
        // 左上角坐标
        0f, 0f,
        // 右上角坐标
        bitmap.width.toFloat(), 0f,
        // 右下角坐标
        bitmap.width.toFloat(), bitmap.height.toFloat(),
        // 左下角坐标
        0f, bitmap.height.toFloat(),
        // 中间点坐标
        bitmap.width.toFloat() / 2, bitmap.height.toFloat() / 2
    )
    // 矩阵变换后的坐标
    var mDstPoints = mSrcPoints.clone()
    // 图片的矩形占位
    private var mBitmapBound = RectF(0f, 0f, bitmap.width.toFloat(), bitmap.height.toFloat())
    protected var mode: Int = OperationMode.MODE_NONE

    fun setFocus(isFocus: Boolean) {
        this.isFocus = isFocus
    }

    fun isFocus() = isFocus

    override fun translate(dx: Float, dy: Float) {
        mMatrix.postTranslate(dx, dy)
        LogUtils.debugLog("translate dx = $dx, dy = $dy")
        updatePoints()
    }

    override fun scale(sx: Float, sy: Float) {
        LogUtils.debugLog("translate dx = $sx, dy = $sy")
        mMatrix.postScale(sx, sy, mMidPointF.x, mMidPointF.y)
        updatePoints()
    }

    override fun rotate(degree: Float) {
        LogUtils.debugLog("rotate degree = $degree")
        mMatrix.postRotate(degree, mMidPointF.x, mMidPointF.y)
        updatePoints()
    }

    override fun reverse() {
        LogUtils.debugLog("reverse")
        mMatrix.preScale(-1f, 1f)
        mMatrix.preTranslate(-bitmap.width.toFloat(), 0f)
        updatePoints()
    }

    override fun delete() {
        bitmap.recycle()
    }

    override fun onDraw(canvas: Canvas, paint: Paint) {
        canvas.drawBitmap(bitmap, mMatrix, paint)
        paint.color = Color.YELLOW
        if (isFocus) {
            //绘制贴纸边框
            canvas.drawLine(
                mDstPoints[0] - mPadding,
                mDstPoints[1] - mPadding,
                mDstPoints[2] + mPadding,
                mDstPoints[3] - mPadding,
                paint
            )
            canvas.drawLine(
                mDstPoints[2] + mPadding,
                mDstPoints[3] - mPadding,
                mDstPoints[4] + mPadding,
                mDstPoints[5] + mPadding,
                paint
            )
            canvas.drawLine(
                mDstPoints[4] + mPadding,
                mDstPoints[5] + mPadding,
                mDstPoints[6] - mPadding,
                mDstPoints[7] + mPadding,
                paint
            )
            canvas.drawLine(
                mDstPoints[6] - mPadding,
                mDstPoints[7] + mPadding,
                mDstPoints[0] - mPadding,
                mDstPoints[1] - mPadding,
                paint
            )
        }
    }

    private fun updatePoints() {
        mMatrix.mapPoints(mDstPoints, mSrcPoints)
        mMidPointF.set(mDstPoints[8], mDstPoints[9])
    }

    fun getMatrix() = mMatrix

    fun getBound() = mBitmapBound

    @Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FIELD, AnnotationTarget.FUNCTION)
    @IntDef(
        OperationMode.MODE_NONE,
        OperationMode.MODE_SINGLE,
        OperationMode.MODE_MULTIPLE
    )
    annotation class OperationMode {
        companion object {
            const val MODE_NONE = 0 // 初始状态
            const val MODE_SINGLE = 1 // 单指操作
            const val MODE_MULTIPLE = 2 // 多指操作
        }
    }
}