package app.allever.android.sample.jetpack

import android.app.Activity
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation

class NavigationExt {
}

fun View.navigate(actionId: Int) {
    Navigation.findNavController(this).navigate(actionId);//这个id就是navigation里的action的id
}

fun View.popBackStack() {
    Navigation.findNavController(this).popBackStack(); //返回上一个碎片
}