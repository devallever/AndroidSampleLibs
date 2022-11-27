package app.allever.android.lib.common

import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.lib.mvvm.base.MvvmConfig

abstract class BaseFragmentActivity<DB : ViewDataBinding, VM : BaseViewModel> :
    BaseActivity<DB, VM>() {

    protected lateinit var mFragment: Fragment

    override fun init() {
        mFragment = attachFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, mFragment)
            .commit()
    }

    override fun getContentMvvmConfig() =
        MvvmConfig(R.layout.activity_base_fragment, BR.baseFragmentVM)

    abstract fun attachFragment(): Fragment
}

class BaseFragmentViewModel : BaseViewModel() {

}