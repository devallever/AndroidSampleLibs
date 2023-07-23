package app.allever.android.lib.demo.ui.dragrecycler

import androidx.recyclerview.widget.LinearLayoutManager
import app.allever.android.lib.common.BaseFragment
import app.allever.android.lib.demo.databinding.FragmentManageFriendGroupBinding
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeRecyclerView

class ManageFriendGroupFragment :
    BaseFragment<FragmentManageFriendGroupBinding, ManageFriendViewModel>(
    ) {
    override fun init() {
        initList()
        mViewModel.getTestData()
    }

    private fun initList() {
        mBinding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        mBinding.recyclerView.adapter = mViewModel.adapter
        mBinding.recyclerView.disableSwipeDirection(DragDropSwipeRecyclerView.ListOrientation.DirectionFlag.DOWN)
        mBinding.recyclerView.disableSwipeDirection(DragDropSwipeRecyclerView.ListOrientation.DirectionFlag.UP)
        mBinding.recyclerView.disableSwipeDirection(DragDropSwipeRecyclerView.ListOrientation.DirectionFlag.RIGHT)
        mBinding.recyclerView.disableSwipeDirection(DragDropSwipeRecyclerView.ListOrientation.DirectionFlag.LEFT)
    }

    override fun inflate() = FragmentManageFriendGroupBinding.inflate(layoutInflater)
}