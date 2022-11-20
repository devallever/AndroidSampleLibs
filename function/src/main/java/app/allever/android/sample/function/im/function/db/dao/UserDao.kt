package app.allever.android.sample.function.im.function.db.dao

import androidx.room.*
import app.allever.android.sample.function.im.user.UserInfo

@Dao
interface UserDao {
    @Insert
    fun addUser(user: UserInfo): Long

    @Delete()
    fun deleteUser(user: UserInfo)

    @Query("delete from userinfo where id = :id")
    fun deleteByUserId(id: Long)

    @Query("delete from userinfo")
    fun deleteAllUser()

    @Update
    fun updateUser(user: UserInfo)

    @Query("select * from userinfo")
    fun getAllUser(): MutableList<UserInfo>

    @Query("select * from userinfo where id = :id")
    fun getUserById(id: Long): MutableList<UserInfo>
}