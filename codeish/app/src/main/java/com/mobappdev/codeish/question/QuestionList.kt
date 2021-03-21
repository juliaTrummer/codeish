package com.mobappdev.codeish.question

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.awesomedialog.*
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageReference
import com.mobappdev.codeish.R
import com.mobappdev.codeish.chapter1.itemquiz
import com.mobappdev.codeish.mainView.mainView

class QuestionList : AppCompatActivity() {

    private var db : FirebaseFirestore? = null
    private var progressBar: ProgressBar? = null
    private var trueQuestions: MutableList<Question> = ArrayList()
    private var falseQuestions: MutableList<Question> = ArrayList()
    private var questiongroups : MutableList<QuestionGroup> = ArrayList()
    private var imageButtons : MutableList<ImageButton> = ArrayList()
    private var textViews : MutableList<TextView> = ArrayList()
    private var questionList : MutableList<Question> = ArrayList()
    private var questionCounter : Int = 0
    private var storageString : String = ""
    lateinit var header : TextView
    lateinit var context : Context
    lateinit var storage : FirebaseStorage

    fun generateQuestions(progressBar: ProgressBar, imageViews : MutableList<ImageButton>,
                          textViews:MutableList<TextView>, header: TextView, storageString:String,
                          context : Context){
        db = FirebaseFirestore.getInstance()
        this.progressBar = progressBar
        progressBar.visibility = View.VISIBLE
        this.imageButtons = imageViews
        this.textViews = textViews
        this.context = context
        this.header = header
        this.storageString  = storageString
        storage = FirebaseStorage.getInstance()

        getAllQuestions()
    }

    private fun generateTrueFalseList(){
        for (question in questionList){
            if(question.rightAnswer){
                trueQuestions.add(question)
            }else{
                falseQuestions.add(question)
            }
        }
        trueQuestions.shuffle()
        falseQuestions.shuffle()
    }

    private fun randomQuestions(){
        var counter : Int = 1
        if(questiongroups.size==0){
            for (trueQuestion in trueQuestions){
                if(counter < falseQuestions.size-1){
                    //TODO: Randomize Questions
                    questiongroups.add(QuestionGroup(falseQuestions[counter], falseQuestions[counter+1], trueQuestion))
                    counter+=2
                }
            }
        }
    }

    private fun getAllQuestions(){
        db?.collection("questions")
            ?.get()
            ?.addOnSuccessListener { result ->
                Log.w("QuestionList", "Success getting documents")
                for (document in result) {
                    val currentDbObject = document.toObject(Question::class.java)
                    questionList.add(currentDbObject)
                }
                if(questionList.size!=0){
                    generateTrueFalseList()
                    randomQuestions()
                    addimagesToView()
                }
            }
            ?.addOnFailureListener { exception ->
                Log.w("QuestionList", "Error getting documents: ", exception)
            }
    }

    private fun addimagesToView() {
        if(questiongroups!=null && questiongroups.size!=0 && questionCounter<questiongroups.size){
            var qg = questiongroups[questionCounter]
            header.setText("Frage ${questionCounter + 1}")
            for (i in 0..2){
                when(i){
                    0->{
                        insertingImgWithGlide(imageButtons[0], qg.Question1.imgPath)
                        textViews[0].setText(qg.Question1.name)
                        imageButtons[0].setOnClickListener{
                            displayRightAnswer(qg.Question1.rightAnswer)
                        }
                    }
                    1->{
                        insertingImgWithGlide(imageButtons[1], qg.Question2.imgPath)
                        textViews[1].setText(qg.Question2.name)
                        imageButtons[1].setOnClickListener{
                            displayRightAnswer(qg.Question2.rightAnswer)
                        }
                    }
                    2->{
                        insertingImgWithGlide(imageButtons[2], qg.Question3.imgPath)
                        textViews[2].setText(qg.Question3.name)
                        imageButtons[2].setOnClickListener{
                            displayRightAnswer(qg.Question3.rightAnswer)
                        }
                    }
                }
            }
        }
    }

    private fun displayRightAnswer(rightAnswer: Boolean){
        if(rightAnswer){
            Log.d(ContentValues.TAG, "SUCCESS: Selected right answer")
            questionCounter++
            addimagesToView()
            if(questionCounter>=questiongroups.size){
                AwesomeDialog.build(context as Activity)
                        .title("Toll gemacht!")
                        .position(AwesomeDialog.POSITIONS.CENTER)
                        .icon(R.drawable.coin)
                        .onPositive("Zurück zu den Themen!") {
                            val intent = Intent(context, mainView::class.java)
                            context.startActivity(intent)
                        }
            }else{
                AwesomeDialog.build(context as Activity)
                        .title("Toll gemacht!")
                        .icon(R.drawable.ic_congrts)
                        .position(AwesomeDialog.POSITIONS.CENTER)
                        .onPositive("Weiter zur nächsten Frage!") {

                        }
            }
        }else{
            Log.d(ContentValues.TAG, "Fail: Selected wrong answer")
            AwesomeDialog.build(context as Activity)
                    .title("Oh Nein")
                    .icon(R.drawable.ic_error_)
                    .position(AwesomeDialog.POSITIONS.CENTER)
                    .onPositive("Versuche es noch einmal.") {

                    }
        }

    }
    private fun insertingImgWithGlide(img: ImageView, imgPath:String){
        var storageReference = FirebaseStorage.getInstance().reference.child(storageString+imgPath)
        storageReference.downloadUrl.addOnSuccessListener { Uri ->
            val imageUrl = Uri.toString()
            Glide.with(context)
                    .load(imageUrl)
                    .fitCenter()
                    .placeholder(R.drawable.card_bg)
                    .error(R.drawable.ic_error_)
                    .into(img)
        }
        progressBar?.visibility = View.INVISIBLE
    }

    private fun getAllQuestionImgs() {
        val storage = FirebaseStorage.getInstance()
        val listRef = storage.reference.child(storageString)
        val imgList : ArrayList<img> = ArrayList()
        val allTaskList : Task<ListResult> = listRef.listAll()

        allTaskList.addOnCompleteListener{ result ->
            val imgs: List<StorageReference> = result.result!!.items
            imgs.forEachIndexed{ index, item ->
                item.downloadUrl.addOnSuccessListener {
                    imgList.add(img(it.toString()))
                }.addOnCompleteListener{
                    Log.w("questionList", "Finished reading all downloadUrls!")
                }
            }
        }
    }
}


