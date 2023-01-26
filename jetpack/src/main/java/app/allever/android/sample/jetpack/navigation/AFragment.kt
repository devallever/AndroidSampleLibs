package app.allever.android.sample.jetpack.navigation

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import androidx.navigation.fragment.findNavController
import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.adapter.TextAdapter
import app.allever.android.lib.core.helper.NotificationHelper
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.sample.jetpack.R
import app.allever.android.sample.jetpack.databinding.FragmentABinding

class AFragment : ListFragment<FragmentABinding, BaseViewModel, String>() {

    override fun getTitle()  = "Navigation"

    override fun getAdapter() = TextAdapter()

    override fun getList() = mutableListOf(
         "页面基础跳转",
        "显式深链接"
    )

    override fun onItemClick(position: Int, item: String) {
        when(position) {
            0 -> {
                val bundle = Bundle()
                bundle.putString("userId", "001")
                findNavController().navigate(R.id.action2B, bundle)
            }
            1 -> {
                val bundle = Bundle()
                bundle.putString("userId", "001")
                val pendingIntent = NavDeepLinkBuilder(requireContext())
                    .setGraph(R.navigation.nav_graph)
                    .setDestination(R.id.bFragment)
                    .setArguments(bundle)
                    .createPendingIntent()

                NotificationHelper.notify(requireContext(), "通知渠道ID") {
                    it.setSmallIcon(R.drawable.icon_edit_music_online_play)//状态栏用到
                        .setLargeIcon(
                            BitmapFactory.decodeResource(
                                resources,
                                R.drawable.icon_edit_music_online_play
                            )
                        ) //右边
                        .setContentTitle("Navigation")
                        .setContentText("显式深链接")
                        .setContentIntent(pendingIntent)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setAutoCancel(true)
                }
            }
        }
    }
}