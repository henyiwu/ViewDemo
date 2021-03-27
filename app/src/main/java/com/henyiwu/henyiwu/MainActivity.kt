package com.henyiwu.henyiwu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import com.henyiwu.henyiwu.sticker.StickerActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initSticker()
    }

    private fun initSticker() {
        val button = Button(this)
        button.text = "贴纸"
        button.gravity = Gravity.CENTER
        button.setOnClickListener {
            startActivity(Intent(this, StickerActivity::class.java))
        }
        ll_root.addView(button)
    }
}
