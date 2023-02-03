package app.allever.android.sample.jetpack.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class User {
    @PrimaryKey(autoGenerate = true)
    var id = 0L
    var name = ""
    var gender = 1
    var age = 0
}