package com.mobappdev.codeish.chapter5

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.lukedeighton.wheelview.WheelView
import com.lukedeighton.wheelview.WheelView.OnWheelAngleChangeListener
import com.lukedeighton.wheelview.WheelView.OnWheelItemClickListener
import com.mobappdev.codeish.R
import com.mobappdev.codeish.chapter5.gamedata.WheelAdapter

class ciper_game : AppCompatActivity() {

    private lateinit var wheelView : WheelView
    private lateinit var parent : WheelView
    private lateinit var itemDrawable : Drawable
    private var position: Int = 0
    private lateinit var wordList: MutableList<String>
    private lateinit var generatedW : TextView
    private lateinit var hintW : TextView
    private lateinit var hintbtn : MaterialButton
    private lateinit var answerbtn : MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chapter5_cipher_game)
        initWheel()
        generatedW = findViewById(R.id.generatedWord)
        hintW = findViewById(R.id.textViewHint)
        hintbtn = findViewById(R.id.hintBtn)
        answerbtn = findViewById(R.id.answerBtn)

        wordList = ArrayList()
        wordList.add("SONNE")
        wordList.add("ASTRONAUT")
        wordList.add("RAKETE")
        wordList.add("WELTALL")
        wordList.add("STERNE")
        wordList.add("PLANETEN")
        wordList.add("MOND")

        generateCipher()

        

    }


    private fun generateCipher(){
        var randomNumber = generateRandomNumber(1, 20)
        var generatedString : String = ""
        var word = wordList[generateRandomNumber(0, wordList.size)]
        val result = StringBuffer()

        for (i in 0..word.length-1) {
            if (Character.isUpperCase(word[i])) {
                var n : Int = (word[i] + randomNumber - 65).toInt()
                n = n.rem(26)+65
                generatedString+= n.toChar() + " "
            }else{
                var n : Int = (word[i] + randomNumber - 97).toInt()
                n = n.rem(26)+97
                generatedString+= n.toChar() + " "
            }
        }
        generatedW.setText(generatedString)
        var generatedHint : String = ""
        generatedHint += word[0] + " "
        for(i in 0..word.length-2){
            generatedHint += " _ "
        }
        hintW.setText(generatedHint)
    }

    private fun generateRandomNumber(start:Int, end:Int) : Int{
        return (start..end).random()
    }

    private fun initWheel(){
        val wheelAdapter = WheelAdapter(this)
        wheelView = findViewById<WheelView>(R.id.wheelview)
        wheelView.adapter = wheelAdapter

        wheelView.setOnWheelItemSelectedListener { parent, itemDrawable, position ->
            //the adapter position that is closest to the selection angle and it's drawable

        }
        wheelView.onWheelItemClickListener = OnWheelItemClickListener { parent, position, isSelected ->
            //the position in the adapter and whether it is closest to the selection angle
            //TODO
        }
        wheelView.onWheelAngleChangeListener = OnWheelAngleChangeListener {
            //the new angle of the wheel
            //TODO:
        }
    }

}