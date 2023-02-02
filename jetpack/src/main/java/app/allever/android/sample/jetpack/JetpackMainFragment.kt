package app.allever.android.sample.jetpack

import app.allever.android.lib.common.FragmentActivity
import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.ListViewModel
import app.allever.android.lib.common.adapter.TextDetailAdapter
import app.allever.android.lib.common.adapter.bean.TextDetailItem
import app.allever.android.lib.common.databinding.FragmentListBinding
import app.allever.android.lib.widget.fragment.EmptyFragment
import app.allever.android.sample.jetpack.lifecycle.LifeCycleMainFragment
import app.allever.android.sample.jetpack.livedata.LiveDataMainFragment
import app.allever.android.sample.jetpack.navigation.NavigationFragment
import app.allever.android.sample.jetpack.paging3.PagingFragment
import app.allever.android.sample.jetpack.viewmodel.ViewModelMainFragment

class JetpackMainFragment : ListFragment<FragmentListBinding, ListViewModel, TextDetailItem>() {
    private var ITEM_ID_PAGING = 0
    private var ITEM_ID_NAVIGATION = 1
    private var ITEM_ID_VIEW_MODEL = 2
    private var ITEM_ID_LIFE_CYCLE = 3
    private var ITEM_ID_LIVE_DATA = 4
    private var ITEM_ID_DATA_STORE = 5
    private var ITEM_ID_ROOM = 6
    private var ITEM_ID_DATA_BINDING = 7
    private var ITEM_ID_VIEW_BINDING = 8

    override fun getAdapter() = TextDetailAdapter()

    override fun getList() = mutableListOf(
        TextDetailItem(
            "Paging3",
            "Paging 库可帮助您加载和显示来自本地存储或网络中更大的数据集中的数据页面。此方法可让您的应用更高效地利用网络带宽和系统资源。Paging 库的组件旨在契合推荐的 Android 应用架构，流畅集成其他 Jetpack 组件，并提供一流的 Kotlin 支持。",
            ITEM_ID_PAGING
        ),
        TextDetailItem(
            "Navigation",
            "Android Jetpack 的导航组件可帮助您实现导航，无论是简单的按钮点击，还是应用栏和抽屉式导航栏等更为复杂的模式，该组件均可应对。导航组件还通过遵循一套既定原则来确保一致且可预测的用户体验。",
            ITEM_ID_NAVIGATION
        ),
        TextDetailItem(
            "ViewModel",
            "ViewModel 类是一种业务逻辑或屏幕级状态容器。它用于将状态公开给界面，以及封装相关的业务逻辑。 它的主要优点是，它可以缓存状态，并可在配置更改后持久保留相应状态。这意味着在 activity 之间导航时或进行配置更改后（例如旋转屏幕时），界面将无需重新提取数据。",
            ITEM_ID_VIEW_MODEL
        ),
        TextDetailItem(
            "LifeCycle",
            "生命周期感知型组件可执行操作来响应另一个组件（如 Activity 和 Fragment）的生命周期状态的变化。这些组件有助于您编写出更有条理且往往更精简的代码，此类代码更易于维护。",
            ITEM_ID_LIFE_CYCLE
        ),
        TextDetailItem(
            "LiveData",
            "LiveData 是一种可观察的数据存储器类。与常规的可观察类不同，LiveData 具有生命周期感知能力，意指它遵循其他应用组件（如 activity、fragment 或 service）的生命周期。这种感知能力可确保 LiveData 仅更新处于活跃生命周期状态的应用组件观察者。",
            ITEM_ID_LIVE_DATA
        ),
        TextDetailItem(
            "DataStore",
            "Jetpack DataStore 是一种数据存储解决方案，允许您使用协议缓冲区存储键值对或类型化对象。DataStore 使用 Kotlin 协程和 Flow 以异步、一致的事务方式存储数据。",
            ITEM_ID_DATA_STORE
        ),
        TextDetailItem(
            "Room",
            "Room 持久性库在 SQLite 上提供了一个抽象层，以便在充分利用 SQLite 的强大功能的同时，能够流畅地访问数据库。",
            ITEM_ID_ROOM
        ),
        TextDetailItem(
            "DataBinding",
            "数据绑定库是一种支持库，借助该库，您可以使用声明性格式（而非程序化地）将布局中的界面组件绑定到应用中的数据源。",
            ITEM_ID_DATA_BINDING
        ),
        TextDetailItem(
            "ViewBinding",
            "通过视图绑定功能，您可以更轻松地编写可与视图交互的代码。在模块中启用视图绑定之后，系统会为该模块中的每个 XML 布局文件生成一个绑定类。绑定类的实例包含对在相应布局中具有 ID 的所有视图的直接引用。",
            ITEM_ID_VIEW_BINDING
        ),
    )

    override fun onItemClick(position: Int, item: TextDetailItem) {
        when (item.id) {
            ITEM_ID_PAGING -> {
                FragmentActivity.start<PagingFragment>(item.title)
            }
            ITEM_ID_NAVIGATION -> {
                FragmentActivity.start<NavigationFragment>(item.title)
            }
            ITEM_ID_VIEW_MODEL -> {
                FragmentActivity.start<ViewModelMainFragment>(item.title)
            }
            ITEM_ID_LIFE_CYCLE -> {
                FragmentActivity.start<LifeCycleMainFragment>(item.title)
            }
            ITEM_ID_LIVE_DATA -> {
                FragmentActivity.start<LiveDataMainFragment>(item.title)
            }
            ITEM_ID_DATA_STORE -> {
                FragmentActivity.start<EmptyFragment>(item.title)
            }
            ITEM_ID_ROOM -> {
                FragmentActivity.start<EmptyFragment>(item.title)
            }
            ITEM_ID_DATA_BINDING -> {
                FragmentActivity.start<EmptyFragment>(item.title)
            }
            ITEM_ID_VIEW_BINDING -> {
                FragmentActivity.start<EmptyFragment>(item.title)
            }
        }
    }

}