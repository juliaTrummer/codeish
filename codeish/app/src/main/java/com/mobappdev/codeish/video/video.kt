package com.mobappdev.codeish.video

import android.content.Context
import android.webkit.WebView
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class Video : AppCompatActivity() {

    private lateinit var videoView: WebView
    private lateinit var mediaControls : MediaController
    lateinit var context: Context

    fun updateVideoView(onlineUri:String, context: Context,
                        videoView: WebView){
        this.videoView = videoView
        this.context = context
        addVideoToView(onlineUri)
    }

    private fun addVideoToView(onlineUri:String){
        videoView.settings.javaScriptEnabled = true
        videoView.loadData(onlineUri, "text/html" , "utf-8")
    }

}