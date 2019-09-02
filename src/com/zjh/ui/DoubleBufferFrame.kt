package com.zjh.ui

import com.zjh.DIFFICULTY
import java.awt.*
import javax.swing.JFrame

/**
 * Created by ${juha} on 2019/9/2.
 *  实现双缓存绘制的frame
 */
abstract class DoubleBufferFrame(width: Int=600,height: Int=600) : JFrame(){
    private lateinit var updateThread: UpdateThread;
    private var iBuff: Image? = null
    private var gBuffer: Graphics? = null
    var isStopDrawThread:Boolean = false;

    init {
        size = Dimension(width, height)
        setLocationRelativeTo(null)

        isVisible = true
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        updateThread = UpdateThread((1000/DIFFICULTY).toLong())
        updateThread.start()
    }

    override fun paint(g: Graphics?) {
        if(iBuff == null){
            iBuff = createImage(size.width,size.height)
            gBuffer = iBuff!!.graphics;
        }
        gBuffer!!.color = background
        gBuffer!!.fillRect(0,0,size.width,size.height)
        super.paint(g)

        updateAttr()
        dPaint(gBuffer)
        g!!.drawImage(iBuff,insets.left,insets.top,this)
    }

    /**
     * 更新绘制属性
     */
    abstract fun updateAttr();

    /**
     * 双缓存绘制
     */
    abstract fun dPaint(g: Graphics?);


    inner class UpdateThread(timeMillis:Long = 500): Thread(){
        val timeMillis:Long = timeMillis

        override fun run() {
            super.run()
            while(true){
                sleep(this.timeMillis)
                if(!isStopDrawThread){
                    this@DoubleBufferFrame.repaint()
                }
            }
        }
    }
}
