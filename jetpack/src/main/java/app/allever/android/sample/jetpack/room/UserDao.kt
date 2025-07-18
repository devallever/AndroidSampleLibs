//package app.allever.android.sample.jetpack.room
//
//import androidx.room.*
//
//@Dao
//interface UserDao {
//    @Insert
//    suspend fun addUser(user: User)
//
//    @Query("select * from user")
//    suspend fun getAllUser(): MutableList<User>
//}