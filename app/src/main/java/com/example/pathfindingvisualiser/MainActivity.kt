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
    val graph: MutableList<MutableList<MutableList<Button>>> = ArrayList()
    var butsrcx:Int=0
    var butsrcy:Int=0
    var butdesx:Int=0
    var butdesy:Int=0

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
        //findPath()
        search.setOnClickListener {
            findPath()
        }
        
    }

    private fun findPath() {
        var obj=dijikstra()
        obj.size=size
        obj.srcx=butsrcx
        obj.srcy=butsrcy
        obj.desx=butdesx
        obj.desy=butdesy
        obj.weightMaker()
        for (i in 0..size-1){
            for(j in 0..size-1){
                if(buttonStatusKeeper[i].get(buttons[i][j])==1){
                    obj.weight[i][j]=1000
                }
            }
        }
        obj.main()
        var path=obj.path
        tester.append(butsrcx.toString()+" "+butsrcy.toString()+"\n")
        tester.append(butdesx.toString()+" "+butdesy.toString()+"\n")
        for (i in 1..(path[butdesx][butdesy].size-1)){
            buttons[path[butdesx][butdesy][i][0]][path[butdesx][butdesy][i][1]].setBackgroundColor(Color.parseColor("#008000"))
            tester.append(path[butdesx][butdesy][i][0].toString()+" "+path[butdesx][butdesy][i][1].toString()+"\n")
        }
    }

    val graphh= listOf<Int>()

    private fun createGraph() {
        //inside grid
        Log.i("grapher","missle")
        for (i in 1 until size)
        {
            Log.i("grapher","missle1")
            for (j in 1 until (size*2)-1){
                //left
                graph[i][j].add(buttons[i-1][j])
                //top
                Log.i("grapher","missle2")
                graph[i][j].add(buttons[i][j-1])
                //right
                graph[i][j].add(buttons[i+1][j])
                //bottom
                graph[i][j].add(buttons[i][j+1])
            }
        }
        Log.i("grapher","missle3")
        //top edges
        for (i in 1..(size-1)){
            //left
            graph[i][0].add(buttons[i-1][0])
            //right
            graph[i][0].add(buttons[i+1][0])
            //bottom
            graph[i][0].add(buttons[i][1])
        }
        //bottom edges
        for (i in 1..(size-1)){
            //left
            graph[i][(size*2)].add(buttons[i-1][(size*2)])
            //right
            graph[i][(size*2)].add(buttons[i+1][(size*2)])
            //top
            graph[i][(size*2)].add(buttons[i][(size*2)-1])
        }
        //left edges
        for (i in 1..(size*2)-1){
            //top
            graph[0][i].add(buttons[0][i-1])
            //right
            graph[0][i].add(buttons[1][i])
            //bottom
            graph[0][i].add(buttons[0][i+1])
        }
        //right edges
        for (i in 1..(size*2)-1){
            //top
            graph[size][i].add(buttons[size][i-1])
            //left
            graph[size][i].add(buttons[size-1][i])
            //bottom
            graph[size][i].add(buttons[size][i+1])
        }

        //top-left corner
        graph[0][0].add(buttons[0][1])//bottom
        graph[0][0].add(buttons[1][0])//right
        //top-right corner
        graph[size][0].add(buttons[size][1])//bottom
        graph[size][0].add(buttons[size-1][0])//left
        //bottom-left corner
        graph[0][(size*2)].add(buttons[0][(size*2)-1])//top
        graph[0][(size*2)].add(buttons[1][(size*2)])//right
        //bottom-right corner
        graph[size][(size*2)-1].add(buttons[size][(size*2)-1])//top
        graph[size][(size*2)-1].add(buttons[size-1][(size*2)])//right
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
            for (j in 0..(size))
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
            val graphCol:MutableList<MutableList<Button>> = mutableListOf()
            for (j in 0..(size)) {
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
                        butsrcx=i
                        butsrcy=j
                    }
                    else if(endStatusKeeper==0){
                        button.setBackgroundResource(R.drawable.ic_gps_fixed_24px)
                        button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#008000")))
                        button.isClickable=false
                        endStatusKeeper=1
                        butdesx=i
                        butdesy=j
                        //findPath()
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
                val graphPoint:MutableList<Button> = mutableListOf()
                graphCol.add(graphPoint)
            }
            buttonStatusKeeper.add(buttonStatusCol)
            buttons.add(buttonCol)
            graph.add(graphCol)
            screen.addView(arrayLinearLayout)
        }



    }




}











