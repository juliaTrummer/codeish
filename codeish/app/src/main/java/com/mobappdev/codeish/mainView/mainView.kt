package com.mobappdev.codeish.mainView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobappdev.codeish.R
import com.mobappdev.codeish.mainView.data.Topic
import com.mobappdev.codeish.mainView.data.TopicList

class mainView : AppCompatActivity() {

    lateinit var allTopics : ArrayList<Topic>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.topics_recycler)

        createTopicsList()

        var rvTopic = findViewById<RecyclerView>(R.id.rvTopic)
        rvTopic?.layoutManager = LinearLayoutManager(this)
        val adapter = TopicList(allTopics)
        rvTopic?.adapter = adapter
    }

    private fun createTopicsList(){
         allTopics =
            arrayListOf<Topic>(
                Topic(resources.getString(R.string.header1), "test", "testtext", "img"),
                Topic(resources.getString(R.string.header2), "test1", "testtext1", "imgage"),
                Topic(resources.getString(R.string.header3), "test", "testtext", "img"),
                Topic(resources.getString(R.string.header4), "test", "testtext", "img"),
                Topic(resources.getString(R.string.header5), "test", "testtext", "img"),
                Topic(resources.getString(R.string.header6), "test", "testtext", "img"),
                Topic(resources.getString(R.string.header7), "test", "testtext", "img"),
                Topic(resources.getString(R.string.header8), "test", "testtext", "img"))


    }
}