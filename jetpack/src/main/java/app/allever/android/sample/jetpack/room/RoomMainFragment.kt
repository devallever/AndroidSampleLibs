package app.allever.android.sample.jetpack.room

import androidx.lifecycle.lifecycleScope
import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.ListViewModel
import app.allever.android.lib.common.adapter.TextDetailAdapter
import app.allever.android.lib.common.adapter.bean.TextDetailItem
import app.allever.android.lib.common.databinding.FragmentListBinding
import app.allever.android.lib.core.ext.log
import app.allever.android.lib.core.ext.toJson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoomMainFragment : ListFragment<FragmentListBinding, ListViewModel, TextDetailItem>() {
    override fun getAdapter() = TextDetailAdapter()

    override fun getList() = mutableListOf(
        TextDetailItem("@Entity", "实体类：用于表示应用的数据库中的表。"),
        TextDetailItem(
            "@Dao",
            "数据库操作接口：提供您的应用可用于查询(@Query)、更新(@Update)、新增(@Insert)和删除(@Delete)数据库中的数据的方法。"
        ),
        TextDetailItem("@Database", "数据库类：用于保存数据库并作为应用持久性数据底层连接的主要访问点。"),
        TextDetailItem("Migration升级数据库", "addMigrations(migrationObject)")
    )

    override fun init() {
        super.init()
        testData()
    }

    private fun testData() {
        lifecycleScope.launch(Dispatchers.IO) {
            var allUser = DBController.getAllUser()
            if (allUser.isEmpty()) {
                for (i in 1..4) {
                    val user = User()
                    user.name = "张三${i}号"
                    user.age = 20 + i
                    user.gender = i % 2
                    DBController.addUser(user)
                }
            }

            allUser = DBController.getAllUser()
            allUser.map {
                log("user = ${it.toJson()}")
            }


            var allBook = DBController.getAllBook()
            if (allBook.isEmpty()) {
                for (i in 1..4) {
                    val book = Book()
                    book.name = "书名${i}号"
                    DBController.addBook(book)
                }
            }

            allBook = DBController.getAllBook()
            allBook.map {
                log("book = ${it.toJson()}")
            }
        }
    }
}