package com.mobappdev.codeish.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.mobappdev.codeish.MainActivity
import com.mobappdev.codeish.R
import com.mobappdev.codeish.userdata.userSpecificData

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private var progressBar: ProgressBar? = null
    private lateinit var email: String
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_login)
        auth = FirebaseAuth.getInstance()

        findViewById<TextView>(R.id.backToRegister).setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.loginButton).setOnClickListener {
            closeKeyboard()
            logIn()
        }
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
        //progressBar = findViewById(R.id.progressBarLogin)
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
                    val intent = Intent(this, userSpecificData::class.java)
                    startActivity(intent)
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to login user: ${it.message}", Toast.LENGTH_SHORT)
                    .show()
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
            findViewById<TextInputEditText>(R.id.emailLoginEditText).error = "Please enter text for an email address."
            validationError = true
        }
        if (password.length < 6) {
            findViewById<TextInputEditText>(R.id.passwordLoginEditText).error = "Your password must have at least 6 characters."
            validationError = true
        }

        return validationError
    }
}
