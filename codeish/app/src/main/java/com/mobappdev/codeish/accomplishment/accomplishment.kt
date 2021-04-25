package com.mobappdev.codeish.accomplishment

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobappdev.codeish.R
import com.mobappdev.codeish.accomplishment.acData.AcList
import com.mobappdev.codeish.accomplishment.acData.Accomplishment
import com.mobappdev.codeish.shop.data.ShopList

class Accomplishments : AppCompatActivity() {

    private lateinit var accomplishmentList: MutableList<Accomplishment>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.accomplishhment)
        accomplishmentList = ArrayList()
        accomplishmentList.add(Accomplishment("Test1", "path"))
        accomplishmentList.add(Accomplishment("Test2", "path"))
        accomplishmentList.add(Accomplishment("Test3", "path"))
        accomplishmentList.add(Accomplishment("Test4", "path"))
        createAcList()
    }

    private fun createAcList(){
        var rvAccomplishment = findViewById<RecyclerView>(R.id.rvAccomplishments)
        rvAccomplishment.layoutManager = GridLayoutManager(this, 3)
        val adapter = AcList(accomplishmentList, this)
        rvAccomplishment?.adapter = adapter
    }
}