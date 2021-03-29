package com.mobappdev.codeish.video

import android.content.Context
import android.net.Uri
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

class Video : AppCompatActivity() {

    private lateinit var videoView: VideoView
    private lateinit var mediaControls : MediaController
    lateinit var context: Context

    fun updateVideoView(onlineUri:Uri, context: Context,
                        videoView: VideoView){
        this.videoView = videoView
        this.context = context
        addVideoToView(onlineUri)
    }

    private fun addVideoToView(onlineUri:Uri){
        mediaControls = MediaController(context)
        mediaControls.setAnchorView(videoView)

        videoView.setMediaController(mediaControls)
        videoView.setVideoURI(onlineUri)
        videoView.requestFocus()
        videoView.start()
    }
}