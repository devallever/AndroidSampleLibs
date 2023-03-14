package app.allever.android.sample.designpattern.behavior.command

/**
 * 请求者类：持有相应命令引用
 */
class Invoker(private val command: Command) {
    fun executeAction() {
        command.execute()
    }
}