package com.mobappdev.codeish.video

import android.content.Context
import android.content.DialogInterface
import android.net.Uri
import android.text.InputType
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.mobappdev.codeish.R
import org.w3c.dom.Text

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