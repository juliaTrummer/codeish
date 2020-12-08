package com.mobappdev.codeish.userdata

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mobappdev.codeish.MainActivity
import com.mobappdev.codeish.R
import com.mobappdev.codeish.dao.UserDataObject


class userSpecificData : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private var TAG  : String = "userSpecificData - "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.userdata)

        findViewById<Button>(R.id.studentbutton).setOnClickListener(){
            saveUserData(UserDataObject(FirebaseAuth.getInstance().uid.toString(), "123asdf243", "student"))
        }

        findViewById<Button>(R.id.teacherButton).setOnClickListener(){
            saveUserData(UserDataObject(FirebaseAuth.getInstance().uid.toString(), "", "teacher"))
        }
    }

    /**
     * write to DB when exercise is finished - needed for History
     * minimum SDK 26
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveUserData(userDataObject: UserDataObject) {
        val userData = hashMapOf(
                "userId" to userDataObject.userId,
                "userName" to userDataObject.userName,
                "profession" to userDataObject.profession
        )
        savePreferences("PROFESSION", userDataObject.profession.toString())

        if (userDataObject.userId != null) {
            val db = FirebaseFirestore.getInstance()
            db.collection("userdata")
                    .document()
                    .set(userData)
                    .addOnSuccessListener {
                        Toast.makeText(baseContext, "Saved userData successfully!", Toast.LENGTH_LONG).show()
                        Log.d(ContentValues.TAG, TAG + "SUCCESS: Saved userData successfully!")
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                    .addOnFailureListener { e -> Log.w(ContentValues.TAG, TAG + "ERROR: could not save userData!", e) }
        }
    }

    private fun savePreferences(key: String, value: String) {
        val sharedPreference =  getSharedPreferences("PREFERENCE_NAME",Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()
        editor.putString(key,value)
        editor.commit()
    }
}