package com.mobappdev.codeish.rankings.rankdata

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobappdev.codeish.R
import com.mobappdev.codeish.mainView.data.Topic


class RankList (private val mRanks: List<Rank>, val context : Context) : RecyclerView.Adapter<RankList.ViewHolder>(){

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val rank = itemView.findViewById<TextView>(R.id.rank)
        val rName = itemView.findViewById<TextView>(R.id.rankName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankList.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val topicView = inflater.inflate(R.layout.item_rank, parent, false)
        return ViewHolder(topicView)
    }

    override fun getItemCount(): Int {
        return mRanks.size
    }

    override fun onBindViewHolder(holder: RankList.ViewHolder, position: Int) {
        val rank: Rank = mRanks.get(position)
        holder.rName.setText(rank.name)
        holder.rank.setText("${position+1}")
    }

}