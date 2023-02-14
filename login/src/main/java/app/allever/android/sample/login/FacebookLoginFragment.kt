package app.allever.android.sample.login

import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.ListViewModel
import app.allever.android.lib.common.adapter.TextAdapter
import app.allever.android.lib.common.databinding.FragmentListBinding
import app.allever.android.lib.login.facebook.FacebookHelper

class FacebookLoginFragment : ListFragment<FragmentListBinding, ListViewModel, String>() {
    override fun getAdapter() = TextAdapter()

    override fun getList() = mutableListOf(
        "初始化",
        "登录",
        "退出"
    )

    private fun initFacebook() {
        FacebookHelper.init(requireContext(), "1350542608624681", "", "")
    }

    override fun init() {
        super.init()
        initFacebook()
    }

    override fun onItemClick(position: Int, item: String) {
        when (position) {
            0 -> {
                initFacebook()
            }
            1 -> {
                FacebookHelper.login(requireActivity())
            }

            2 -> {
                FacebookHelper.logOut()
            }
        }
    }
}
