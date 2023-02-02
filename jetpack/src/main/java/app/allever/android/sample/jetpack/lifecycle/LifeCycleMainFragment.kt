package app.allever.android.sample.jetpack.lifecycle

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.ListViewModel
import app.allever.android.lib.common.adapter.TextAdapter
import app.allever.android.lib.common.databinding.FragmentListBinding
import app.allever.android.lib.core.ext.log

class LifeCycleMainFragment : ListFragment<FragmentListBinding, ListViewModel, String>() {
    override fun getAdapter() = TextAdapter()

    override fun getList() = mutableListOf(
        "DefaultLifecycleObserver",
        "LifecycleEventObserver"
    )

    override fun init() {
        super.init()
        lifecycle.addObserver(MyLifecycleObserver())
        lifecycle.addObserver(MyLifecycleEventObserver())
    }
}

class MyLifecycleObserver: DefaultLifecycleObserver {

    override fun onCreate(owner: LifecycleOwner) {
        log("MyLifecycleObserver: onCreate")
    }

    override fun onStart(owner: LifecycleOwner) {
        log("MyLifecycleObserver: onStart")
    }

    override fun onResume(owner: LifecycleOwner) {
        log("MyLifecycleObserver: onResume")
    }

    override fun onPause(owner: LifecycleOwner) {
        log("MyLifecycleObserver: onPause")
    }

    override fun onStop(owner: LifecycleOwner) {
        log("MyLifecycleObserver: onStop")
    }

    override fun onDestroy(owner: LifecycleOwner) {
        log("MyLifecycleObserver: onDestroy")
    }
}

class MyLifecycleEventObserver: LifecycleEventObserver{
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        log("MyLifecycleEventObserver: onStateChanged -> ${event.name}")
    }

}