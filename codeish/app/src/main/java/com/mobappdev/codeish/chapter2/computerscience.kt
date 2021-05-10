package com.mobappdev.codeish.chapter2

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.awesomedialog.*
import com.mobappdev.codeish.R
import com.mobappdev.codeish.video.Video

class computerscience : AppCompatActivity() {

    private val video = Video()
    lateinit var nextButton: Button
    lateinit var questBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_video)
        nextButton = findViewById(R.id.next)
        questBtn = findViewById(R.id.questBtn)
        findViewById<TextView>(R.id.headerintro).setText("KAPITEL 2 - Informatik ")
        video.updateVideoView("<iframe width=\"800\" height=\"450\" " +
                "src=\"https://www.youtube.com/embed/FFrTc5mm0MU\" title=\"YouTube video player\" " +
                "frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; " +
                "encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>",
                this,
                findViewById(R.id.videoView))

        questBtn.setOnClickListener(){
            showdialog()
        }
    }

    fun showdialog() {
        AwesomeDialog.build(this)
                .title("Test")
                .icon(R.drawable.ic_warning_yellow)
                .position(AwesomeDialog.POSITIONS.CENTER)
                .onPositive("OK") {

                }
    }
}