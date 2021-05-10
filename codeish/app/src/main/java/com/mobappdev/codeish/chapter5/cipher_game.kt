package com.mobappdev.codeish.chapter5

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.awesomedialog.*
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
    private var displayLenght : Int = 0

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
        wordList.add("MARS")
        wordList.add("VENUS")
        wordList.add("MERKUR")
        wordList.add("SATURN")


        generateCipher()
    }

    private fun generateCipher(){
        var randomNumber = generateRandomNumber(1, 20)
        var generatedString : String = ""
        var word = wordList[generateRandomNumber(0, wordList.size)]

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
        generateHintDisplay(word, displayLenght)

        answerbtn.setOnClickListener(){
            showdialog(word)
        }

        hintbtn.setOnClickListener(){
            generateHintDisplay(word, displayLenght);
        }
    }

    fun generateHintDisplay(word:String, length:Int){
        var generatedHint : String = ""
        if(length!=word.length){
            for(i in 0..length){
                generatedHint += word[i] + " "
            }
            for(i in length..word.length-2){
                generatedHint += " _ "
            }
        }
        if(displayLenght==word.length-1){
            createFailedAwesomeDialog(word)
        }else{
            displayLenght++
        }
        hintW.setText(generatedHint)
    }

    fun showdialog(word : String){
        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Gib das Lösungswort ein und klicke auf OK!")
        val input = EditText(this)
        input.setHint("Lösungswort")
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)
        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
            var m_Text = input.text.toString()
            if(m_Text == word.toLowerCase()){
                createAwesomeDialog();
            }else{
                createFailedAwesomeDialog()
            }
        })
        builder.setNegativeButton("Abbrechen", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
        builder.show()
    }

    private fun createAwesomeDialog(){
            AwesomeDialog.build(this)
                    .title("Toll gemacht! \nDu bekommst 100 COINS!")
                    .icon(R.drawable.ic_congrts)
                    .position(AwesomeDialog.POSITIONS.CENTER)
                    .onPositive("Weiter zum nächsten Rätsel!") {
                        displayLenght=0
                        generateCipher()
                    }
        addCoinsToSharedPrefs(100)
    }

    private fun addCoinsToSharedPrefs(coins:Int){
        val sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        val keys: Map<String, *> = sharedPreference.all
        editor.putInt("COINS",keys["COINS"].toString().toInt() + coins)
        editor.commit()
    }

    private fun createFailedAwesomeDialog(){
        AwesomeDialog.build(this)
                .title("Oh Nein! \nDas Wort ist nicht richtig.")
                .icon(R.drawable.ic_error_)
                .position(AwesomeDialog.POSITIONS.CENTER)
                .onPositive("Versuche es noch einmal.")
    }

    private fun createFailedAwesomeDialog(word:String){
        AwesomeDialog.build(this)
                .title("Oh Nein! \nDas wort war: " + word.toUpperCase() +" !\nDafür bekommst du keine COINS.")
                .icon(R.drawable.ic_error_)
                .position(AwesomeDialog.POSITIONS.CENTER)
                .onPositive("Weiter zum nächsten Rätsel!") {
                    displayLenght=0
                    generateCipher()
                }
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