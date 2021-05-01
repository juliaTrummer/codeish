package com.mobappdev.codeish.accomplishment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobappdev.codeish.R
import com.mobappdev.codeish.accomplishment.acData.AcList
import com.mobappdev.codeish.accomplishment.acData.Accomplishment

class Accomplishments : AppCompatActivity() {

    private lateinit var accomplishmentList: MutableList<Accomplishment>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.accomplishhment)
        accomplishmentList = ArrayList()
        accomplishmentList.add(Accomplishment("Test1", "<noch leer>"))
        accomplishmentList.add(Accomplishment("Test2", "Rätselmeister!"))
        accomplishmentList.add(Accomplishment("Test3", "Schneller Rechner!"))
        accomplishmentList.add(Accomplishment("Test4", "10 Fragen richtig!"))
        accomplishmentList.add(Accomplishment("Test4", "Rechengenie"))
        accomplishmentList.add(Accomplishment("Test1", "<noch leer>"))
        accomplishmentList.add(Accomplishment("Test1", "<noch leer>"))
        accomplishmentList.add(Accomplishment("Test1", "<noch leer>"))
        createAcList()
    }

    private fun createAcList(){
        var rvAccomplishment = findViewById<RecyclerView>(R.id.rvAccomplishments)
        rvAccomplishment.layoutManager = GridLayoutManager(this, 2)
        val adapter = AcList(accomplishmentList, this)
        rvAccomplishment?.adapter = adapter
    }
}