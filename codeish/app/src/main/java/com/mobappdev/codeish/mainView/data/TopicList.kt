package com.mobappdev.codeish.mainView.data

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.appcompat.view.menu.MenuView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.mobappdev.codeish.R
import com.mobappdev.codeish.chapter1.gettingstarted


class TopicList (private val mTopics: List<Topic>, val context : Context) : RecyclerView.Adapter<TopicList.ViewHolder>()
{
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val title = itemView.findViewById<TextView>(R.id.item_title)
        val desc = itemView.findViewById<TextView>(R.id.item_description)
        val item = itemView.findViewById<RelativeLayout>(R.id.relativeLayoutTopic)
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

        setIntent(topic, viewHolder)

        if(position % 5 == 0){
            viewHolder.item.setBackgroundResource(R.drawable.itemcolor1)
        }else if(position % 5 == 1){
            viewHolder.item.setBackgroundResource(R.drawable.itemcolor2)
        }else if(position % 5 == 2){
            viewHolder.item.setBackgroundResource(R.drawable.itemcolor3)
        }else if(position % 5 == 3){
            viewHolder.item.setBackgroundResource(R.drawable.itemcolor4)
        }else if(position % 5 == 4){
            viewHolder.item.setBackgroundResource(R.drawable.itemcolor5)
        }
    }

    override fun getItemCount(): Int {
        return mTopics.size
    }

    private fun setIntent(topic: Topic, viewHolder: ViewHolder){
        val intent = topic.activityName
        viewHolder.itemView.setOnClickListener(){
            context.startActivity(intent)
        }
    }
}