package app.allever.android.lib.demo.ui.expandrecycler

import androidx.recyclerview.widget.LinearLayoutManager
import app.allever.android.lib.common.BaseFragment
import app.allever.android.lib.common.TestData
import app.allever.android.lib.demo.databinding.FragmentContactExpandBinding
import app.allever.android.lib.mvvm.base.BaseViewModel

class ContactExpandListFragment : BaseFragment<FragmentContactExpandBinding, BaseViewModel>() {

    val dataList = mutableListOf<ContactGroup>()
    val adapter = ContactGroupAdapter(dataList)

    private fun initList() {
        mBinding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        mBinding.recyclerView.adapter = adapter
    }

    private fun initData() {
        for (i in 0..3) {
            val userList = mutableListOf<ContactUser>()
            val group = ContactGroup().apply {
                id = i.toString()
                name = "好友分组${i + 1}"
                list = userList
            }
            for (j in 0..4) {
                val user = ContactUser().apply {
                    id = j.toString()
                    avatar = TestData.avatarList.random()
                    nickname = "昵称${i + 1}"
                    signature = "签名${i}"
                }
                userList.add(user)
            }
            dataList.add(group)
        }

    }

    override fun inflate() = FragmentContactExpandBinding.inflate(layoutInflater)
    override fun init() {
        initList()
        initData()
    }


}