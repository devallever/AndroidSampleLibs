package app.allever.android.sample.jetpack.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookDao {
    @Insert
    suspend fun addBook(book: Book): Long

    @Query("select * from Book")
    suspend fun getAllBook(): MutableList<Book>
}