package com.henyiwu.customview.barrage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.baselib.ext.binding
import com.henyiwu.customview.R
import com.henyiwu.customview.barrage.adapter.BarrageAdapter
import com.henyiwu.customview.barrage.model.BarrageData
import com.henyiwu.customview.barrage.model.PopupBarrageViewHolder
import com.henyiwu.customview.barrage.ui.BarrageView
import com.henyiwu.customview.databinding.ActivityBarrageBinding

class BarrageActivity : AppCompatActivity() {

    val binding: ActivityBarrageBinding by binding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sourceData = arrayListOf<BarrageData>()
        for (i in 0..20) {
            sourceData.add(BarrageData("哈哈${i}"))
        }
        binding.barrageView.apply {
            this.postDelayed({
                val option = BarrageView.Options()
                    .setGravity(BarrageView.MODEL_RANDOM)
                    .setInterval(100)
                    .setSpeed(200, 29)
                    .setModel(BarrageView.MODEL_COLLISION_DETECTION)
                    .setRepeat(-1)
                    .setClick(false)
                val adapter = object : BarrageAdapter<BarrageData>(null, this@BarrageActivity) {
                    override fun onCreateViewHolder(
                        root: View,
                        type: Int
                    ): BarrageViewHolder<BarrageData> {
                        return PopupBarrageViewHolder(root)
                    }

                    override fun getItemLayout(t: BarrageData?): Int {
                        return R.layout.item_barrage
                    }
                }
                adapter.addList(sourceData)
                this.setOptions(option)
                this.setAdapter(adapter)
            }, 1000)
        }
    }
}