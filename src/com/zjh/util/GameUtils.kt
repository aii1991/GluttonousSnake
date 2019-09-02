package com.zjh.util

import com.zjh.BLOCK_LENGTH
import com.zjh.ui.widget.Food
import java.awt.Point

/**
 * Created by ${juha} on 2019/9/2.
 *
 */
object GameUtils {
    fun generatePosition(): Point {
        val x = (Math.random() * (BLOCK_LENGTH - 1)).toInt() * Food.widthAndHeight
        val y = (Math.random() * (BLOCK_LENGTH - 3)).toInt() * Food.widthAndHeight
        println("x=>$x,y=>$y")
        return Point(x,y)
    }
}