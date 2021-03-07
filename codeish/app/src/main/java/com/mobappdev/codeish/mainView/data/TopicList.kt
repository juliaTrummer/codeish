package com.mobappdev.codeish.mainView.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobappdev.codeish.R

class TopicList (private val mTopics: List<Topic>) : RecyclerView.Adapter<TopicList.ViewHolder>()
{
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val title = itemView.findViewById<TextView>(R.id.item_title)
        val desc = itemView.findViewById<TextView>(R.id.item_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicList.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val topicView = inflater.inflate(R.layout.item_topic, parent, false)
        return ViewHolder(topicView)
    }

    override fun onBindViewHolder(viewHolder: TopicList.ViewHolder, position: Int) {
        val topic: Topic = mTopics.get(position)
        val title_text = viewHolder.title
        title_text.setText(topic.name)

        val desc_text = viewHolder.desc
        desc_text.setText(topic.description)
    }

    override fun getItemCount(): Int {
        return mTopics.size
    }
}