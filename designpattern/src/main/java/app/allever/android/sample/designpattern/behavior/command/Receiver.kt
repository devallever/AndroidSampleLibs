package app.allever.android.sample.designpattern.behavior.command

import app.allever.android.lib.core.ext.log

/**
 * 接受者类：真正执行的具体方法
 */
class Receiver {
    fun invoke() {
        log("接受者类：真正执行的具体方法")
    }
}