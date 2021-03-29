package com.mobappdev.codeish.shop.data

import com.mobappdev.codeish.mainView.data.Topic

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.mobappdev.codeish.R

class ShopList (private val mShopItems: List<ShopItem>, val context : Context) : RecyclerView.Adapter<ShopList.ViewHolder>()
{
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val title = itemView.findViewById<TextView>(R.id.item_shop_title)
        val desc = itemView.findViewById<TextView>(R.id.item_shop_description)
        val buybtn = itemView.findViewById<Button>(R.id.buyButton)
        //val image = itemView.findViewById<ImageView>(R.id.item_shop_img)
        val item = itemView.findViewById<LinearLayout>(R.id.item_shop_linlayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopList.ViewHolder {
        val context = parent.context

        val inflater = LayoutInflater.from(context)
        val topicView = inflater.inflate(R.layout.item_shop, parent, false)
        return ViewHolder(topicView)
    }

    override fun onBindViewHolder(viewHolder: ShopList.ViewHolder, position: Int) {

        val shopitem: ShopItem = mShopItems.get(position)
        val title_text = viewHolder.title
        title_text.setText(shopitem.name)

        //val image = viewHolder.image
        //image.setImageResource(shopitem.imagePath)

        val desc_text = viewHolder.desc
        desc_text.setText(shopitem.description)

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
        return mShopItems.size
    }

    private fun setIntent(topic: Topic, viewHolder: ViewHolder){
        val intent = topic.activityName
        viewHolder.itemView.setOnClickListener(){
            context.startActivity(intent)
        }
    }
}