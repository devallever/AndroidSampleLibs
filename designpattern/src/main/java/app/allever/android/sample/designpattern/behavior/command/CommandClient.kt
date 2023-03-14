package app.allever.android.sample.designpattern.behavior.command

class CommandClient {
    fun execute() {
        val receiver = Receiver()
        val command = ConcreteCommand(receiver)
        val invoker = Invoker(command)
        invoker.executeAction()
    }
}