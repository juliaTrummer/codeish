package com.mobappdev.codeish.chapter1

import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.mobappdev.codeish.R

class gettingstarted : AppCompatActivity() {

    private lateinit var videoView: VideoView
    private lateinit var mediaControls : MediaController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chapter1_gettingstarted)

        videoView = findViewById(R.id.videoView)
        mediaControls = MediaController(this)
        mediaControls.setAnchorView(videoView)

        val onlineUri = Uri.parse("https://cdn.videvo.net/videvo_files/video/free/2012-07/small_watermarked/Countdown%20Timer_preview.webm")

        videoView.setMediaController(mediaControls)
        videoView.setVideoURI(onlineUri)
        videoView.requestFocus()
        videoView.start()

    }
}
