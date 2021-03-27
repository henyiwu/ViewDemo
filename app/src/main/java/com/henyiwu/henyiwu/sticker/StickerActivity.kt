package com.henyiwu.henyiwu.sticker

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.henyiwu.henyiwu.R
import com.henyiwu.henyiwu.sticker.lib.Sticker
import kotlinx.android.synthetic.main.activity_sticker.*

class StickerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sticker)
        Handler(Looper.getMainLooper()).post {
            val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.wzp)
            val sticker = Sticker(this, bitmap)
            sticker_layout.addSticker(sticker)
        }
    }
}