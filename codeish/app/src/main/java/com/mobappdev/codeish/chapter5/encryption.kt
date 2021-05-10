package com.mobappdev.codeish.chapter5

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.awesomedialog.*
import com.mobappdev.codeish.R
import com.mobappdev.codeish.video.Video

class encryption : AppCompatActivity() {

    private val video = Video()
    lateinit var nextButton: Button
    lateinit var questBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_video)
        nextButton = findViewById(R.id.next)
        questBtn = findViewById(R.id.questBtn)
        findViewById<TextView>(R.id.headerintro).setText("KAPITEL 4 - Verschlüsselung")

        video.updateVideoView("<iframe width=\"800\" height=\"500\" " +
                "src=\"https://www.youtube.com/embed/ieGzXdzmdIc\" " +
                "title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; " +
                "clipboard-write; encrypted-media; gyroscope; picture-in-picture\" " +
                "allowfullscreen></iframe>",
                this,
                findViewById(R.id.videoView))

        nextButton.setOnClickListener {
            val activity = Intent(this, ciper_game::class.java)
            startActivity(activity)
        }
        questBtn.setOnClickListener(){
            showdialog()
        }
    }

    fun showdialog() {
        AwesomeDialog.build(this)
                .title("Mit Kryptographie versucht man Informationen zu verstecken. " +
                        "Oft wird die Kryptografie von großen Firmen verwendet, die nicht wollen " +
                        "das jeder geheime Informationen sehen kann. \n Geräte, die verschlüsselt " +
                        "werden, sind zum Beispiel Geldautomaten oder kann sogar dein eigener " +
                        "Computer sein. Texte die Verschlüsselt sind, sind zwar für jeden " +
                        "lesbar aber können nicht von jedem verstanden werden.")
                .icon(R.drawable.ic_warning_yellow)
                .position(AwesomeDialog.POSITIONS.CENTER)
                .onPositive("OK") {

                }
    }
}