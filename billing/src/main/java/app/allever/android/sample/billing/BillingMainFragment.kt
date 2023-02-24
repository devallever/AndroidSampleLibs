package app.allever.android.sample.billing

import androidx.lifecycle.lifecycleScope
import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.ListViewModel
import app.allever.android.lib.common.adapter.TextClickAdapter
import app.allever.android.lib.common.adapter.bean.TextClickItem
import app.allever.android.lib.common.databinding.FragmentListBinding
import app.allever.android.sample.billing.helper.BillingHelper
import kotlinx.coroutines.launch

class BillingMainFragment : ListFragment<FragmentListBinding, ListViewModel, TextClickItem>() {

    override fun init() {
        super.init()
    }

    override fun getAdapter() = TextClickAdapter()

    override fun getList() = mutableListOf(
        TextClickItem("连接") {
            BillingHelper.connect()
        },
        TextClickItem("断开连接") {
            BillingHelper.disConnect()
        },
        TextClickItem("获取商品详情") {
            lifecycleScope.launch {
                BillingHelper.getProductDetails(mutableListOf("weekly_pro_access"), null)
            }
        },
        TextClickItem("获取商品订阅状态") {
            lifecycleScope.launch {
                BillingHelper.checkScribeStatus { success, code, message ->

                }
            }
        },
        TextClickItem("订阅商品") {
            lifecycleScope.launch {
                BillingHelper.subScribe(
                    requireActivity(),
                    "weekly_pro_access"
                ) { success, code, message ->

                }
            }
        },
    )
}