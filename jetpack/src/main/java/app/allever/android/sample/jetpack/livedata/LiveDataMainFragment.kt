package app.allever.android.sample.jetpack.livedata

import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.ListViewModel
import app.allever.android.lib.common.adapter.TextDetailAdapter
import app.allever.android.lib.common.adapter.bean.TextDetailItem
import app.allever.android.lib.common.databinding.FragmentListBinding
import app.allever.android.lib.core.ext.log
import app.allever.android.lib.mvvm.base.BaseViewModel

class LiveDataMainFragment : ListFragment<FragmentListBinding, ListViewModel, TextDetailItem>() {

    private val mThisViewModel: LiveDataMainFragmentViewModel by viewModels()

    override fun getAdapter() = TextDetailAdapter()

    override fun getList() = mutableListOf(
        TextDetailItem("MutableLiveData", ""),
        TextDetailItem(
            "Transformations.map(liveData) {  }",
            "您可能希望在将 LiveData 对象分派给观察者之前对存储在其中的值进行更改，或者您可能需要根据另一个实例的值返回不同的LiveData 实例。Lifecycle 软件包会提供 Transformations 类，该类包括可应对这些情况的辅助程序方法。"
        ),
        TextDetailItem(
            "Transformations.switchMap(userIdLiveData) \n{ value -> }",
            "与 map() 类似，对存储在 LiveData 对象中的值应用函数，并将结果解封和分派到下游。传递给 switchMap() 的函数必须返回 LiveData 对象"
        ),
        TextDetailItem(
            "MediatorLiveData",
            "允许您合并多个 LiveData 源。只要任何原始的 LiveData 源对象发生更改，就会触发 MediatorLiveData 对象的观察者"
        )
    )

    override fun init() {
        super.init()
        mThisViewModel.usernameLiveData.observe(this) {
            log(it)
        }

        mThisViewModel.userLiveData.observe(this) {

        }

        mThisViewModel.userFullNameLiveData.observe(this) {
            log(it)
        }

        mThisViewModel.switchMapUserLiveData.observe(this) {
            log("${it.firstName}${it.name}")
        }

        mThisViewModel.userMediatorLiveData.observe(this) {
            log("${it.firstName}${it.name}")
        }

        mThisViewModel.initData()
    }
}


class LiveDataMainFragmentViewModel : BaseViewModel() {
    val usernameLiveData = MutableLiveData<String>()


    /**
     * 表示看不懂，但代码能看懂
     * 您可能希望在将 LiveData 对象分派给观察者之前对存储在其中的值进行更改，或者您可能需要根据另一个实例的值返回不同的
     * LiveData 实例。Lifecycle 软件包会提供 Transformations 类，该类包括可应对这些情况的辅助程序方法。
     */
    val userLiveData = MutableLiveData<User>()
    val userFullNameLiveData = Transformations.map(userLiveData) {
        "${it.firstName}${it.name}"
    }

    /**
     * 表示看不懂，但代码能看懂
     * 与 map() 类似，对存储在 LiveData 对象中的值应用函数，并将结果解封和分派到下游。传递给 switchMap() 的函数必须返回 LiveData 对象
     */
    val userIdLiveData = MutableLiveData("")
    val switchMapUserLiveData = Transformations.switchMap(userIdLiveData) { userId ->
        getUser(userId)
    }

    val userMediatorLiveData = MediatorLiveData<User>()
    val remoteUserLiveData = MutableLiveData<User>()
    val dbUserLiveData = MutableLiveData<User>()

    init {
        /**
         * 不能多次添加
         */
        userMediatorLiveData.addSource(remoteUserLiveData) {
            userMediatorLiveData.value = it
        }

        userMediatorLiveData.addSource(dbUserLiveData) {
            userMediatorLiveData.value = it
        }
    }

    fun initData() {
        usernameLiveData.value = "Hello"
        var user = User()
        user.firstName = "张"
        user.name = "小明"
        userLiveData.value = user

        userIdLiveData.value = "1"

        user = User()
        user.firstName = "网络:"
        user.name = "小明"
        remoteUserLiveData.value = user

        user = User()
        user.firstName = "数据库:"
        user.name = "小明"
        dbUserLiveData.value = user
    }

    fun getUser(userId: String): LiveData<User> {
        val user = User()
        user.firstName = "王"
        user.name = "小红"
        return MutableLiveData(user)
    }
}

class User {
    var firstName = ""
    var name = ""
}
