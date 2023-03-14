package app.allever.android.sample.designpattern.behavior.command

import app.allever.android.sample.designpattern.behavior.command.demo1.*

class CommandClient {
    fun execute() {
        val receiver = Receiver()
        val command = ConcreteCommand(receiver)
        val invoker = Invoker(command)
        invoker.executeAction()
    }

    fun executeRussiaBlockGame() {
        val russiaBlockGame = RussiaBlockGame()

        val leftOption =  LeftOption(russiaBlockGame)
        val rightOption = RightOption(russiaBlockGame)

        val button = Button()
        button.leftOption = leftOption
        button.rightOption = rightOption

        button.left()
        button.right()
    }
}