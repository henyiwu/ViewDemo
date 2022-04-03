package com.henyiwu.henyiwu.barrage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.henyiwu.henyiwu.R
import com.henyiwu.henyiwu.barrage.adapter.BarrageAdapter
import com.henyiwu.henyiwu.barrage.model.BarrageData
import com.henyiwu.henyiwu.barrage.model.PopupBarrageViewHolder
import com.henyiwu.henyiwu.barrage.ui.BarrageView

class BarrageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barrage)
        val sourceData = arrayListOf<BarrageData>()
        for (i in 0..20) {
            sourceData.add(BarrageData("哈哈${i}"))
        }
        findViewById<BarrageView>(R.id.barrageView).also {
            it.postDelayed({
                val option = BarrageView.Options()
                    .setGravity(BarrageView.MODEL_RANDOM)
                    .setInterval(100)
                    .setSpeed(200, 29)
                    .setModel(BarrageView.MODEL_COLLISION_DETECTION)
                    .setRepeat(-1)
                    .setClick(false)
                val adapter = object : BarrageAdapter<BarrageData>(null, this) {
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
                it.setOptions(option)
                it.setAdapter(adapter)
            }, 1000)
        }
    }
}