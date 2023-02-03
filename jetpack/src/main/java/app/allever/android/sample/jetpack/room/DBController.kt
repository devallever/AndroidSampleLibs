package app.allever.android.sample.jetpack.room

object DBController {
    private val userDao by lazy {
        JetpackDB.getIns().userDao()
    }

    suspend fun addUser(user: User) = userDao.addUser(user)

    suspend fun getAllUser() = userDao.getAllUser()
}