package com.mobappdev.codeish.shop.data

import android.app.Activity
import com.mobappdev.codeish.mainView.data.Topic

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.awesomedialog.*
import com.google.firebase.storage.FirebaseStorage
import com.mobappdev.codeish.R

class ShopList (private val mShopItems: List<ShopItem>, val context : Context, val displaycoins:TextView) : RecyclerView.Adapter<ShopList.ViewHolder>()
{

    private lateinit var customs : HashSet<String>
    private var userCoins : Int = 0
    private lateinit var coinsTextView : TextView

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val title = itemView.findViewById<TextView>(R.id.item_shop_title)
        val desc = itemView.findViewById<TextView>(R.id.item_shop_description)
        val price = itemView.findViewById<TextView>(R.id.item_shop_price)
        val buybtn = itemView.findViewById<Button>(R.id.item_shop_btn)
        val lookbtn = itemView.findViewById<Button>(R.id.item_shop_btn2)
        val image = itemView.findViewById<ImageView>(R.id.item_shop_img)
        val item = itemView.findViewById<LinearLayout>(R.id.item_shop_linlayout)
        val progressBar  = itemView.findViewById<ProgressBar>(R.id.progressBar2)
        val coins = itemView.findViewById<TextView>(R.id.coindisplay)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopList.ViewHolder {
        val context = parent.context
        userCoins = loadPreferences()?.toInt()!!
        coinsTextView = displaycoins
        coinsTextView.setText("" + userCoins)
        val inflater = LayoutInflater.from(context)
        val topicView = inflater.inflate(R.layout.item_shop, parent, false)
        return ViewHolder(topicView)
    }

    override fun onBindViewHolder(viewHolder: ShopList.ViewHolder, position: Int) {

        val shopitem: ShopItem = mShopItems.get(position)
        val title_text = viewHolder.title
        title_text.setText(shopitem.name)

        val price = viewHolder.price
        price.setText(""+shopitem.price)

        val buybutton = viewHolder.buybtn
        buybutton.setOnClickListener {
            AwesomeDialog.build(context as Activity)
                .title("Willst du ${shopitem.name} wirklich kaufen? \nDas kostet " +
                        "${shopitem.price} COINS!")
                .icon(R.drawable.ic_congrts)
                .position(AwesomeDialog.POSITIONS.CENTER)
                .onPositive("JA") {
                    buyItemAndPay(shopitem.price, shopitem.name, shopitem.imagePath)
                }.onNegative("NEIN"){

                }
        }

        val image = viewHolder.image
        insertingImgWithGlide(image, shopitem.imagePath, viewHolder.progressBar)

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

    private fun buyItemAndPay(price:Int, name:String, imgPath: String){
        var coins = userCoins
        if(coins < price){
            createMotionDialog("Du hast nicht genug Geld damit du dir etwas kaufen kannst.", "OK", R.drawable.ic_error_)
        }else{
            if(!customs.contains(imgPath)){
                coins -= price
                userCoins = coins
                createMotionDialog("Du hast " + name +" um " + price + " gekauft! ", "JUHUU!", R.drawable.ic_congrts)
                savePreferences(coins, imgPath)
                coinsTextView.setText(coins.toString())
            }else{
                createMotionDialog("Du hast " + name + " bereits gekauft!\n Sieh dir deine AlienFreunde im Profil an!", "OH NO!", R.drawable.ic_warning_yellow)
            }
        }
    }

    private fun createMotionDialog(text : String, positive:String, draw:Int){
        AwesomeDialog.build(context as Activity)
            .title(text)
            .icon(draw)
            .position(AwesomeDialog.POSITIONS.CENTER)
            .onPositive(positive) {
            }
    }

    private fun loadPreferences() : String?{
        val sharedPreference = context.getSharedPreferences("PREFERENCE_NAME",Context.MODE_PRIVATE)
        val keys: Map<String, *> = sharedPreference.all
        if(keys["CUSTOMS"]!=null){
            customs = keys["CUSTOMS"] as HashSet<String>
        }
        return keys["COINS"].toString()
    }

    private fun setIntent(topic: Topic, viewHolder: ViewHolder){
        val intent = topic.activityName
        viewHolder.itemView.setOnClickListener(){
            context.startActivity(intent)
        }
    }

    private fun savePreferences(coins:Int, customItem:String){
        val sharedPreference =  context.getSharedPreferences("PREFERENCE_NAME",Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        customs.add(customItem)
        editor.putStringSet("CUSTOMS",customs)
        editor.putInt("COINS",coins)
        editor.commit()
    }

    private fun insertingImgWithGlide(img: ImageView, imgPath:String, progressBar:ProgressBar){
        var storageReference = FirebaseStorage.getInstance().reference.child("customs/$imgPath")
        storageReference.downloadUrl.addOnSuccessListener { Uri ->
            val imageUrl = Uri.toString()
            Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.card_bg)
                .error(R.drawable.ic_error_)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?,
                                              target: Target<Drawable>?,
                                              isFirstResource: Boolean): Boolean {
                        return true
                    }
                    override fun onResourceReady(resource: Drawable?, model: Any?,
                                                 target: Target<Drawable>?,
                                                 dataSource: com.bumptech.glide.load.DataSource?,
                                                 isFirstResource: Boolean): Boolean {
                        progressBar.visibility = View.INVISIBLE
                        return false
                    }
                })
                .into(img)
        }
    }
}