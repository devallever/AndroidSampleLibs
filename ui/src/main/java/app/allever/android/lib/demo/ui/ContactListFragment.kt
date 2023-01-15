package app.allever.android.lib.demo.ui

import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import app.allever.android.lib.common.BaseFragment
import app.allever.android.lib.core.function.imageloader.load
import app.allever.android.lib.demo.R
import app.allever.android.lib.demo.databinding.FragmentContactListBinding
import app.allever.android.lib.demo.ui.ContactItem.Companion.TYPE_CONTACT
import app.allever.android.lib.demo.ui.ContactItem.Companion.TYPE_GROUP
import app.allever.android.lib.mvvm.base.BaseViewModel
import com.chad.library.adapter.base.BaseProviderMultiAdapter
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class ContactListFragment : BaseFragment<FragmentContactListBinding, BaseViewModel>() {
    private var mAdapter = ContactAdapter()

    private val mLetterList = listOf(
        "A",
        "B",
        "C",
        "D",
        "E",
        "F",
        "G",
        "H",
        "I",
        "J",
        "K",
        "L",
        "M",
        "N",
        "O",
        "P",
        "Q",
        "R",
        "S",
        "T",
        "U",
        "V",
        "W",
        "X",
        "Y",
        "Z",
        "#"
    )

    override fun inflate() = FragmentContactListBinding.inflate(layoutInflater)

    override fun init() {
        val smoothScroller = object : LinearSmoothScroller(context) {
            override fun getHorizontalSnapPreference(): Int {
                return SNAP_TO_START//将返回值设置为SNAP_TO_START
            }

            override fun getVerticalSnapPreference(): Int {
                return SNAP_TO_START//将返回值设置为SNAP_TO_START
            }
        }
        mBinding.sideBar.setTextView(mBinding.tvGroupName)
        mBinding.sideBar.setOnTouchingLetterChangedListener { groupName ->
            var index = -1
            mAdapter.data.map {
                if (it.data is String) {
                    if (groupName == it.data) {
                        index = mAdapter.data.indexOf(it)
                        return@map
                    }
                }
            }
            if (index != -1) {
                smoothScroller.targetPosition = index//要滑动到的位置
                mBinding.recyclerView.layoutManager?.startSmoothScroll(smoothScroller)
            }
        }
        mBinding.recyclerView.layoutManager = LinearLayoutManager(context)
        mBinding.recyclerView.adapter = mAdapter
        initContactData()
        mBinding.sideBar.setData(mLetterList)
    }

    private fun initContactData() {
        val list = mutableListOf<ContactItem>()
        mLetterList.map {
            var groupItem = ContactItem()
            groupItem.data = it
            groupItem.type = TYPE_GROUP
            list.add(groupItem)

            val contactItem = ContactItem()
            val user = User()
            user.nickname = "${it}: 昵称"
            user.avatar =
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fblog%2F202109%2F06%2F20210906225922_1c31b.thumb.1000_0.jpeg&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1673106746&t=9af4cad4b25231320a4895f209801f56"
            contactItem.data = user
            contactItem.type = TYPE_CONTACT
            list.add(contactItem)
        }
        mAdapter.setList(list)
    }
}


class ContactAdapter : BaseProviderMultiAdapter<ContactItem>() {

    init {
        addItemProvider(ContactProvider())
        addItemProvider(ContactGroupProvider())
    }

    override fun getItemType(data: List<ContactItem>, position: Int): Int {
        return data[position].type
    }

}

class ContactProvider : BaseItemProvider<ContactItem>() {
    override val itemViewType: Int = TYPE_CONTACT
    override val layoutId: Int = R.layout.rv_contact_item
    override fun convert(helper: BaseViewHolder, item: ContactItem) {
        val data = item.data as? User ?: return
        helper.setText(R.id.tvNickname, data.nickname)
        helper.getView<ImageView>(R.id.ivAvatar).load(data.avatar)
    }
}

class ContactGroupProvider : BaseItemProvider<ContactItem>() {
    override val itemViewType: Int = TYPE_GROUP
    override val layoutId: Int = R.layout.rv_contact_group_item
    override fun convert(helper: BaseViewHolder, item: ContactItem) {
        val data = item.data as? String ?: return
        helper.setText(R.id.tvGroupName, data)
    }
}


class User {
    var avatar = ""
    var nickname = ""
}

class ContactItem {
    var data: Any? = null
    var type: Int = TYPE_CONTACT

    companion object {
        const val TYPE_CONTACT = 0
        const val TYPE_GROUP = 1
    }
}
