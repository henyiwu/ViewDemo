package com.henyiwu.customview.sticker

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.baselib.ext.binding
import com.henyiwu.customview.R
import com.henyiwu.customview.databinding.ActivityStickerBinding
import com.henyiwu.customview.sticker.lib.Sticker

class StickerActivity : AppCompatActivity() {

    private val binding: ActivityStickerBinding by binding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding // 初始化一下
        Handler(Looper.getMainLooper()).post {
            val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.asdf)
            val sticker = Sticker(this, bitmap)
            binding.stickerLayout.addSticker(sticker)
        }
    }
}