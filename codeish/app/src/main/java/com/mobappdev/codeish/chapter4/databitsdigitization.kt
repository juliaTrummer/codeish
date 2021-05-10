package com.mobappdev.codeish.chapter4

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.awesomedialog.*
import com.mobappdev.codeish.R
import com.mobappdev.codeish.chapter4.binarygame.binarygame
import com.mobappdev.codeish.video.Video

class databitsdigitization : AppCompatActivity() {

    private val video = Video()
    lateinit var nextButton: Button
    lateinit var questBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_video)
        nextButton = findViewById(R.id.next)
        questBtn = findViewById(R.id.questBtn)
        findViewById<TextView>(R.id.headerintro).setText("KAPITEL 3 - Binär Rechnen")

        video.updateVideoView("<iframe width=\"800\" height=\"450\" " +
                "src=\"https://www.youtube.com/embed/nPMmD8cTTdc\" title=\"YouTube video player\" " +
                "frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; " +
                "encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>",
                this,
                findViewById(R.id.videoView))

        nextButton.setOnClickListener {
            startActivity(Intent(this, binarygame::class.java))
        }

        questBtn.setOnClickListener(){
            showdialog()
        }

    }

    fun showdialog() {
        AwesomeDialog.build(this)
                .title("Für einen Computer besteht die Welt nur aus 1en und 0en. " +
                        "\n Damit du einen Computer verstehen kannst musst du die „Binär-Sprache“ " +
                        "kennen. Binär ist ein System, das nur aus zwei Zahlen besteht, der 1 " +
                        "und der 0. Das binäre System ist sehr nützlich in elektronischen Geräten, " +
                        "denn über die eins und null kann an und aus dargestellt werden.  \n" +
                        "Das funktioniert gleich, wie wenn du das Licht in deinem Zimmer ein und ausschaltest. ")
                .icon(R.drawable.ic_warning_yellow)
                .position(AwesomeDialog.POSITIONS.CENTER)
                .onPositive("OK") {

                }
    }
}
