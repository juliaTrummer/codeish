package com.mobappdev.codeish.shop

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.mobappdev.codeish.R
import com.mobappdev.codeish.shop.data.ShopItem
import com.mobappdev.codeish.shop.data.ShopList

class Shop : AppCompatActivity() {

    private lateinit var currentShopList : MutableList<ShopItem>
    private var db : FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shop)
        db = FirebaseFirestore.getInstance()
        currentShopList = ArrayList()
        getAllQuestions("shopItems")
    }

    private fun createShopList(){
        var rvShopItem = findViewById<RecyclerView>(R.id.rvShop)
        rvShopItem.layoutManager = GridLayoutManager(this, 2)
        val adapter = ShopList(currentShopList, this)
        rvShopItem?.adapter = adapter
    }

    private fun getAllQuestions(collectionPath: String){
        db?.collection(collectionPath)
                ?.get()
                ?.addOnSuccessListener { result ->
                    Log.w("Item List", "Success getting documents")
                    for (document in result) {
                        val currentDbObject = document.toObject(ShopItem::class.java)
                        currentShopList.add(currentDbObject)
                    }
                    createShopList()
                }
                ?.addOnFailureListener { exception ->
                    Log.w("QuestionList", "Error getting documents: ", exception)
                }
    }
}