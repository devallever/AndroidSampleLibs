package app.allever.android.sample.thirtypart.ui

import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.ListViewModel
import app.allever.android.lib.common.adapter.TextClickAdapter
import app.allever.android.lib.common.adapter.bean.TextClickItem
import app.allever.android.lib.common.databinding.FragmentListBinding
import okhttp3.*
import java.io.IOException

class OkHttpFragment : ListFragment<FragmentListBinding, ListViewModel, TextClickItem>() {
    override fun getAdapter() = TextClickAdapter()

    override fun getList() = mutableListOf<TextClickItem>()

    private fun initOkHttp() {
        //1.
        val client = OkHttpClient.Builder()
            .build()
        //2.
        val request = Request.Builder()
            .build()
        //3.
        val call = client.newCall(request)

        //4.1 同步请求
        val response = call.execute()

        //4.2 异步请求
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {

            }
        })
    }
}