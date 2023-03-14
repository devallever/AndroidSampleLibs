package app.allever.android.sample.designpattern.behavior.command.demo1

/**
 * 具体命令类，持有Receiver
 */
class RightOption(private val russiaBlockGame: RussiaBlockGame) : Option {
    override fun execute() {
        russiaBlockGame.toRight()
    }
}