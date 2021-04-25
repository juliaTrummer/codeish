package com.mobappdev.codeish.accomplishment.acData

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobappdev.codeish.R

import com.mobappdev.codeish.shop.data.ShopItem

class AcList (private val mAccomplishments: List<Accomplishment>, val context : Context) : RecyclerView.Adapter<AcList.ViewHolder>(){

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val badgeimg = itemView.findViewById<ImageView>(R.id.badge)
        val badgetitle = itemView.findViewById<TextView>(R.id.badgetitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AcList.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val topicView = inflater.inflate(R.layout.item_ac, parent, false)
        return ViewHolder(topicView)
    }

    override fun getItemCount(): Int {
        return mAccomplishments.size
    }

    override fun onBindViewHolder(holder: AcList.ViewHolder, position: Int) {
        val ac: Accomplishment = mAccomplishments.get(position)
        holder.badgetitle.setText("${ac.title}")
    }

}