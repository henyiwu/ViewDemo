package com.henyiwu.henyiwu.barrage.model

class BarrageData(val nickName: String) : DataSource {
    override fun getType(): Int {
        return 1
    }
}