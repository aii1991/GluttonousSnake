package com.zjh.ui.widget

import com.zjh.util.GameUtils
import java.awt.Color
import java.awt.Graphics
import java.util.*

/**
 * Created by ${juha} on 2019/9/2.
 *
 */
class Snake() : View{
    enum class Direction{ //方向枚举
        TOP,RIGHT,BOTTOM,LEFT
    }

    var cellList: ArrayList<Cell> = ArrayList() //蛇的每一节列表
    var color: Color = Color.GREEN //蛇的颜色

    private var inflectionPoint: ArrayList<Cell> = ArrayList() //记录发生转向的蛇头

    init {
        initCellList()
    }

    fun initCellList(){
        cellList.clear()
        cellList.add(Cell(0,0, Direction.LEFT))
    }

    /**
     * 检查是否成功进食,是=增加蛇身长度,否则不做处理
     */
    fun tryEat(food: Food): Boolean{
        if(cellList[0].x == food.x && cellList[0].y == food.y){
            val lastCell = cellList[cellList.size - 1];
            when(lastCell.direction){
                Direction.LEFT-> cellList.add(Cell(lastCell.x + Food.widthAndHeight,lastCell.y,lastCell.direction))
                Direction.RIGHT-> cellList.add(Cell(lastCell.x - Food.widthAndHeight,lastCell.y,lastCell.direction))
                Direction.TOP-> cellList.add(Cell(lastCell.x,lastCell.y + Food.widthAndHeight,lastCell.direction))
                Direction.BOTTOM-> cellList.add(Cell(lastCell.x,lastCell.y - Food.widthAndHeight,lastCell.direction))
            }
            return true
        }
        return false
    }


    fun walk(){
        for(index in cellList.indices){
            val cell:Cell = cellList[index]
            if(index != 0){
                for(pCell in inflectionPoint){ //判断蛇身的每一个正方形是否经过蛇头转向点,是改变方向
                    if(cell.x == pCell.x && cell.y == pCell.y){
                        cell.direction = pCell.direction
                        println("change direction")
                        if(index == cellList.size-1){ //当蛇尾也经过蛇头转向点时,从列表删除转向信息
                            inflectionPoint.remove(pCell)
                        }
                        break;
                    }
                }
            }


            when(cell.direction){ //根据方向修改坐标,进行移动操作
                Snake.Direction.TOP-> cell.y -= Food.widthAndHeight
                Snake.Direction.RIGHT-> cell.x += Food.widthAndHeight
                Snake.Direction.BOTTOM-> cell.y += Food.widthAndHeight
                Snake.Direction.LEFT-> cell.x -= Food.widthAndHeight
            }
        }
    }

    /**
     * 根据输入的#{switchDirection}进行转向操作
     * @param switchDirection 方向枚举
     */
    fun switchDirection(switchDirection:Snake.Direction){
        val cDirection = cellList[0].direction
        when(switchDirection){
            Snake.Direction.LEFT->{
                if(cDirection != Snake.Direction.RIGHT){
                    cellList[0].direction = Snake.Direction.LEFT
                }
            }

            Snake.Direction.TOP->{
                if(cDirection != Snake.Direction.BOTTOM){
                    cellList[0].direction = Snake.Direction.TOP
                }
            }

            Snake.Direction.RIGHT->{
                if(cDirection != Snake.Direction.LEFT){
                    cellList[0].direction = Snake.Direction.RIGHT
                }
            }

            Snake.Direction.BOTTOM->{
                if(cDirection != Snake.Direction.TOP){
                    cellList[0].direction = Snake.Direction.BOTTOM
                }
            }
        }
        if(cellList.size > 1){
            inflectionPoint.add(Cell(cellList[0].x,cellList[0].y,cellList[0].direction))
        }
    }

    /**
     * 获取蛇头
     */
    fun getHeaderCell(): Cell{
        return cellList[0]
    }

    fun getCellCount(): Int{
        return cellList.size
    }

    /**
     * 是否咬到自己
     */
    fun biteYourself(): Boolean{
        for(index in cellList.indices){
            if(index != 0){
                val cell = cellList[index]
                if(getHeaderCell().x == cell.x && getHeaderCell().y == cell.y){
                   return true
                }
            }
        }
        return false
    }

    fun generatePosition(){
        val point = GameUtils.generatePosition()
        cellList[0].x = point.x
        cellList[0].y = point.y
        if(point.x <= Food.widthAndHeight * 2){
            cellList[0].direction = Direction.RIGHT
        }
    }

    override fun draw(g: Graphics?) {
        g!!.color = color
        for(cell in cellList){
            g.fillRect(cell.x,cell.y,cell.width,cell.height)
        }
    }

    /**
     * 蛇的每一节,每吃一个食物涨一节
     */
    data class Cell(var x:Int,var y:Int,var direction: Direction,var width: Int = Food.widthAndHeight,var height: Int = Food.widthAndHeight)
}