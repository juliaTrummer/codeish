package com.mobappdev.codeish.chapter1


import android.os.Bundle
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mobappdev.codeish.R
import com.mobappdev.codeish.question.QuestionList


class itemquiz : AppCompatActivity() {

    private val questions = QuestionList()
    var imageViews : MutableList<ImageButton> = ArrayList()
    var textViews : MutableList<TextView> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chapter1_quiz)

        val progressBar : ProgressBar = findViewById(R.id.progressBar)
        val header : TextView = findViewById(R.id.header)
        val storageString : String = "gettingstarted/"

        imageViews = ArrayList()
        imageViews.add(findViewById(R.id.imageButton0))
        imageViews.add(findViewById(R.id.imageButton1))
        imageViews.add(findViewById(R.id.imageButton2))

        textViews.add(findViewById(R.id.nametag0))
        textViews.add(findViewById(R.id.nametag1))
        textViews.add(findViewById(R.id.nametag2))

        questions.generateQuestions(progressBar, imageViews, textViews, header, storageString,
            this)
    }
}