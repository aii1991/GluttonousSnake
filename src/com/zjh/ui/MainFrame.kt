package com.zjh.ui

import com.zjh.SCORE
import com.zjh.ui.widget.Food
import com.zjh.ui.widget.Snake
import java.awt.Graphics
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import javax.swing.JOptionPane

/**
 * Created by ${juha} on 2019/8/30.
 *  游戏主界面
 */
class MainFrame(width:Int,height:Int) : DoubleBufferFrame(width,height), KeyListener {
    var isConfirmStart = false; //是否确认开始
    var food: Food = Food()
    var snake: Snake = Snake()

    init {
        initData()
        title = "贪吃蛇"
        addKeyListener(this)
        if(JOptionPane.showOptionDialog(this,"点击确定开始游戏","提示",JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE,null,null,null) == 0){
            isConfirmStart = true
        }
    }

    fun initData(){
        food.generateFood(snake.cellList)
        snake.generatePosition()
    }

    override fun updateAttr() {
        if(!isConfirmStart){
            return
        }
        if(isOver()){
            isStopDrawThread = true;
            if(JOptionPane.showOptionDialog(this,"游戏结束,得分${(snake.getCellCount()-1) * SCORE},是否重新开始","提示",JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE,null,null,null) == 0){
                snake.initCellList()
                initData()
                isStopDrawThread = false;
            }
            return
        }
        if(snake.tryEat(food)){
            food.generateFood(snake.cellList)
        }
        snake.walk()
    }

    /**
     * 蛇撞墙或着咬到自己则返回true,游戏结束
     */
    fun isOver(): Boolean{
        return isCrashWall() || snake.biteYourself()
    }

    /**
     * 蛇是否碰撞到墙
     */
    fun isCrashWall(): Boolean{
        val cell:Snake.Cell = snake.getHeaderCell();
        return cell.x < contentPane.x || cell.x + cell.width > contentPane.width || cell.y < contentPane.y || cell.y + cell.height > contentPane.height
    }


    override fun dPaint(g: Graphics?) {
        food.draw(g)
        snake.draw(g)
    }

    override fun keyTyped(e: KeyEvent?) {

    }

    override fun keyPressed(e: KeyEvent?) {
        isStopDrawThread = true

        when(e!!.keyCode){
            37-> snake.switchDirection(Snake.Direction.LEFT)
            38-> snake.switchDirection(Snake.Direction.TOP)
            39-> snake.switchDirection(Snake.Direction.RIGHT)
            40-> snake.switchDirection(Snake.Direction.BOTTOM)
        }
        repaint()
    }

    override fun keyReleased(e: KeyEvent?) {
        isStopDrawThread = false
    }

}
