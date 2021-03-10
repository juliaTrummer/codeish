package com.mobappdev.codeish.userdata

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mobappdev.codeish.MainActivity
import com.mobappdev.codeish.R
import com.mobappdev.codeish.dao.UserDataObject
import com.mobappdev.codeish.mainView.mainView
import org.w3c.dom.Text
import www.sanju.motiontoast.MotionToast


class userSpecificData : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private var TAG  : String = "userSpecificData - "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.userdata)

        var username : String? = intent.getStringExtra("username")
        if(username!=null){
            findViewById<TextView>(R.id.usernameGreeting).setText(username)
        }

        findViewById<Button>(R.id.studentbutton).setOnClickListener(){
            if(username!=null){
                saveUserData(UserDataObject(FirebaseAuth.getInstance().uid.toString(), username, "student"))
            }
        }

        findViewById<Button>(R.id.teacherButton).setOnClickListener(){
            saveUserData(UserDataObject(FirebaseAuth.getInstance().uid.toString(), "test", "teacher"))
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
                        MotionToast.createToast(this,
                                "Hurra 😍",
                                "Du bist nun angemeldet!",
                                MotionToast.TOAST_SUCCESS,
                                MotionToast.GRAVITY_BOTTOM,
                                MotionToast.SHORT_DURATION,
                                ResourcesCompat.getFont(this,R.font.helvetica_regular))
                        Log.d(ContentValues.TAG, TAG + "SUCCESS: Saved userData successfully!")
                        val intent = Intent(this, mainView::class.java)
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