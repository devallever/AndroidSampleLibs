package app.allever.android.lib.demo.ui

import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import app.allever.android.lib.common.BaseFragment
import app.allever.android.lib.core.function.imageloader.load
import app.allever.android.lib.demo.BR
import app.allever.android.lib.demo.R
import app.allever.android.lib.demo.databinding.FragmentContactListBinding
import app.allever.android.lib.demo.ui.ContactItem.Companion.TYPE_CONTACT
import app.allever.android.lib.demo.ui.ContactItem.Companion.TYPE_GROUP
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.lib.mvvm.base.MvvmConfig
import com.chad.library.adapter.base.BaseProviderMultiAdapter
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class ContactListFragment : BaseFragment<FragmentContactListBinding, BaseViewModel>() {
    private var mAdapter = ContactAdapter()
    override fun getMvvmConfig() = MvvmConfig(R.layout.fragment_contact_list, BR.contactListVM)

    override fun init() {
        mBinding.recyclerView.layoutManager = LinearLayoutManager(context)
        mBinding.recyclerView.adapter = mAdapter
        initContactData()
    }

    private fun initContactData() {
        val list = mutableListOf<ContactItem>()
        var contactItem = ContactItem()
        contactItem.data = "A"
        contactItem.type = TYPE_GROUP
        list.add(contactItem)

        contactItem = ContactItem()
        var user = User()
        user.nickname = "Allever"
        user.avatar =
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fblog%2F202109%2F06%2F20210906225922_1c31b.thumb.1000_0.jpeg&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1673106746&t=9af4cad4b25231320a4895f209801f56"
        contactItem.data = user
        contactItem.type = TYPE_CONTACT
        list.add(contactItem)

        contactItem = ContactItem()
        contactItem.data = "B"
        contactItem.type = TYPE_GROUP
        list.add(contactItem)

        contactItem = ContactItem()
        user = User()
        user.nickname = "Bobby"
        user.avatar =
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fblog%2F202109%2F06%2F20210906225922_1c31b.thumb.1000_0.jpeg&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1673106746&t=9af4cad4b25231320a4895f209801f56"
        contactItem.data = user
        contactItem.type = TYPE_CONTACT
        list.add(contactItem)

        contactItem = ContactItem()
        contactItem.data = "C"
        contactItem.type = TYPE_GROUP
        list.add(contactItem)

        contactItem = ContactItem()
        user = User()
        user.nickname = "Cristy"
        user.avatar =
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fblog%2F202109%2F06%2F20210906225922_1c31b.thumb.1000_0.jpeg&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1673106746&t=9af4cad4b25231320a4895f209801f56"
        contactItem.data = user
        contactItem.type = TYPE_CONTACT
        list.add(contactItem)

        contactItem = ContactItem()
        contactItem.data = "D"
        contactItem.type = TYPE_GROUP
        list.add(contactItem)

        contactItem = ContactItem()
        user = User()
        user.nickname = "David"
        user.avatar =
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fblog%2F202109%2F06%2F20210906225922_1c31b.thumb.1000_0.jpeg&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1673106746&t=9af4cad4b25231320a4895f209801f56"
        contactItem.data = user
        contactItem.type = TYPE_CONTACT
        list.add(contactItem)

        contactItem = ContactItem()
        contactItem.data = "E"
        contactItem.type = TYPE_GROUP
        list.add(contactItem)

        contactItem = ContactItem()
        user = User()
        user.nickname = "Eiverd"
        user.avatar =
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fblog%2F202109%2F06%2F20210906225922_1c31b.thumb.1000_0.jpeg&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1673106746&t=9af4cad4b25231320a4895f209801f56"
        contactItem.data = user
        contactItem.type = TYPE_CONTACT
        list.add(contactItem)

        contactItem = ContactItem()
        contactItem.data = "F"
        contactItem.type = TYPE_GROUP
        list.add(contactItem)

        contactItem = ContactItem()
        user = User()
        user.nickname = "Frans"
        user.avatar =
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fblog%2F202109%2F06%2F20210906225922_1c31b.thumb.1000_0.jpeg&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1673106746&t=9af4cad4b25231320a4895f209801f56"
        contactItem.data = user
        contactItem.type = TYPE_CONTACT
        list.add(contactItem)

        contactItem = ContactItem()
        contactItem.data = "G"
        contactItem.type = TYPE_GROUP
        list.add(contactItem)

        contactItem = ContactItem()
        user = User()
        user.nickname = "Gigi"
        user.avatar =
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fblog%2F202109%2F06%2F20210906225922_1c31b.thumb.1000_0.jpeg&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1673106746&t=9af4cad4b25231320a4895f209801f56"
        contactItem.data = user
        contactItem.type = TYPE_CONTACT
        list.add(contactItem)

        contactItem = ContactItem()
        contactItem.data = "H"
        contactItem.type = TYPE_GROUP
        list.add(contactItem)

        contactItem = ContactItem()
        user = User()
        user.nickname = "Hop man"
        user.avatar =
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fblog%2F202109%2F06%2F20210906225922_1c31b.thumb.1000_0.jpeg&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1673106746&t=9af4cad4b25231320a4895f209801f56"
        contactItem.data = user
        contactItem.type = TYPE_CONTACT
        list.add(contactItem)

        contactItem = ContactItem()
        contactItem.data = "I"
        contactItem.type = TYPE_GROUP
        list.add(contactItem)

        contactItem = ContactItem()
        user = User()
        user.nickname = "Ivvi"
        user.avatar =
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fblog%2F202109%2F06%2F20210906225922_1c31b.thumb.1000_0.jpeg&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1673106746&t=9af4cad4b25231320a4895f209801f56"
        contactItem.data = user
        contactItem.type = TYPE_CONTACT
        list.add(contactItem)

        contactItem = ContactItem()
        contactItem.data = "J"
        contactItem.type = TYPE_GROUP
        list.add(contactItem)

        contactItem = ContactItem()
        user = User()
        user.nickname = "Jack"
        user.avatar =
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fblog%2F202109%2F06%2F20210906225922_1c31b.thumb.1000_0.jpeg&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1673106746&t=9af4cad4b25231320a4895f209801f56"
        contactItem.data = user
        contactItem.type = TYPE_CONTACT
        list.add(contactItem)

        contactItem = ContactItem()
        contactItem.data = "K"
        contactItem.type = TYPE_GROUP
        list.add(contactItem)

        contactItem = ContactItem()
        user = User()
        user.nickname = "King"
        user.avatar =
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fblog%2F202109%2F06%2F20210906225922_1c31b.thumb.1000_0.jpeg&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1673106746&t=9af4cad4b25231320a4895f209801f56"
        contactItem.data = user
        contactItem.type = TYPE_CONTACT
        list.add(contactItem)

        contactItem = ContactItem()
        contactItem.data = "L"
        contactItem.type = TYPE_GROUP
        list.add(contactItem)

        contactItem = ContactItem()
        user = User()
        user.nickname = "Lily"
        user.avatar =
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fblog%2F202109%2F06%2F20210906225922_1c31b.thumb.1000_0.jpeg&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1673106746&t=9af4cad4b25231320a4895f209801f56"
        contactItem.data = user
        contactItem.type = TYPE_CONTACT
        list.add(contactItem)

        contactItem = ContactItem()
        contactItem.data = "M"
        contactItem.type = TYPE_GROUP
        list.add(contactItem)

        contactItem = ContactItem()
        user = User()
        user.nickname = "May"
        user.avatar =
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fblog%2F202109%2F06%2F20210906225922_1c31b.thumb.1000_0.jpeg&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1673106746&t=9af4cad4b25231320a4895f209801f56"
        contactItem.data = user
        contactItem.type = TYPE_CONTACT
        list.add(contactItem)

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
