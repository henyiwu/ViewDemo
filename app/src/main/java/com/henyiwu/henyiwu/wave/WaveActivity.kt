package com.henyiwu.henyiwu.wave

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.henyiwu.henyiwu.R
import kotlinx.android.synthetic.main.activity_wave.*

class WaveActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wave)
        viewWave.startWaveAnimation()
    }
}