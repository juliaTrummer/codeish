package com.mobappdev.codeish.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mobappdev.codeish.R

class Settings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)
    }
}