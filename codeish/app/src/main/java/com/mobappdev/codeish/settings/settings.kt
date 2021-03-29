package com.mobappdev.codeish.settings

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
import com.google.firebase.firestore.FirebaseFirestore
import com.mobappdev.codeish.R
import com.mobappdev.codeish.login.LoginActivity
import kotlin.math.log


class Settings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)
        getUsernameFromDB()

        findViewById<Button>(R.id.logoutButton).setOnClickListener {
            logOut()
        }
    }

    private fun logOut() {
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
        val db = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()
        val userId = auth.uid

        if (userId != null) {
            db.collection("users").document(userId)
                    .get()
                    .addOnSuccessListener { result ->
                        Log.i("Logout", "Getting userdata was successful")
                        findViewById<TextView>(R.id.displayusername).text = result.getString("username")
                        findViewById<EditText>(R.id.changeUsername).setText(result.getString("username"))
                        findViewById<EditText>(R.id.changeEmail).setText(result.getString("email"))
                    }
                    .addOnFailureListener { exception ->
                        Log.w("Logout", "Error getting documents: ", exception)
                    }
        }
    }

    private fun deletePreferences(){
        val settings: SharedPreferences = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        settings.edit().clear().commit()
    }




}