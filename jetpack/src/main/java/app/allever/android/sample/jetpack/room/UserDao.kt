package app.allever.android.sample.jetpack.room

import androidx.room.*

@Dao
interface UserDao {
    @Insert
    suspend fun addUser(user: User): Long

    @Delete
    suspend fun deleteUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Query("select * from User")
    suspend fun getAllUser(): MutableList<User>
}