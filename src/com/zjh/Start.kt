package com.zjh

import com.zjh.ui.MainFrame
import com.zjh.ui.widget.Food

/**
 * Created by ${juha} on 2019/8/30.
 *
 */
const val BLOCK_LENGTH:Int = 20
const val SCORE:Int = 10 //每吃一个食物的得分
const val DIFFICULTY:Int = 1 //难度 1-10

fun main(args: Array<String>){
    MainFrame(Food.widthAndHeight * BLOCK_LENGTH,Food.widthAndHeight * BLOCK_LENGTH)
}
