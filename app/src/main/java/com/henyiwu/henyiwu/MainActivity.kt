package com.henyiwu.henyiwu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.henyiwu.henyiwu.sticker.StickerActivity
import com.henyiwu.henyiwu.wave.WaveActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initSticker()
    }

    private fun initSticker() {
        findViewById<Button>(R.id.btnSticker).setOnClickListener {
            startActivity(Intent(this, StickerActivity::class.java))
        }
        findViewById<Button>(R.id.btnWave).setOnClickListener {
            startActivity(Intent(this, WaveActivity::class.java))
        }
    }
}
