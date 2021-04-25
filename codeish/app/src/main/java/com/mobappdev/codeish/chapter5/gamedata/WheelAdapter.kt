package com.mobappdev.codeish.chapter5.gamedata

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.Drawable
import com.amulyakhare.textdrawable.TextDrawable
import com.lukedeighton.wheelview.adapter.WheelAdapter
import com.mobappdev.codeish.R
import org.w3c.dom.Text


class WheelAdapter (context: Context) : WheelAdapter{

    private var cont = context
    private var c : Char = 'A'
    private var counter : Int = 0

    override fun getDrawable(position: Int): Drawable {
        var drawableIcon = TextDrawable.builder().buildRound("$c", Color.TRANSPARENT)
        c++
        return drawableIcon
    }

    override fun getCount(): Int {
        return 26
    }

}
