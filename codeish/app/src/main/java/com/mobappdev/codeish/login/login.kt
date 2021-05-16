package com.mobappdev.codeish.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.content.res.ResourcesCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mobappdev.codeish.R
import com.mobappdev.codeish.mainView.mainView
import com.mobappdev.codeish.profile.usercustoms
import www.sanju.motiontoast.MotionToast

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private var progressBar: ProgressBar? = null
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        findViewById<TextView>(R.id.backToRegister).setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.loginButton).setOnClickListener {
            closeKeyboard()
            logIn()
        }

        findViewById<ImageView>(R.id.close).setOnClickListener(){
           startMainView()
        }
    }

    private fun startMainView(){
        val intent = Intent(this, mainView::class.java)
        startActivity(intent)
    }

    private fun closeKeyboard() {
        val inputManager: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            currentFocus?.windowToken,
            InputMethodManager.SHOW_FORCED
        )
    }

    /**
     * Firebase Authentication
     * log in
     */
    private fun logIn() {
        progressBar?.visibility = View.VISIBLE
        email = findViewById<EditText>(R.id.emailLoginEditText).text.toString()
        password = findViewById<EditText>(R.id.passwordLoginEditText).text.toString()

        if (hasInputValidationError()) {
            progressBar?.visibility = View.INVISIBLE
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    getUserCustomsAndCoins()
                    val intent = Intent(this, mainView::class.java)
                    startActivity(intent)
                }
            }
            .addOnFailureListener {
                MotionToast.createToast(this,
                    "OH Nein...!",
                    "Du hast ein falsches Passwort oder eine falsche E-Mail eingegeben.",
                    MotionToast.TOAST_ERROR,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(this,R.font.helvetica_regular))
                progressBar?.visibility = View.INVISIBLE
            }
    }

    /**
     * Checks for invalid or missing input
     * returns TRUE for invalid input
     */
    private fun hasInputValidationError(): Boolean {
        var validationError = false

        if (email.isEmpty()) {
            findViewById<EditText>(R.id.emailLoginEditText).error = "Please enter text for an email address."
            validationError = true
        }
        if (password.length < 6) {
            findViewById<EditText>(R.id.passwordLoginEditText).error = "Your password must have at least 6 characters."
            validationError = true
        }
        return validationError
    }


    private fun getUserCustomsAndCoins(){
        db.collection("usercustoms")
                .get()
                .addOnSuccessListener { result ->
                    Log.w("Register", "Success getting documents")
                    for (document in result) {
                        val currentDbObject = document.toObject(usercustoms::class.java)
                        savePreferences(currentDbObject.coins, currentDbObject.customisations)
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("Resgister", "Error getting documents: ", exception)
                }
    }

    private fun savePreferences(coins:Int, customizations:MutableList<String>?){
        var actualcoins = 0
        val sharedPreference =  getSharedPreferences("PREFERENCE_NAME",Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        var customSet = HashSet<String>()
        if (customizations != null) {
            for(customs in customizations){
                customSet.add(customs)
            }
            editor.putStringSet("CUSTOMS", customSet)
        }
        actualcoins += coins
        editor.putInt("COINS", actualcoins)
        editor.commit()
    }
}
