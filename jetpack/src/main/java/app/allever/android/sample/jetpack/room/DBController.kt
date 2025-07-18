//package app.allever.android.sample.jetpack.room
//
//object DBController {
//    private val userDao by lazy {
//        JetpackDB.getIns().userDao()
//    }
//
//    private val bookDao by lazy {
//        JetpackDB.getIns().bookDao()
//    }
//
//    suspend fun addUser(user: User) = userDao.addUser(user)
//
//    suspend fun getAllUser() = userDao.getAllUser()
//
//    suspend fun addBook(book: Book) = bookDao.addBook(book)
//
//    suspend fun getAllBook() = bookDao.getAllBook()
//}