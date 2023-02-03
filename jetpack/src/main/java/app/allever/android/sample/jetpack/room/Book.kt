package app.allever.android.sample.jetpack.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Book {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L
    var name: String = ""
}