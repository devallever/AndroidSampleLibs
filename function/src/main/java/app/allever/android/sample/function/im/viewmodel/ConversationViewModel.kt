package app.allever.android.sample.function.im.viewmodel

import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.sample.function.im.constant.ActionType
import app.allever.android.sample.function.im.message.BaseMessage
import app.allever.android.sample.function.im.message.TextMessage
import app.allever.android.sample.function.im.ui.adapter.MessageAdapter
import app.allever.android.sample.function.im.user.UserInfo

class ConversationViewModel : BaseViewModel() {
    val userMe = UserInfo()
    val userOther = UserInfo()

    val messageAdapter = MessageAdapter()
    val messageList = mutableListOf<BaseMessage>()

    override fun init() {
        initTestData()
    }

    private fun initTestData() {
        userMe.avatar = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Flmg.jj20.com%2Fup%2Fallimg%2F1112%2F030919135303%2F1Z309135303-1-1200.jpg&refer=http%3A%2F%2Flmg.jj20.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1671294148&t=d7b7ce51004c24fc155d1fed6b6d84bd"
        userMe.nickname = "小猫咪666"
        userOther.avatar = "https://img2.baidu.com/it/u=1801140900,2951304091&fm=253&fmt=auto&app=120&f=JPEG?w=1280&h=800"
        userOther.nickname = "倾国倾城"
        val msg1 = TextMessage()
        msg1.actionType = ActionType.RECEIVE
        msg1.user = userOther
        msg1.content = "你好美女，能交个朋友吗？"
        messageList.add(msg1)

        val msg2 = TextMessage()
        msg2.user = userMe
        msg2.actionType = ActionType.SEND
        msg2.content = "可以的呀，你是哪里人？"
        messageList.add(msg2)

        val msg3 = TextMessage()
        msg3.user = userOther
        msg3.actionType = ActionType.RECEIVE
        msg3.content = "广州的，你呢？"
        messageList.add(msg3)
        messageList.add(msg3)
        messageList.add(msg3)
        messageList.add(msg3)

        messageList.add(msg3)
        messageList.add(msg3)
        messageList.add(msg3)
        messageList.add(msg3)

        messageAdapter.setList(messageList)
    }

    fun sendMessage(content: String) {
        val message = TextMessage()
        message.user = userMe
        message.actionType = ActionType.SEND
        message.content = content
        messageAdapter.addData(message)
    }

}