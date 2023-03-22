package app.allever.android.sample.project.app

import android.os.Bundle
import app.allever.android.lib.core.base.AbstractActivity
import app.allever.android.lib.core.helper.FragmentHelper
import app.allever.android.sample.project.R

class GuideActivity : AbstractActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)
        FragmentHelper.addToContainer(
            supportFragmentManager,
            GuideFragment(),
            R.id.fragmentContainer
        )
    }
}