package app.allever.android.sample.designpattern.behavior.command

/**
 * 具体命令类：持有接受者引用，调用接受者的方法
 */
class ConcreteCommand(private val receiver: Receiver) : Command {
    override fun execute() {
        receiver.invoke()
    }
}