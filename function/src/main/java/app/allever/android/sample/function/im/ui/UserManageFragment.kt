package app.allever.android.sample.function.im.ui

import android.os.Build
import android.text.TextUtils
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import app.allever.android.lib.core.ext.toast
import app.allever.android.lib.core.function.mediapicker.MediaPickerHelper
import app.allever.android.lib.core.function.paging.BasePagingSource
import app.allever.android.lib.mvvm.base.BaseMvvmFragment
import app.allever.android.sample.function.databinding.FragmentUserManageBinding
import app.allever.android.sample.function.im.user.UserInfo
import app.allever.android.sample.function.im.viewmodel.UserManageViewModel
import kotlinx.coroutines.launch

class UserManageFragment : BaseMvvmFragment<FragmentUserManageBinding, UserManageViewModel>() {

    override fun inflate() = FragmentUserManageBinding.inflate(layoutInflater)

    @RequiresApi(Build.VERSION_CODES.M)
    override fun init() {
        initManageView()
        initUserList()
        initObserver()
        mViewModel.getAllUserInfo()
    }

    private fun initObserver() {
        mViewModel.currentUserLiveData.observe(this) {
            if (it == null) {
                mBinding.etAvatar.setText("")
                mBinding.etNickname.setText("")
                return@observe
            }

            mBinding.etAvatar.setText(it.avatar)
            mBinding.etNickname.setText(it.nickname)
        }
    }

    private fun initManageView() {
        mBinding.btnChooseAvatar.setOnClickListener {
            lifecycleScope.launch {
                val result =
                    MediaPickerHelper.launchPicker(requireContext(), MediaPickerHelper.TYPE_IMAGE)
                mBinding.etAvatar.setText(result.list[0].path)
            }
        }
        mBinding.btnAddUser.setOnClickListener {
            if (!checkCanClick()) {
                return@setOnClickListener
            }

            mViewModel.addUser(
                mBinding.etNickname.text.toString(),
                mBinding.etAvatar.text.toString()
            )
        }

        mBinding.btnUpdateUser.setOnClickListener {
            if (!checkCanClick()) {
                return@setOnClickListener
            }
            mViewModel.updateUser(
                mBinding.etNickname.text.toString(),
                mBinding.etAvatar.text.toString()
            )
        }

        mBinding.btnDeleteUser.setOnClickListener {
            if (!checkCanClick()) {
                return@setOnClickListener
            }

            mViewModel.deleteSelectUser()
        }

        mBinding.btnLogin.setOnClickListener {
            mViewModel.currentUserLiveData.value?.let {
                mViewModel.loginCurrentUser()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initUserList() {
        mBinding.reboundLayout.setChildCanScrollView(mBinding.rvUser)
        mBinding.rvUser.layoutManager = LinearLayoutManager(requireActivity())
        mBinding.rvUser.adapter = mViewModel.userAdapter
        mViewModel.userAdapter.setOnItemClickListener { adapter, view, position ->
            mViewModel.currentUserLiveData.value = mViewModel.userAdapter.data[position]
        }


        //Paging3
//        lifecycleScope.launch {
//            mViewModel.getPagingFlowData(UserPagingSource(mViewModel)).collect { pagingData ->
//                mViewModel.userAdapter.submitData(pagingData)
//            }
//        }
//        mViewModel.userAdapter.itemClickListener = { position, baseBindingViewHolder, item ->
//            mViewModel.currentUserLiveData.value = item
//        }
    }

    fun checkCanClick(): Boolean {
        val nickname = mBinding.etNickname.text.toString()
        val avatar = mBinding.etAvatar.text.toString()
        if (TextUtils.isEmpty(nickname)) {
            toast("请输入昵称")
            return false
        }
        if (TextUtils.isEmpty(avatar)) {
            toast("请选择头像")
            return false
        }

        return true
    }
}

class UserPagingSource(val viewModel: UserManageViewModel) : BasePagingSource<UserInfo>() {
    override suspend fun getData(currentPage: Int): MutableList<UserInfo> {
        return getTestData()
    }

    private suspend fun getTestData(): MutableList<UserInfo> {
        if (viewModel.isRefreshData) {
            return mutableListOf()
        }
        val list = viewModel.getAllUser()
//        val userMe = UserInfo()
//        val userOther = UserInfo()
//        userMe.avatar =
//            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F2019-05-28%2F5cecf6fe1ce3b.jpg&refer=http%3A%2F%2Fpic1.win4000.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1671440223&t=fc84daf5543856c1686bd29b9f2dbadc"
//        userMe.nickname = "小猫咪666"
//        userOther.avatar =
//            "https://img2.baidu.com/it/u=1801140900,2951304091&fm=253&fmt=auto&app=120&f=JPEG?w=1280&h=800"
//        userOther.nickname = "倾国倾城"

        viewModel.isRefreshData = false

        return list
    }

}