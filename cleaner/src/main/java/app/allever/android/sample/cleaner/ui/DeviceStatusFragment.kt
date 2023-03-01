package app.allever.android.sample.cleaner.ui

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.ListViewModel
import app.allever.android.lib.common.adapter.TextDetailClickAdapter
import app.allever.android.lib.common.adapter.bean.TextDetailClickItem
import app.allever.android.lib.common.databinding.FragmentListBinding
import app.allever.android.sample.cleaner.ui.viewmodel.DeviceStatusViewModel
import kotlinx.coroutines.launch

class DeviceStatusFragment :
    ListFragment<FragmentListBinding, ListViewModel, TextDetailClickItem>() {

    private val deviceViewModel by viewModels<DeviceStatusViewModel>()

    override fun getAdapter() = TextDetailClickAdapter()

    override fun getList() = mutableListOf<TextDetailClickItem>()

    override fun onResume() {
        super.onResume()
        getDeviceInfo()
    }

    private fun getDeviceInfo() {
        lifecycleScope.launch {
            val list = mutableListOf<TextDetailClickItem>()
            val cpuItem = TextDetailClickItem()
            cpuItem.title = "CPU温度"
            cpuItem.detail = "${deviceViewModel.getCPUTemperature()} 度"
            list.add(cpuItem)

            val memoryItem = TextDetailClickItem()
            memoryItem.title = "内存"
            memoryItem.detail = "可用：${deviceViewModel.getMemoryPercent()}%"
            list.add(memoryItem)

            val storageItem = TextDetailClickItem()
            storageItem.title = "存储"
            storageItem.detail =
                "${deviceViewModel.getUsedStorage()}/${deviceViewModel.getAllStorage()}\n可用：${deviceViewModel.getStoragePercent()}%"
            list.add(storageItem)


            updateList(list)
        }
    }
}