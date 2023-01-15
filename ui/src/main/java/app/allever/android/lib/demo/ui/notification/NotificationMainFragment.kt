package app.allever.android.lib.demo.ui.notification

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.ListViewModel
import app.allever.android.lib.common.adapter.TextAdapter
import app.allever.android.lib.common.databinding.FragmentListBinding
import app.allever.android.lib.core.ext.log
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
        "基础通知",
        "超长文本通知",
        "大图通知",
        "媒体通知",
        "通信消息通知",
        "自定义通知界面"
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
            4 -> {
                showLongTextNotification(position)
            }
            5 -> {
                showBigImageNotification(position)
            }
            6 -> {
                toast(item)
            }
            7 -> {
                toast(item)
            }
            8 -> {
                showCustomDisplayNotification(position)
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
        val intent = Intent(requireContext(), NotificationActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(requireContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setSmallIcon(R.drawable.default_avatar)//状态栏用到
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.default_avatar)) //右边
            .setContentTitle("通知标题")
            .setContentText("通知内容")
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
        NotificationHelper.manager.notify(notificationId, builder.build())
    }

    private fun showLongTextNotification(notificationId: Int) {
        val intent = Intent(requireContext(), NotificationActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(requireContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setSmallIcon(R.drawable.default_avatar)//状态栏用到
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.default_avatar)) //右边
            .setContentTitle("长文本通知")
            .setContentText("基础通知内容")
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .setSummaryText("超长文本通知标题摘要")
                    .setBigContentTitle("超长文本通知标题")
                    .bigText("超长文本通知内容: \uD83D\uDD25\uD83D\uDD25\uD83D\uDD25Android基础组件库\uD83D\uDD25\uD83D\uDD25\uD83D\uDD25 打造一个简单易用的基础组件库，封装架构组件，网络组件，数据存储组件，图片加载组件，媒体库组件，运行时权限组件，全局捕获异常组件， 业务流程组件，日志组件，广告组件")
            )
        NotificationHelper.manager.notify(notificationId, builder.build())
    }

    private fun showBigImageNotification(notificationId: Int) {
        val intent = Intent(requireContext(), NotificationActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(requireContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setSmallIcon(R.drawable.default_avatar)//状态栏用到
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.default_avatar)) //右边
            .setContentTitle("大图通知")
            .setContentText("基础通知内容")
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(BitmapFactory.decodeResource(resources, R.drawable.default_avatar))
                    .setBigContentTitle("大图通知标题")
                    .bigLargeIcon(
                        BitmapFactory.decodeResource(
                            resources,
                            R.drawable.default_avatar
                        )
                    )
                    .setSummaryText("大图通知摘要")
            )
        NotificationHelper.manager.notify(notificationId, builder.build())
    }

    private fun showMessagingNotification(notificationId: Int) {
    }

    @SuppressLint("RemoteViewLayout")
    private fun showCustomDisplayNotification(notificationId: Int) {
        val notificationView = layoutInflater.inflate(R.layout.layout_notification, null, false)

        // Get the layouts to use in the custom notification
        log("pkg = ${requireContext().packageName}")
        val notificationLayout =
            RemoteViews(requireContext().packageName, R.layout.layout_notification)
//        val notificationLayoutExpanded = RemoteViews(requireContext().packageName, R.layout.layout_notification)

        val intent = Intent(requireContext(), NotificationActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(requireContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setSmallIcon(R.drawable.default_avatar)//状态栏用到
//            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.default_avatar)) //右边
            .setContentTitle("自定义界面通知")
//            .setContentText("通知内容")
//            .setContent(notificationLayout)//兼容旧版
//            .setContentIntent(pendingIntent)
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setAutoCancel(true)
//            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(notificationLayout)// android 12
//            .setCustomBigContentView(notificationLayoutExpanded)
        NotificationHelper.manager.notify(notificationId, builder.build())
    }
}