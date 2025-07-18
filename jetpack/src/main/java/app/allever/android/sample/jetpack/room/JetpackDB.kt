//package app.allever.android.sample.jetpack.room
//
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//import androidx.room.migration.Migration
//import androidx.sqlite.db.SupportSQLiteDatabase
//import app.allever.android.lib.core.app.App
//
//@Database(version = 2, entities = [User::class, Book::class])
//abstract class JetpackDB : RoomDatabase() {
//    abstract fun userDao(): UserDao
//    abstract fun bookDao(): BookDao
//
//    companion object {
//        private var INS: JetpackDB? = null
//
//        @Synchronized
//        fun getIns(): JetpackDB {
//            INS?.let {
//                return it
//            }
//
//            val MIGRATION_1_2 = object : Migration(1, 2) {
//                override fun migrate(database: SupportSQLiteDatabase) {
//                    val sql = "create table Book (" +
//                            "id integer primary key autoincrement not null, " +
//                            "name text not null)"
//                    database.execSQL(sql)
//                }
//
//            }
//
//            return Room.databaseBuilder(
//                App.context.applicationContext,
//                JetpackDB::class.java,
//                "jetpack_db"
//            )
//                .addMigrations(MIGRATION_1_2)
//                .build().apply {
//                    INS = this
//                }
//        }
//    }
//}