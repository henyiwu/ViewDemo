package com.henyiwu.henyiwu.barrage.model

import android.view.View
import android.widget.TextView
import com.henyiwu.henyiwu.R
import com.henyiwu.henyiwu.barrage.adapter.BarrageAdapter

class PopupBarrageViewHolder(private val root: View) : BarrageAdapter.BarrageViewHolder<BarrageData>(root){
    override fun onBind(data: BarrageData?) {
        root.findViewById<TextView>(R.id.tvText).also {
            it.text = data?.nickName
        }
    }
}