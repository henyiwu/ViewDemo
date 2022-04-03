package com.henyiwu.customview.wave

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.baselib.ext.binding
import com.henyiwu.customview.R
import com.henyiwu.customview.databinding.ActivityWaveBinding
import kotlinx.android.synthetic.main.activity_wave.*

class WaveActivity : AppCompatActivity() {

    val binding: ActivityWaveBinding by binding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wave)
        binding.viewWave.startWaveAnimation()
    }
}