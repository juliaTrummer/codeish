package com.mobappdev.codeish

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mobappdev.codeish.mainView.mainView
import com.mobappdev.codeish.profile.usercustoms
import com.mobappdev.codeish.userdata.userSpecificData

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private var progressBar:ProgressBar? = null
    private lateinit var userName:String
    private lateinit var email:String
    private lateinit var password:String
    private lateinit var db: FirebaseFirestore
    private var useruuid : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        findViewById<TextView>(R.id.alreadyAccountTextView).setOnClickListener {
            val intent = Intent("com.mobappdev.codeish.login.LoginActivity")
            startActivity(intent)
        }

        findViewById<Button>(R.id.registerButton).setOnClickListener {
            closeKeyboard()
            createAccount()
        }

        findViewById<ImageView>(R.id.close).setOnClickListener(){
            val intent = Intent(this, mainView::class.java)
            startActivity(intent)
        }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            var profession : String? = loadPreferences()
            if(profession!=null && !profession.equals("")){
                if(profession.equals("student")||profession.equals("teacher")) {
                    val intent = Intent(this, mainView::class.java)
                    startActivity(intent)
                }
            }else{
                val intent = Intent(this, userSpecificData::class.java)
                startActivity(intent)
            }
        }
    }

    private fun loadPreferences() : String?{
        val sharedPreference =  getSharedPreferences("PREFERENCE_NAME",Context.MODE_PRIVATE)
        val keys: Map<String, *> = sharedPreference.all
        getUserCustomsAndCoins()
        return keys["PROFESSION"].toString()
    }

    private fun closeKeyboard() {
        val inputManager: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
                currentFocus?.windowToken,
                InputMethodManager.SHOW_FORCED
        )
    }

    private fun createAccount() {
        //progressBar = findViewById(R.id.progressBarRegister)
        //progressBar?.visibility = View.VISIBLE

        userName = findViewById<EditText>(R.id.usernameRegisterEditText).text.toString()
        email = findViewById<EditText>(R.id.emailRegisterEditText).text.toString()
        password = findViewById<EditText>(R.id.passwordRegisterEditText).text.toString()

        if(hasInputValidationError()){
            progressBar?.visibility = View.INVISIBLE
            return
        }
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        saveUserToDB(userName, email)
                        val intent = Intent(this, userSpecificData::class.java)
                        intent.putExtra("username", userName)
                        startActivity(intent)
                    }
                }
                .addOnFailureListener {
                    progressBar?.visibility = View.INVISIBLE
                }
    }

    private fun hasInputValidationError():Boolean{
        var validationError = false

        if(userName.isEmpty()){
            findViewById<EditText>(R.id.usernameRegisterEditText).error = "Please enter text for a username."
            validationError = true
        }
        if (email.isEmpty()) {
            findViewById<EditText>(R.id.emailRegisterEditText).error = "Please enter text for an email address."
            validationError = true
        }
        if (password.length < 6) {
            findViewById<EditText>(R.id.passwordRegisterEditText).error = "Your password must have at least 6 characters."
            validationError = true
        }
        return validationError
    }

    private fun saveUserToDB(userName: String, email: String) {
        val db : FirebaseFirestore = FirebaseFirestore.getInstance()
        val user = hashMapOf(
                "id" to auth.uid,
                "username" to userName,
                "email" to email
        )
        val userId = auth.uid
        if (userId != null) {
            useruuid = userId
        }
        if (userId != null) {
            db.collection("users").document(userId)
                    .set(user)
                    .addOnSuccessListener {
                        Log.i("Register Activity", "User added to DB")
                    }
                    .addOnFailureListener { e ->
                        Log.i("Register Activity", "Error adding user", e)
                    }
        }
    }

    private fun getUserCustomsAndCoins(){
        db.collection("collectionPath")
            .get()
            .addOnSuccessListener { result ->
                Log.w("Register", "Success getting documents")
                for (document in result) {
                    val currentDbObject = document.toObject(usercustoms::class.java)
                    savePreferences(currentDbObject.coins, currentDbObject.customizations)
                }
            }
            .addOnFailureListener { exception ->
                Log.w("Resgister", "Error getting documents: ", exception)
            }
    }

    private fun savePreferences(coins:Int, customizations:Array<String>?){
        val sharedPreference =  getSharedPreferences("PREFERENCE_NAME",Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        var customSet = HashSet<String>()
        if (customizations != null) {
            for(customs in customizations){
                customSet.add(customs)
            }
            editor.putStringSet("CUSTOMS", customSet)
        }
        editor.putInt("COINS",coins)
        editor.apply()
    }
}
