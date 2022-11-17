package app.allever.android.sample.function.theme

import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.SkinAppCompatDelegateImpl
import androidx.fragment.app.Fragment
import app.allever.android.lib.common.TabActivity
import app.allever.android.lib.common.TabViewModel
import app.allever.android.lib.common.databinding.ActivityTabBinding


class ThemeMainActivity : TabActivity<ActivityTabBinding, TabViewModel>() {
    override fun getPageTitle() = "主题"

    override fun getTabTitles() = mutableListOf("更改主题")

    override fun getFragments(): MutableList<Fragment> = mutableListOf(ChangeThemeFragment())

    override fun getDelegate(): AppCompatDelegate {
        return SkinAppCompatDelegateImpl.get(this, this)
    }
}

