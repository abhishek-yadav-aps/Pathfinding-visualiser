package com.example.pathfindingvisualiser

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginTop
import androidx.core.view.setPadding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val buttonStatusKeeper: MutableList<MutableMap<Button,Int>> = ArrayList()
    val buttons: MutableList<MutableList<Button>> = ArrayList()
    val size=10
    var startStatusKeeper:Int=0
    var endStatusKeeper:Int=0


    val gdForRedColor:GradientDrawable= GradientDrawable()
    val gdForGreenColor:GradientDrawable= GradientDrawable()
    val gdForBrownColor:GradientDrawable= GradientDrawable()
    val gdForWhiteColor:GradientDrawable= GradientDrawable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gradientDrawableValueSetter()


        createButtonGrid()
        paintAllButtonsWhite()

    }

    private fun gradientDrawableValueSetter() {
        gdForRedColor.setColor(Color.parseColor("#FF0000"))
        gdForRedColor.cornerRadius=5.0f
        gdForRedColor.setStroke(1,Color.parseColor("#000000"))

        gdForBrownColor.setColor(Color.parseColor("#A52A2A"))
        gdForBrownColor.cornerRadius=5.0f
        gdForBrownColor.setStroke(1,Color.parseColor("#000000"))

        gdForGreenColor.setColor(Color.parseColor("#008000"))
        gdForGreenColor.cornerRadius=5.0f
        gdForGreenColor.setStroke(1,Color.parseColor("#000000"))

        gdForWhiteColor.setColor(Color.parseColor("#FFFFFF"))
        gdForWhiteColor.cornerRadius=5.0f
        gdForWhiteColor.setStroke(1,Color.parseColor("#000000"))
    }

    private fun paintAllButtonsWhite() {
        for (i in 0..size)
        {
            for (j in 0..(size*2))
            {
                buttons[i][j].background=gdForWhiteColor
            }
        }
    }

    private fun createButtonGrid() {

        for (i in 0..size) {

            val arrayLinearLayout = LinearLayout(this)
            arrayLinearLayout.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT,1.0f
            )
            arrayLinearLayout.orientation = LinearLayout.VERTICAL
            //arrayLinearLayout.setPadding(2,2,2,2)

            val buttonStatusCol: MutableMap<Button,Int> = mutableMapOf()
            val buttonCol:MutableList<Button> = mutableListOf()
            for (j in 0..(size*2)) {
                val button = Button(this)
                button.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    1.0f
                )
                //button.setPadding(3,3,3,3)
                button.background=null
                button.setOnClickListener {

                    if(startStatusKeeper==0)
                    {
                        button.setBackgroundResource(R.drawable.ic_trending_flat_24px)
                        button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF0000")))
                        button.isClickable=false
                        startStatusKeeper=1
                    }
                    else if(endStatusKeeper==0){
                        button.setBackgroundResource(R.drawable.ic_gps_fixed_24px)
                        button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#008000")))
                        button.isClickable=false
                        endStatusKeeper=1
                    }
                    else {
                        val buttonStatus = buttonStatusCol.get(button)
                        if (buttonStatus == 0) {
                            button.background=gdForBrownColor
                            buttonStatusCol.put(button, 1)
                        } else if (buttonStatus == 1) {
                            button.background=gdForWhiteColor
                            buttonStatusCol.put(button, 0)
                        }
                    }
                }
                buttonStatusCol.put(button,0)
                buttonCol.add(button)
                arrayLinearLayout.addView(button)
            }
            buttonStatusKeeper.add(buttonStatusCol)
            buttons.add(buttonCol)

            screen.addView(arrayLinearLayout)
        }



    }




}











