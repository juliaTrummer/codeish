package com.mobappdev.codeish.chapter1

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.awesomedialog.*
import com.mobappdev.codeish.R
import com.mobappdev.codeish.video.Video

class gettingstarted : AppCompatActivity() {

    private val video = Video()
    lateinit var nextButton: Button
    lateinit var questBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_video)
        nextButton = findViewById(R.id.next)
        questBtn = findViewById(R.id.questBtn)
        findViewById<TextView>(R.id.headerintro).setText("KAPITEL 1 - Lass uns anfangen!")

        video.updateVideoView("<iframe width=\"800\" height=\"500\" " +
                "src=\"https://www.youtube.com/embed/FFrTc5mm0MU\" title=\"YouTube video player\" " +
                "frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; " +
                "encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>",
        this,
                findViewById(R.id.videoView))
        

        nextButton.setOnClickListener {
            val activity = Intent(this, itemquiz::class.java)
            activity.putExtra("collectionPath", "questions")
            activity.putExtra("storageString", "gettingstarted/")
            startActivity(activity)
        }

        findViewById<Button>(R.id.questBtn).setOnClickListener(){
            showdialog()
        }
    }
    fun showdialog() {
        AwesomeDialog.build(this)
                .title("Es gibt verschiedene Computer. Manche dieser Computer sind riesig und manche ganz klein. \n" +
                        "Normalerweise spricht man von einem Computer, wenn man über einen PC spricht. \n" +
                        "Zuhause hat man viele Computer, wie ein Desktop Computer, ein Laptop oder ein Tablet aber auch dein Smartphone ist ein Computer. \n")
                .icon(R.drawable.ic_warning_yellow)
                .position(AwesomeDialog.POSITIONS.CENTER)
                .onPositive("OK") {

                }
    }
}
