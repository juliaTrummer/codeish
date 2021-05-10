package com.mobappdev.codeish.chapter3

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.awesomedialog.*
import com.mobappdev.codeish.R
import com.mobappdev.codeish.chapter1.itemquiz
import com.mobappdev.codeish.video.Video

class hardware : AppCompatActivity() {

    private val video = Video()
    lateinit var nextButton: Button
    lateinit var questBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_video)
        nextButton = findViewById(R.id.next)
        questBtn = findViewById(R.id.questBtn)
        findViewById<TextView>(R.id.headerintro).setText("KAPITEL 2 - Hardware")

        video.updateVideoView("<iframe width=\"800\" height=\"500\" " +
                "src=\"https://www.youtube.com/embed/v4WZcR2IgXA\" title=\"YouTube video player\" " +
                "frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; " +
                "encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>",
                this,
                findViewById(R.id.videoView))

        nextButton.setOnClickListener {
            val activity = Intent(this, itemquiz::class.java)
            activity.putExtra("collectionPath", "hardware")
            activity.putExtra("storageString", "hardware/")
            startActivity(activity)
        }

        questBtn.setOnClickListener(){
            showdialog()
        }
    }

    fun showdialog() {
        AwesomeDialog.build(this)
                .title("Software, sprich ssofft-währ, sind Programme, mit denen man Computer " +
                        "betreibt. Dank Software passiert überhaupt etwas auf dem Computer. Die " +
                        "Software enthält Regeln und Befehle, damit der Computer weiß, was er tun soll.\n" +
                        "\n" +
                        "Das Gegenstück zur Software ist die Hardware: Das sind die Teile des" +
                        " Computers, die man anfassen kann. Software ist deshalb „immateriell“, " +
                        "kein fester Gegenstand. Man kann sie leicht vervielfältigen und verändern.")
                .icon(R.drawable.ic_warning_yellow)
                .position(AwesomeDialog.POSITIONS.CENTER)
                .onPositive("OK") {

                }
    }
}