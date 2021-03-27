package com.henyiwu.henyiwu.sticker.lib

import android.content.Context
import android.graphics.*
import android.util.DisplayMetrics
import android.view.MotionEvent
import com.example.baselib.ScreenUtils
import com.henyiwu.henyiwu.R
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.sqrt

class Sticker(context: Context, bitmap: Bitmap) : BaseSticker(bitmap){

    private var mLastSinglePoint = PointF() // 上一次单指触摸屏幕的坐标
    private var mLastDistanceVector = PointF() // 上一次双指之间的向量
    private var mDistanceVector = PointF() // 当前双指之间的向量
    private var mLastDistance = 0f // 记录上一次双指之间的距离
    private val reverseBtm = BitmapFactory.decodeResource(context.resources, R.mipmap.sticker_reverse)
    private val deleteBtn = BitmapFactory.decodeResource(context.resources, R.mipmap.sticker_close)

    // 记录点坐标，减少onTouch中的对象创建
    private var mFirstPointF = PointF()
    private var mSecondPoint = PointF()

    private var mReverseBound = RectF(
            -reverseBtm.width.toFloat()/2,
            -reverseBtm.height.toFloat()/2,
            reverseBtm.width.toFloat()/2,
            reverseBtm.height.toFloat()/2
    )
    private var mDeleteBound = RectF(
            -deleteBtn.width.toFloat()/2,
            -deleteBtn.height.toFloat()/2,
            deleteBtn.width.toFloat()/2,
            deleteBtn.height.toFloat()/2
    )

    private fun reset() {
        mLastSinglePoint.set(0f, 0f)
        mLastDistanceVector.set(0f, 0f)
        mDistanceVector.set(0f, 0f)
        mLastDistance = 0f
        mode = OperationMode.MODE_NONE
    }

    override fun onTouch(event: MotionEvent?) {
        when(event?.action?.and(MotionEvent.ACTION_MASK)) {
            MotionEvent.ACTION_DOWN -> {
                mode = OperationMode.MODE_SINGLE
                mLastSinglePoint.set(event.x, event.y)
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                if (event.pointerCount == 2) {
                    mode = OperationMode.MODE_MULTIPLE
                    mFirstPointF.set(event.getX(0), event.getY(0))
                    mSecondPoint.set(event.getX(1), event.getY(1))
                    mLastDistanceVector.set(
                            mFirstPointF.x - mSecondPoint.x,
                            mFirstPointF.y - mSecondPoint.y
                    )
                    mLastDistance = calculateDistance(mFirstPointF, mSecondPoint)
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (mode == OperationMode.MODE_SINGLE) {
                    translate(
                            event.x - mLastSinglePoint.x,
                            event.y - mLastSinglePoint.y
                    )
                    mLastSinglePoint.set(event.x, event.y)
                }
                if (mode == OperationMode.MODE_MULTIPLE && event.pointerCount == 2) {
                    mFirstPointF.set(event.getX(0), event.getY(0))
                    mSecondPoint.set(event.getX(1), event.getY(1))
                    val distance = calculateDistance(mFirstPointF, mSecondPoint)
                    val scaleFactor = distance / mLastDistance
                    if (scaleFactor > 1 || !isSizeLimit()) {
                        scale(scaleFactor, scaleFactor)
                    }
                    mLastDistance = distance
                    mDistanceVector.set(mFirstPointF.x - mSecondPoint.x, mFirstPointF.y - mSecondPoint.y)
                    val degrees = calculateDegrees(mLastDistanceVector, mDistanceVector)
                    rotate(degrees)
                    mLastDistanceVector.set(mDistanceVector.x, mDistanceVector.y)
                }
            }
            MotionEvent.ACTION_UP -> {
                reset()
            }
        }
    }

    private fun isSizeLimit(): Boolean {
        val x = abs(mDstPoints[0] - mDstPoints[2])
        val y = abs(mDstPoints[1] - mDstPoints[3])
        return sqrt((x * x + y * y).toDouble()) <= 200
    }

    /**
     * 通过三角函数，由两个矢量计算出旋转角度
     * @param lastVector 上一个矢量
     * @param curVector 当前矢量
     */
    private fun calculateDegrees(lastVector: PointF, curVector: PointF): Float {
        val lastDegree = atan2(lastVector.y.toDouble(), lastVector.x.toDouble())
        val curDegrees = atan2(curVector.y.toDouble(), curVector.x.toDouble())
        return Math.toDegrees((curDegrees - lastDegree)).toFloat()
    }

    /**
     * 计算两点之间的距离
     */
    private fun calculateDistance(firstPointF: PointF, secondPointF: PointF): Float {
        val x = firstPointF.x - secondPointF.x
        val y = firstPointF.y - secondPointF.y
        return sqrt(x * x + y * y.toDouble()).toFloat()
    }

    fun getReverseBmpBound() = mReverseBound

    fun getDeleteBmpBound() = mDeleteBound

    override fun onDraw(canvas: Canvas, paint: Paint) {
        super.onDraw(canvas, paint)
        if (isFocus()) {
            canvas.drawBitmap(
                    reverseBtm,
                    mDstPoints[6] - reverseBtm.width / 2 - mPadding,
                    mDstPoints[7] - reverseBtm.height / 2 - mPadding,
                    paint
            )
            canvas.drawBitmap(
                    deleteBtn,
                    mDstPoints[2] - deleteBtn.width / 2 - mPadding,
                    mDstPoints[3] - deleteBtn.height / 2 - mPadding,
                    paint
            )
        }
    }
}