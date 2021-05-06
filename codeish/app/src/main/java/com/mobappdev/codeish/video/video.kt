package com.mobappdev.codeish.video

import android.content.Context
import android.content.DialogInterface
import android.net.Uri
import android.text.InputType
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.mobappdev.codeish.R
import org.w3c.dom.Text

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