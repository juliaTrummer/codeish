package com.mobappdev.codeish.chapter5

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mobappdev.codeish.R
import com.mobappdev.codeish.video.Video

class encryption : AppCompatActivity() {

    private val video = Video()
    lateinit var nextButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_video)
        nextButton = findViewById(R.id.next)
        findViewById<TextView>(R.id.headerintro).setText("KAPITEL 3 - Hardware")

        video.updateVideoView(
            Uri.parse(
                "https://cdn.videvo.net/videvo_files/video/free/2012-07/" +
                        "small_watermarked/Countdown%20Timer_preview.webm"
            ),
            this,
            findViewById(R.id.videoView)
        )

        nextButton.setOnClickListener {
            val activity = Intent(this, ciper_game::class.java)
            startActivity(activity)
        }
    }
}