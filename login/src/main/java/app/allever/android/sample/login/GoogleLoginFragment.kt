package app.allever.android.sample.login

import android.content.Intent
import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.ListViewModel
import app.allever.android.lib.common.adapter.TextAdapter
import app.allever.android.lib.common.databinding.FragmentListBinding
import app.allever.android.lib.core.app.App
import app.allever.android.lib.core.ext.toast
import app.allever.android.lib.login.google.GoogleHelper
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

class GoogleLoginFragment : ListFragment<FragmentListBinding, ListViewModel, String>() {
    override fun getAdapter() = TextAdapter()

    override fun getList() = mutableListOf(
        "初始化", "登录", "退出"
    )

    private fun initGoogleLogin() {
        GoogleHelper.init(
            App.context,
            "1024726076058-038png8ttn9t7itq42s7p0004qrhdscj.apps.googleusercontent.com",
            "",
            ""
        )
    }

    override fun init() {
        super.init()
        initGoogleLogin()
    }

    override fun onItemClick(position: Int, item: String) {
        when (position) {
            0 -> {
                initGoogleLogin()
            }
            1 -> {
                GoogleHelper.setLoginResultListener(object : GoogleHelper.LoginResultCallback {
                    override fun onSuccess(account: GoogleSignInAccount?) {
                        toast("登录成功：${account?.displayName}")
                    }

                    override fun onFailure(errorCode: Int, message: String?) {
                        toast(message)
                    }
                })
                GoogleHelper.login(this)
            }
            2 -> {
                GoogleHelper.logOut(requireActivity()) {
                    toast("已退出")
                }
            }
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        GoogleHelper.handleOnActivityForResult(requestCode, resultCode, data)
    }
}