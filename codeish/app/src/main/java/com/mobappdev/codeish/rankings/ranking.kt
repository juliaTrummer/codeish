package com.mobappdev.codeish.rankings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobappdev.codeish.R
import com.mobappdev.codeish.mainView.data.TopicList
import com.mobappdev.codeish.rankings.rankdata.Rank
import com.mobappdev.codeish.rankings.rankdata.RankList

class Ranking : AppCompatActivity() {

    private lateinit var ranks : MutableList<Rank>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ranking)
        ranks = ArrayList()
        ranks.add(Rank("Ju", 1000))
        ranks.add(Rank("Marshal", 500))
        ranks.add(Rank("Gigi", 100))
        ranks.add(Rank("Stefan", 50))

        createList()
    }

    private fun createList(){
        var rvRank = findViewById<RecyclerView>(R.id.rvRank)
        rvRank?.layoutManager = LinearLayoutManager(this)
        val adapter = RankList(ranks, this)
        rvRank?.adapter = adapter
    }
}