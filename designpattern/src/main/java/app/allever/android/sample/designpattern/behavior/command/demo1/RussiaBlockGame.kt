package app.allever.android.sample.designpattern.behavior.command.demo1

import app.allever.android.lib.core.ext.log

/**
 * Receiver类，执行具体方法
 */
class RussiaBlockGame {
    fun toLeft() {
        log("左移")
    }
    fun toRight(){
        log("右移")
    }
}