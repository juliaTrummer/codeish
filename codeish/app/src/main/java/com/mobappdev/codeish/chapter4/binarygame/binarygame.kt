package com.mobappdev.codeish.chapter4.binarygame

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextClock
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import com.example.awesomedialog.*
import com.mobappdev.codeish.R
import com.mobappdev.codeish.chapter1.itemquiz
import com.mobappdev.codeish.video.Video
import org.w3c.dom.Text
import www.sanju.motiontoast.MotionToast
import kotlin.properties.Delegates

class binarygame : AppCompatActivity() {

    private lateinit var binarybtn128: Button
    private lateinit var binarybtn64: Button
    private lateinit var binarybtn32: Button
    private lateinit var binarybtn16: Button
    private lateinit var binarybtn8: Button
    private lateinit var binarybtn4: Button
    private lateinit var binarybtn2: Button
    private lateinit var binarybtn1: Button
    private lateinit var randomNumberToCalculate: TextView
    private lateinit var calculatedNumber : TextView
    private lateinit var btnList : MutableList<Button>
    private var numberToBeCalculate : Int = 0
    private var calcResult : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.binary_game)

        initValues()
        updateWithNextNumber()
        createOnClickListener()
    }

    private fun initValues(){
        randomNumberToCalculate = findViewById<TextView>(R.id.randomNumberToCalculate)
        btnList = ArrayList()
        calculatedNumber = findViewById<TextView>(R.id.calculatedNumber)


        btnList.add(findViewById(R.id.binarybtn128))
        btnList.add(findViewById(R.id.binarybtn64))
        btnList.add(findViewById(R.id.binarybtn32))
        btnList.add(findViewById(R.id.binarybtn16))
        btnList.add(findViewById(R.id.binarybtn8))
        btnList.add(findViewById(R.id.binarybtn4))
        btnList.add(findViewById(R.id.binarybtn2))
        btnList.add(findViewById(R.id.binarybtn1))

        binarybtn128 = findViewById(R.id.binarybtn128)
        binarybtn64 = findViewById(R.id.binarybtn64)
        binarybtn32 = findViewById(R.id.binarybtn32)
        binarybtn16 = findViewById(R.id.binarybtn16)
        binarybtn8 = findViewById(R.id.binarybtn8)
        binarybtn4 = findViewById(R.id.binarybtn4)
        binarybtn2 = findViewById(R.id.binarybtn2)
        binarybtn1 = findViewById(R.id.binarybtn1)
    }

    private fun createOnClickListener(){
        for(btn in btnList){
            //ObserverPattern Idea?
            btn.setOnClickListener {
                if(btn.text.toString().equals("1")){
                    calculateResult(btn, 0)
                    btn.text = "0"
                }else{
                    calculateResult(btn, 1)
                    btn.text = "1"
                }
            }
        }
    }


    private fun calculateResult(btn : Button, calcOp : Int){
        when (btn){
            binarybtn128 -> {
                addToResult(128, calcOp)
            }
            binarybtn64 -> {
                addToResult(64, calcOp)
            }
            binarybtn32 -> {
                addToResult(32, calcOp)
            }
            binarybtn16 -> {
                addToResult(16, calcOp)
            }
            binarybtn8 -> {
                addToResult(8, calcOp)
            }
            binarybtn4 -> {
                addToResult(4, calcOp)
            }
            binarybtn2 -> {
                addToResult(2, calcOp)
            }
            binarybtn1 -> {
                addToResult(1, calcOp)
            }
        }
    }

    private fun addToResult(num : Int, calcOp: Int){
        if(calcOp==1){
            calcResult += num
            calculatedNumber.text = "${calcResult}"
        }else{
            calcResult -= num
            calculatedNumber.text = "${calcResult}"
        }
        checkForRightResult()
    }

    private fun checkForRightResult(){
        if (calcResult == numberToBeCalculate){
            AwesomeDialog.build(this)
                    .title("Toll gemacht!")
                    .icon(R.drawable.ic_congrts)
                    .position(AwesomeDialog.POSITIONS.CENTER)
                    .onPositive("Weiter zum nächsten Beispiel!") {
                        updateWithNextNumber()
                    }
        }
    }

    private fun updateWithNextNumber(){
        for(btn in btnList){
            btn.text = "0"
        }
        numberToBeCalculate = randomNumber()
        randomNumberToCalculate.setText("Berechne diese Nummer: \n" + numberToBeCalculate)
        calculatedNumber.text = "0"
        calcResult = 0
    }

    private fun randomNumber() : Int{
        return (0..255).random()
    }
}