package com.example.pathfindingvisualiser

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast

import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginTop
import androidx.core.view.setPadding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    val buttonStatusKeeper: MutableList<MutableMap<Button,Int>> = ArrayList()
    val buttons: MutableList<MutableList<Button>> = ArrayList()
    val size=10
    val sizeb=20
    var startStatusKeeper:Int=0
    var endStatusKeeper:Int=0
    var butsrcx:Int=0
    var butsrcy:Int=0
    var butdesx:Int=0
    var butdesy:Int=0

    val gdForRedColor:GradientDrawable= GradientDrawable()
    val gdForGreenColor:GradientDrawable= GradientDrawable()
    val gdForBrownColor:GradientDrawable= GradientDrawable()
    val gdForWhiteColor:GradientDrawable= GradientDrawable()


    var v:MutableList<MutableList<MutableList<MutableList<Int>>>> = mutableListOf()
    var dis:MutableList<MutableList<Int>> = mutableListOf()
    var path:MutableList<MutableList<MutableList<MutableList<Int>>>> = mutableListOf()
    @RequiresApi(Build.VERSION_CODES.N)
    var pq: PriorityQueue<Tuple2> = PriorityQueue<Tuple2>(ComparatorTuple)
    var weight:MutableList<MutableList<Int>> = mutableListOf()
    var sized: Int=0
    var srcx: Int=0
    var srcy: Int=0
    var desx: Int=0
    var desy: Int=0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gradientDrawableValueSetter()
        createButtonGrid()
        paintAllButtonsWhite()
        search.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
            findPath()}
        }
    }
    fun weightMaker() {
        for (i in 0..(sizeb)) {
            var weightvec: MutableList<Int> = mutableListOf()
            for (j in 0..(size)) {
                weightvec.add(1)
            }
            weight.add(weightvec)
        }
    }
    suspend fun mainer() {
        //buttons[0][0].setBackgroundColor(Color.parseColor("#000000"))
        for (i in 0..(sizeb)) {
            var row: MutableList<MutableList<MutableList<Int>>> = mutableListOf()
            for (j in 0..(size)) {
                var point: MutableList<MutableList<Int>> = mutableListOf()
                if (i == 0 && j == 0) {
                    var neigh1: MutableList<Int> = mutableListOf()
                    neigh1.add(i + 1)
                    neigh1.add(j)
                    neigh1.add(weight[i + 1][j])
                    point.add(neigh1)

                    var neigh2: MutableList<Int> = mutableListOf()
                    neigh2.add(i)
                    neigh2.add(j + 1)
                    neigh2.add(weight[i][j + 1])
                    point.add(neigh2)

                } else if (i == sizeb  && j == 0) {
                    var neigh1: MutableList<Int> = mutableListOf()
                    neigh1.add(i - 1)
                    neigh1.add(j)
                    neigh1.add(weight[i - 1][j])
                    point.add(neigh1)

                    var neigh2: MutableList<Int> = mutableListOf()
                    neigh2.add(i)
                    neigh2.add(j + 1)
                    neigh2.add(weight[i][j + 1])
                    point.add(neigh2)

                } else if (i == 0 && j == size) {
                    var neigh1: MutableList<Int> = mutableListOf()
                    neigh1.add(i + 1)
                    neigh1.add(j)
                    neigh1.add(weight[i + 1][j])
                    point.add(neigh1)

                    var neigh2: MutableList<Int> = mutableListOf()
                    neigh2.add(i)
                    neigh2.add(j - 1)
                    neigh2.add(weight[i][j - 1])
                    point.add(neigh2)

                } else if (i == sizeb  && j == size ) {
                    var neigh1: MutableList<Int> = mutableListOf()
                    neigh1.add(i - 1)
                    neigh1.add(j)
                    neigh1.add(weight[i - 1][j])
                    point.add(neigh1)
                    var neigh2: MutableList<Int> = mutableListOf()

                    neigh2.add(i)
                    neigh2.add(j - 1)
                    neigh2.add(weight[i][j - 1])
                    point.add(neigh2)

                } else if (i == 0) {
                    var neigh1: MutableList<Int> = mutableListOf()
                    neigh1.add(i + 1)
                    neigh1.add(j)
                    neigh1.add(weight[i + 1][j])
                    point.add(neigh1)
                    var neigh2: MutableList<Int> = mutableListOf()

                    neigh2.add(i)
                    neigh2.add(j - 1)
                    neigh2.add(weight[i][j - 1])
                    point.add(neigh2)
                    var neigh3: MutableList<Int> = mutableListOf()

                    neigh3.add(i)
                    neigh3.add(j + 1)
                    neigh3.add(weight[i][j + 1])
                    point.add(neigh3)

                } else if (i == sizeb) {
                    var neigh1: MutableList<Int> = mutableListOf()
                    neigh1.add(i - 1)
                    neigh1.add(j)
                    neigh1.add(weight[i - 1][j])
                    point.add(neigh1)
                    var neigh2: MutableList<Int> = mutableListOf()

                    neigh2.add(i)
                    neigh2.add(j - 1)
                    neigh2.add(weight[i][j - 1])
                    point.add(neigh2)
                    var neigh3: MutableList<Int> = mutableListOf()

                    neigh3.add(i)
                    neigh3.add(j + 1)
                    neigh3.add(weight[i][j + 1])
                    point.add(neigh3)

                } else if (j == 0) {
                    var neigh1: MutableList<Int> = mutableListOf()
                    neigh1.add(i - 1)
                    neigh1.add(j)
                    neigh1.add(weight[i - 1][j])
                    point.add(neigh1)
                    var neigh2: MutableList<Int> = mutableListOf()
                    neigh2.add(i + 1)
                    neigh2.add(j)
                    neigh2.add(weight[i + 1][j])
                    point.add(neigh2)
                    var neigh3: MutableList<Int> = mutableListOf()

                    neigh3.add(i)
                    neigh3.add(j + 1)
                    neigh3.add(weight[i][j + 1])
                    point.add(neigh3)

                } else if (j == size) {
                    var neigh1: MutableList<Int> = mutableListOf()
                    neigh1.add(i - 1)
                    neigh1.add(j)
                    neigh1.add(weight[i - 1][j])
                    point.add(neigh1)
                    var neigh2: MutableList<Int> = mutableListOf()

                    neigh2.add(i + 1)
                    neigh2.add(j)
                    neigh2.add(weight[i + 1][j])
                    point.add(neigh2)
                    var neigh3: MutableList<Int> = mutableListOf()

                    neigh3.add(i)
                    neigh3.add(j - 1)
                    neigh3.add(weight[i][j - 1])
                    point.add(neigh3)

                } else {
                    var neigh1: MutableList<Int> = mutableListOf()
                    neigh1.add(i - 1)
                    neigh1.add(j)
                    neigh1.add(weight[i - 1][j])
                    point.add(neigh1)
                    var neigh2: MutableList<Int> = mutableListOf()

                    neigh2.add(i + 1)
                    neigh2.add(j)
                    neigh2.add(weight[i + 1][j])
                    point.add(neigh2)
                    var neigh3: MutableList<Int> = mutableListOf()

                    neigh3.add(i)
                    neigh3.add(j - 1)
                    neigh3.add(weight[i][j - 1])
                    point.add(neigh3)
                    var neigh4: MutableList<Int> = mutableListOf()

                    neigh4.add(i)
                    neigh4.add(j + 1)
                    neigh4.add(weight[i][j + 1])
                    point.add(neigh4)

                }
                row.add(point)
            }
            v.add(row)
        }

        for (i in 0..(v.size - 1)) {
            var disvec: MutableList<Int> = mutableListOf()
            for (j in 0..(v[i].size - 1)) {
                disvec.add(100)
            }
            dis.add(disvec)
        }

        for (i in 0..(v.size - 1)) {
            var row: MutableList<MutableList<MutableList<Int>>> = mutableListOf()
            for (j in 0..(v[i].size - 1)) {
                var p: MutableList<MutableList<Int>> = mutableListOf()
                row.add(p)
            }
            path.add(row)
        }



        var temp = Tuple2(0, srcx, srcy)
        pq.add(temp)
        dis[srcx][srcy] = 0
        while (!pq.isEmpty()) {
            var u = pq.peek()
            pq.remove()
            var x = u.x
            var y = u.y
            var d = u.d
            //tester.append(x.toString()+" "+y.toString()+"\n")
            if ((x == desx) and (y == desy)) {
                break
            }
            for (i in 0..(v[x][y].size -1)) {

                if (dis[v[x][y][i][0]][v[x][y][i][1]] > ((dis[x][y]) + (v[x][y][i][2]))) {
                    if ((v[x][y][i][0] != desx) or  (v[x][y][i][1] != desy)) {
                    buttons[v[x][y][i][0]][v[x][y][i][1]].background=gdForRedColor
                    delay(100)}
                    dis[v[x][y][i][0]][v[x][y][i][1]] = ((dis[x][y]) + (v[x][y][i][2]))

                    path[v[x][y][i][0]][v[x][y][i][1]].removeAll(path[v[x][y][i][0]][v[x][y][i][1]])

                    path[v[x][y][i][0]][v[x][y][i][1]] =
                        mutableListOf<MutableList<Int>>().apply { addAll(path[x][y]) }
                    var tem: MutableList<Int> = mutableListOf()
                    tem.add(x)
                    tem.add(y)
                    path[v[x][y][i][0]][v[x][y][i][1]].add(tem)
                    var dd: Int = dis[v[x][y][i][0]][v[x][y][i][1]]
                    var xx: Int = v[x][y][i][0]
                    var yy: Int = v[x][y][i][1]
                    var temp2 = Tuple2(dd, xx, yy)
                    pq.add(temp2)
                }
            }
        }
    }
    private suspend fun findPath() {

        sized=size+1
        srcx=butsrcx
        srcy=butsrcy
        desx=butdesx
        desy=butdesy
        var job2=GlobalScope.launch(Dispatchers.Main) {
        weightMaker()}
        job2.join()
        for (i in 0..sizeb){
            for(j in 0..size){
                if(buttonStatusKeeper[i].get(buttons[i][j])==1){
                    weight[i][j]=1000
                }
            }
        }
        var job1=GlobalScope.launch(Dispatchers.Main) {
        mainer()}
        job1.join()
        var pather=path
        //tester.append(srcx.toString()+" "+srcy.toString()+butsrcx.toString()+" "+butsrcy.toString()+"\n")
        //tester.append(desx.toString()+" "+desy.toString()+butdesx.toString()+" "+butdesy.toString()+"\n")
        for (i in 1..(pather[butdesx][butdesy].size-1)){

            buttons[pather[butdesx][butdesy][i][0]][pather[butdesx][butdesy][i][1]].background=gdForGreenColor

            //tester.append(pather[butdesx][butdesy][i][0].toString()+" "+pather[butdesx][butdesy][i][1].toString()+"\n")



        }
        if(pather[butdesx][butdesy].size==0){
            Toast.makeText(this,"NO PATH FOUND", Toast.LENGTH_LONG).show()
        }
    }
    private fun gradientDrawableValueSetter() {
        gdForRedColor.setColor(Color.parseColor("#FF0000"))
        gdForRedColor.cornerRadius=10.0f
        gdForRedColor.setStroke(1,Color.parseColor("#000000"))

        gdForBrownColor.setColor(Color.parseColor("#A52A2A"))
        gdForBrownColor.cornerRadius=10.0f
        gdForBrownColor.setStroke(1,Color.parseColor("#000000"))

        gdForGreenColor.setColor(Color.parseColor("#008000"))
        gdForGreenColor.cornerRadius=10.0f
        gdForGreenColor.setStroke(1,Color.parseColor("#000000"))

        gdForWhiteColor.setColor(Color.parseColor("#FFFFFF"))
        gdForWhiteColor.cornerRadius=10.0f
        gdForWhiteColor.setStroke(1,Color.parseColor("#000000"))
    }
    private fun paintAllButtonsWhite() {
        for (i in 0..sizeb)
        {
            for (j in 0..(size))
            {
                buttons[i][j].background=gdForWhiteColor
            }
        }
    }
    private fun createButtonGrid() {

        for (i in 0..sizeb) {

            val arrayLinearLayout = LinearLayout(this)
            arrayLinearLayout.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,1.0f
            )
            arrayLinearLayout.orientation = LinearLayout.HORIZONTAL
            //arrayLinearLayout.setPadding(2,2,2,2)

            val buttonStatusRow: MutableMap<Button,Int> = mutableMapOf()
            val buttonRow:MutableList<Button> = mutableListOf()
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
                        val buttonStatus = buttonStatusRow.get(button)
                        if (buttonStatus == 0) {
                            button.background=gdForBrownColor
                            buttonStatusRow.put(button, 1)
                        } else if (buttonStatus == 1) {
                            button.background=gdForWhiteColor
                            buttonStatusRow.put(button, 0)
                        }
                    }
                }
                buttonStatusRow.put(button,0)
                buttonRow.add(button)
                arrayLinearLayout.addView(button)
            }
            buttonStatusKeeper.add(buttonStatusRow)
            buttons.add(buttonRow)
            screen.addView(arrayLinearLayout)
        }
    }
}











