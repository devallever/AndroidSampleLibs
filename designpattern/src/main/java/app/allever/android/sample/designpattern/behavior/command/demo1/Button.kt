package app.allever.android.sample.designpattern.behavior.command.demo1

/***
 * 请求者类：持有命令引用
 */
class Button {
    var leftOption: Option? = null
    var rightOption: Option? = null

    fun left() {
        leftOption?.execute()
    }

    fun right() {
        rightOption?.execute()
    }
}