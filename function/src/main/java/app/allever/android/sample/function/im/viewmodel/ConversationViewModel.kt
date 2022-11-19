package app.allever.android.sample.function.im.viewmodel

import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.sample.function.R
import app.allever.android.sample.function.im.constant.ActionType
import app.allever.android.sample.function.im.message.BaseMessage
import app.allever.android.sample.function.im.message.ImageMessage
import app.allever.android.sample.function.im.message.TextMessage
import app.allever.android.sample.function.im.ui.adapter.ExpandAdapter
import app.allever.android.sample.function.im.ui.adapter.ExpandItem
import app.allever.android.sample.function.im.ui.adapter.MessageAdapter
import app.allever.android.sample.function.im.user.UserInfo

class ConversationViewModel : BaseViewModel() {
    val userMe = UserInfo()
    val userOther = UserInfo()

    val messageAdapter = MessageAdapter()
    val messageList = mutableListOf<BaseMessage>()
    val expandAdapter = ExpandAdapter()

    override fun init() {
        initTestData()
    }

    fun initExpandFunData() {
        val list = mutableListOf<ExpandItem>()
        list.add(ExpandItem(R.drawable.bottom_input_emo, "图片", ExpandItem.TYPE_IMAGE))
        list.add(ExpandItem(R.drawable.bottom_input_emo, "视频", ExpandItem.TYPE_VIDEO))
        list.add(ExpandItem(R.drawable.bottom_input_emo, "语音通话", ExpandItem.TYPE_AUDIO_CALL))
        list.add(ExpandItem(R.drawable.bottom_input_emo, "视频通话", ExpandItem.TYPE_VIDEO_CALL))
        list.add(ExpandItem(R.drawable.bottom_input_emo, "位置", ExpandItem.TYPE_LOCATION))
        expandAdapter.setList(list)
    }

    private fun initTestData() {
        userMe.avatar =
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F2019-05-28%2F5cecf6fe1ce3b.jpg&refer=http%3A%2F%2Fpic1.win4000.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1671440223&t=fc84daf5543856c1686bd29b9f2dbadc"
        userMe.nickname = "小猫咪666"
        userOther.avatar =
            "https://img2.baidu.com/it/u=1801140900,2951304091&fm=253&fmt=auto&app=120&f=JPEG?w=1280&h=800"
        userOther.nickname = "倾国倾城"
        val msg1 = TextMessage()
        msg1.actionType = ActionType.RECEIVE
        msg1.user = userOther
        msg1.content = "你好美女，能交个朋友吗？"
        messageList.add(0, msg1)

        val msg2 = TextMessage()
        msg2.user = userMe
        msg2.actionType = ActionType.SEND
        msg2.content = "可以的呀，你是哪里人？"
        messageList.add(0, msg2)

        val msg3 = TextMessage()
        msg3.user = userOther
        msg3.actionType = ActionType.RECEIVE
        msg3.content = "广州的"
        messageList.add(0, msg3)

        val msg4 = ImageMessage()
        msg4.user = userMe
        msg4.url = userMe.avatar
        msg4.width = 1
        messageList.add(0, msg4)

        val msg6 = TextMessage()
        msg6.user = userMe
        msg6.actionType = ActionType.SEND
        msg6.content = "这是我，好看么？可以看看你的照片吗？"
        messageList.add(0, msg6)

        val msg7 = TextMessage()
        msg7.user = userOther
        msg7.actionType = ActionType.RECEIVE
        msg7.content = "当然可以呀"
        messageList.add(0, msg7)

        val msg8 = ImageMessage()
        msg8.user = userOther
        msg8.actionType = ActionType.RECEIVE
        msg8.url = userOther.avatar
        msg8.height = 1
        messageList.add(0, msg8)


//        messageList.add(0, msg3)
//        messageList.add(0, msg3)
//        messageList.add(0, msg3)
//
//        messageList.add(0, msg3)
//        messageList.add(0, msg3)
//        messageList.add(0, msg3)
//        messageList.add(0, msg3)

        messageAdapter.setList(messageList)
    }

    fun sendMessage(content: String) {
        val message = TextMessage()
        message.user = userMe
        message.actionType = ActionType.SEND
        message.content = content
        messageAdapter.addData(0, message)
    }

}