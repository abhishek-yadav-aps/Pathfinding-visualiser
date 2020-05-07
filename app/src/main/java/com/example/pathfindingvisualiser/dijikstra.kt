package com.example.pathfindingvisualiser

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*
class Tuple2(var d:Int,var x:Int,var y:Int){
    fun getx():Int{
        return x
    }
    fun gety():Int{
        return y
    }
    fun getd():Int{
        return d
    }
}
class ComparatorTuple {

    companion object : Comparator<Tuple2> {

        override fun compare(a: Tuple2, b: Tuple2): Int = when {
            a.d != b.d -> a.d - b.d
            else -> 1
        }
    }
}
class dijikstra
{
    var v:MutableList<MutableList<MutableList<MutableList<Int>>>> = mutableListOf()
    var dis:MutableList<MutableList<Int>> = mutableListOf()
    var path:MutableList<MutableList<MutableList<MutableList<Int>>>> = mutableListOf()
    @RequiresApi(Build.VERSION_CODES.N)
    var pq:PriorityQueue<Tuple2> = PriorityQueue<Tuple2>(ComparatorTuple)
    var weight:MutableList<MutableList<Int>> = mutableListOf()
    var size: Int=0
    var srcx: Int=0
    var srcy: Int=0
    var desx: Int=0
    var desy: Int=0
    fun weightMaker()
    {
        for (i in 0..(size - 1)) {
            var weightvec: MutableList<Int> = mutableListOf()
            for (j in 0..(size - 1)) {
                weightvec.add(1)
            }
            weight.add(weightvec)
        }
    }
    fun main() {






        for (i in 0..(size - 1)) {
            var row: MutableList<MutableList<MutableList<Int>>> = mutableListOf()
            for (j in 0..(size - 1)) {
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

                } else if (i == size - 1 && j == 0) {
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

                } else if (i == 0 && j == size - 1) {
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

                } else if (i == size - 1 && j == size - 1) {
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

                } else if (i == size - 1) {
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

                } else if (j == size - 1) {
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
                disvec.add(10000)
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
//            if (x == desx && y == desy) {
//                break
//            }
            for (i in 0..(v[x][y].size - 1)) {

                if (dis[v[x][y][i][0]][v[x][y][i][1]] > ((dis[x][y]) + (v[x][y][i][2]))) {
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

}
