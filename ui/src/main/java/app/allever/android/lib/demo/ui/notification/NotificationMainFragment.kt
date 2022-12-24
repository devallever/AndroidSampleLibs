package app.allever.android.lib.demo.ui.notification

import android.app.NotificationManager
import android.os.Build
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.ListViewModel
import app.allever.android.lib.common.adapter.TextAdapter
import app.allever.android.lib.common.databinding.FragmentListBinding
import app.allever.android.lib.core.ext.toast
import app.allever.android.lib.core.helper.NotificationHelper
import app.allever.android.lib.demo.R

class NotificationMainFragment : ListFragment<FragmentListBinding, ListViewModel, String>() {
    private val CHANNEL_ID = "通知渠道ID"
    override fun getAdapter() = TextAdapter()

    override fun getList() = mutableListOf(
        "创建通知渠道(8.0)",
        "判断通知权限",
        "打开通知设置",
        "基础通知"
    )

    override fun onItemClick(position: Int, item: String) {
        when (position) {
            0 -> {
                createChannelNotification()
            }
            1 -> {
                toast("${NotificationHelper.manager.areNotificationsEnabled()}")
            }
            2 -> {
                NotificationHelper.jumpSetting()
            }
            3 -> {
                showBaseNotification(position)
            }
        }
    }

    private fun createChannelNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val voipChannel = NotificationChannelCompat
                .Builder("通知渠道ID", NotificationManager.IMPORTANCE_HIGH)
                .setName("渠道名称")
                .setDescription("渠道描述")
                .build()
            NotificationHelper.manager.createNotificationChannel(voipChannel)
        }
    }

    private fun showBaseNotification(notificationId: Int) {
        val builder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setSmallIcon(R.drawable.icon_album_video_preview_play)
            .setContentTitle("通知标题")
            .setContentText("通知内容")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
        NotificationHelper.manager.notify(notificationId, builder.build())
    }
}