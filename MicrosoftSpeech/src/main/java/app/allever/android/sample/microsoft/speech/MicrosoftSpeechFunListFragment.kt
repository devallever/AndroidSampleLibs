package app.allever.android.sample.microsoft.speech

import android.Manifest
import app.allever.android.lib.common.FragmentActivity
import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.ListViewModel
import app.allever.android.lib.common.adapter.TextClickAdapter
import app.allever.android.lib.common.adapter.bean.TextClickItem
import app.allever.android.lib.common.databinding.FragmentListBinding
import app.allever.android.lib.core.ext.log

class MicrosoftSpeechFunListFragment: ListFragment<FragmentListBinding, ListViewModel, TextClickItem>() {
    override fun getAdapter() = TextClickAdapter()

    override fun getList(): MutableList<TextClickItem> = mutableListOf(
        TextClickItem("MicrosoftSpeechBaseSample") {
            PermissionHelper.request(requireActivity(), object : PermissionListener {
                override fun onAllGranted() {
                    log("onAllGranted: ")
                    FragmentActivity.start<MicrosoftSpeechBaseSampleFragment>(it.title)
                }
            }, Manifest.permission.RECORD_AUDIO)
        },
    )
}