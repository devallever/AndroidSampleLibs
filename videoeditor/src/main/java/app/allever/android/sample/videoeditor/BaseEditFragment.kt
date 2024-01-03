package app.allever.android.sample.videoeditor

import androidx.recyclerview.widget.GridLayoutManager
import app.allever.android.lib.common.BaseFragment
import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.ListViewModel
import app.allever.android.lib.common.adapter.TextClickAdapter
import app.allever.android.lib.common.adapter.bean.TextClickItem
import app.allever.android.lib.common.databinding.FragmentListBinding
import app.allever.android.lib.core.ext.toast
import app.allever.android.lib.core.helper.FragmentHelper
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.sample.videoeditor.databinding.FragmentBaseEditBinding

class BaseEditFragment : BaseFragment<FragmentBaseEditBinding, BaseViewModel>() {
    override fun inflate() = FragmentBaseEditBinding.inflate(layoutInflater)

    override fun init() {
        FragmentHelper.addToContainer(
            childFragmentManager,
            EditFunListFragment(),
            mBinding.fragmentContainer.id
        )
    }

    class EditFunListFragment :
        ListFragment<FragmentListBinding, ListViewModel, TextClickItem>() {
        override fun layoutManager() = GridLayoutManager(requireContext(), 2)
        override fun getAdapter() = TextClickAdapter()

        override fun getList(): MutableList<TextClickItem> = mutableListOf(
            TextClickItem("添加ImageView") {
                toast(it.title)
            },
            TextClickItem("添加TextView") {
                toast(it.title)
            }
        )

    }
}

