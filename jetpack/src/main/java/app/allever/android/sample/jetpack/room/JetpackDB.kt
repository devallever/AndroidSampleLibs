package app.allever.android.sample.jetpack.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import app.allever.android.lib.core.app.App

@Database(entities = [User::class], version = 1)
abstract class JetpackDB : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        private var INS: JetpackDB? = null

        @Synchronized
        fun getIns(): JetpackDB {
            INS?.let {
                return it
            }

            return Room.databaseBuilder(
                App.context.applicationContext,
                JetpackDB::class.java,
                "jetpack_db"
            )
                .build().apply {
                    INS = this
                }
        }
    }
}