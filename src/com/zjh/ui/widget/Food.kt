package com.zjh.ui.widget

import com.zjh.util.GameUtils
import java.awt.Color
import java.awt.Graphics
import java.util.*

/**
 * Created by ${juha} on 2019/9/2.
 *
 */
class Food() : View{
    companion object {
        val widthAndHeight = 20
    }

    var isShow: Boolean = true
    var color: Color = Color.RED
    var x: Int = 0;
    var y: Int = 0;


    override fun draw(g: Graphics?) {
        g!!.color = if(isShow) color else null
        isShow = !isShow
        g.fillRect(x,y,widthAndHeight,widthAndHeight)
    }

    /**
     * 生成食物
     */
    fun generateFood(cellList: ArrayList<Snake.Cell>){
        while(true){
            val point = GameUtils.generatePosition()
            var isSuccess = true;
            for(cell in cellList){ //排除小蛇的坐标
                if(cell.x == point.x && cell.y == point.y){
                    isSuccess = false
                }
            }
            if(isSuccess){
                x = point.x
                y = point.y
                break
            }
        }
    }

}