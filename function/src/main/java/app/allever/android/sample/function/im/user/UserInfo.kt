package app.allever.android.sample.function.im.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class UserInfo {
    @PrimaryKey(autoGenerate = true)
    var id = 0L
    var nickname = ""
    var avatar = ""
    var extra = ""
}