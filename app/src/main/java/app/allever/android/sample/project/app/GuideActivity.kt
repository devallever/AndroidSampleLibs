package app.allever.android.sample.project.app

import android.os.Bundle
import android.view.View
import app.allever.android.lib.core.base.AbstractActivity
import app.allever.android.lib.core.helper.FragmentHelper
import app.allever.android.sample.project.R

class GuideActivity : AbstractActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val decorView = window.decorView;
        val uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or  View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.systemUiVisibility = uiOptions;
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)
        FragmentHelper.addToContainer(
            supportFragmentManager,
            GuideFragment(),
            R.id.fragmentContainer
        )
    }


}