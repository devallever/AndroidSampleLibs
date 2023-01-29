package app.allever.android.lib.common

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import app.allever.android.lib.common.databinding.ActivityBaseFragmentBinding
import app.allever.android.lib.core.helper.ActivityHelper
import app.allever.android.lib.widget.fragment.EmptyFragment

class FragmentActivity :
    BaseFragmentActivity<ActivityBaseFragmentBinding, BaseFragmentViewModel>() {
    companion object {
//        fun <T : Class<*>> start(title: String, clz: T) {
//            ActivityHelper.startActivity<FragmentActivity> {
//                putExtra("fragmentName", clz.name)
//                putExtra("title", title)
//            }
//        }

        inline fun <reified T> start(title: String) {
            ActivityHelper.startActivity<FragmentActivity> {
                putExtra("fragmentName", T::class.java.name)
                putExtra("title", title)
            }
        }

        inline fun <reified T> start(title: String, block:(fragmentArgs: Bundle) -> Unit) {
            val bundle = Bundle()
            block.invoke(bundle)
            ActivityHelper.startActivity<FragmentActivity> {
                putExtra("fragmentName", T::class.java.name)
                putExtra("title", title)
                putExtra("fragmentArgs", bundle)
            }
        }
    }

    override fun init() {
        super.init()
        initTopBar(intent?.getStringExtra("title") ?: "FragmentActivity")
    }

    override fun attachFragment(): Fragment {
        val fragmentArgs = intent?.getBundleExtra("fragmentArgs")
        try {
            val clzName = intent.getStringExtra("fragmentName")
            if (TextUtils.isEmpty(clzName)) {
                return EmptyFragment()
            }
            val fragment = Class.forName(clzName!!).getConstructor().newInstance() as Fragment
            fragment.arguments = fragmentArgs
            return fragment
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return EmptyFragment()
    }
}