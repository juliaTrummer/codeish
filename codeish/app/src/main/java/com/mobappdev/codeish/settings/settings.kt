package com.mobappdev.codeish.settings

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.mobappdev.codeish.R
import com.mobappdev.codeish.login.LoginActivity
import com.mobappdev.codeish.mainView.mainView
import kotlin.math.log


class Settings : AppCompatActivity() {

    private lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = FirebaseFirestore.getInstance()
        setContentView(R.layout.settings)
        getUsernameFromDB()


        findViewById<Button>(R.id.logoutButton).setOnClickListener {
            logOut()
        }
    }

    private fun logOut() {
        savePreferences()
        FirebaseAuth.getInstance().signOut()
        deletePreferences()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    /**
     * Firebase Firestore
     * getting username from user
     * collection: users, document: userId
     */
    private fun getUsernameFromDB() {
        val auth = FirebaseAuth.getInstance()
        val userId = auth.uid

        if (userId != null) {
            db.collection("users").document(userId)
                    .get()
                    .addOnSuccessListener { result ->
                        Log.i("Logout", "Success")
                        findViewById<TextView>(R.id.displayusername).text = result.getString("username")
                        findViewById<EditText>(R.id.changeUsername).setText(result.getString("username"))
                        findViewById<EditText>(R.id.changeEmail).setText(result.getString("email"))
                    }
                    .addOnFailureListener { exception ->
                        Log.w("Logout", "Error getting documents: ", exception)
                    }
        }
    }

    private fun savePreferences(){
        val auth = FirebaseAuth.getInstance()
        val customList : MutableList<String> = ArrayList()
        val sharedPreference =  getSharedPreferences("PREFERENCE_NAME",Context.MODE_PRIVATE)
        val keys: Map<String, *> = sharedPreference.all
        var coins = keys["COINS"].toString().toInt()
        var customs : HashSet<String> = HashSet()
        if(keys["CUSTOMS"]!=null){
            customs = keys["CUSTOMS"] as HashSet<String>
        }

        for (custom in customs) {
            customList.add(custom)
        }

        val userData = hashMapOf(
                "coins" to coins,
                "customisations" to customList,
                "userid" to auth.uid
        )

        if (auth.uid != null) {
            val db = FirebaseFirestore.getInstance()
            db.collection("usercustoms")
                    .document()
                    .set(userData)
                    .addOnSuccessListener {
                    }
                    .addOnFailureListener { e -> Log.w(ContentValues.TAG, "ERROR: could not save userData!", e) }
        }
    }

    private fun deletePreferences(){
        val settings: SharedPreferences = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        settings.edit().clear().commit()
    }




}