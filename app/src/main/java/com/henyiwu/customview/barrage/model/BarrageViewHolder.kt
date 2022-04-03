package com.henyiwu.customview.barrage.model

import android.view.View
import android.widget.TextView
import com.henyiwu.customview.R
import com.henyiwu.customview.barrage.adapter.BarrageAdapter

class PopupBarrageViewHolder(private val root: View) : BarrageAdapter.BarrageViewHolder<BarrageData>(root){
    override fun onBind(data: BarrageData?) {
        root.findViewById<TextView>(R.id.tvText).also {
            it.text = data?.nickName
        }
    }
}