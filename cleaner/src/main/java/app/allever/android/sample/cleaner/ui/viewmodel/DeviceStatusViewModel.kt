package app.allever.android.sample.cleaner.ui.viewmodel

import android.text.format.Formatter
import app.allever.android.lib.core.app.App
import app.allever.android.lib.core.helper.DeviceHelper
import app.allever.android.lib.mvvm.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeviceStatusViewModel : BaseViewModel() {

    suspend fun getCPUTemperature(): Float = withContext(Dispatchers.IO) {
        DeviceHelper.getThermalInfo()
        return@withContext DeviceHelper.getCpuTemInfo()
    }

    suspend fun getMemoryPercent(): Int = withContext(Dispatchers.IO) {
        val usedMemory = DeviceHelper.getUsedMemory() / 1024f / 1024f / 1024f //GB
        val totalMemory = DeviceHelper.getTotalMemory() / 1024f / 1024f / 1024f //GB
        return@withContext ((usedMemory / totalMemory) * 100).toInt()
    }

    suspend fun getUsedStorage(): String = withContext(Dispatchers.IO) {
        return@withContext Formatter.formatFileSize(
            App.context,
            DeviceHelper.getUsedInternalMemorySize()
        )
    }

    suspend fun getAllStorage(): String = withContext(Dispatchers.IO) {
        return@withContext Formatter.formatFileSize(
            App.context,
            DeviceHelper.getTotalInternalMemorySize()
        )
    }

    suspend fun getStoragePercent(): Int = withContext(Dispatchers.IO) {
        val total = DeviceHelper.getTotalInternalMemorySize() / 1024f / 1024f / 1024f //GB
        val free = DeviceHelper.getAvailableInternalMemorySize() / 1024f / 1024f / 1024f //GB
        return@withContext ((free / total) * 100).toInt()
    }
}