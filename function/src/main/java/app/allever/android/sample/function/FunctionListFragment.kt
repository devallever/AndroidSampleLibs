package app.allever.android.sample.function

import app.allever.android.lib.common.FragmentActivity
import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.ListViewModel
import app.allever.android.lib.common.adapter.TextAdapter
import app.allever.android.lib.common.databinding.FragmentListBinding
import app.allever.android.lib.core.helper.ActivityHelper
import app.allever.android.sample.function.im.IMMainFragment
import app.allever.android.sample.function.theme.ThemeMainActivity
import com.chad.library.adapter.base.BaseQuickAdapter

class FunctionListFragment : ListFragment<FragmentListBinding, ListViewModel, String>() {
    override fun getAdapter(): BaseQuickAdapter<String, *> = TextAdapter()

    override fun getList() = mutableListOf("主题", "IM")

    override fun onItemClick(position: Int, item: String) {
        when (position) {
            0 -> {
                ActivityHelper.startActivity<ThemeMainActivity> { }
            }
            1 -> {
                FragmentActivity.start<IMMainFragment>("IM")
            }
            else -> {

            }
        }
    }
}