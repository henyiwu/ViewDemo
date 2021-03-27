package com.henyiwu.henyiwu.sticker.lib

import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent

/**
 * 规定每个图片的操作
 */
interface IOperation {

    /**
     * 平移
     */
    fun translate(dx: Float, dy: Float)

    /**
     * 缩放
     */
    fun scale(sx: Float, sy: Float)

    /**
     * 旋转
     */
    fun rotate(degree: Float)

    /**
     * 反转
     */
    fun reverse()

    /**
     * 绘制
     */
    fun onDraw(canvas: Canvas, paint: Paint)

    /**
     * 触摸事件
     */
    fun onTouch(event: MotionEvent?)

    /**
     * 删除图片
     */
    fun delete() {

    }
}