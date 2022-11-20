package app.allever.android.sample.function.im.function.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import app.allever.android.sample.function.im.function.db.entity.MessageEntity

@Dao
interface MessageDao {

    /**
     * 发送消息
     */
    @Insert
    fun insertMessage(message: MessageEntity): Long

    /**
     * 删除消息
     */
    @Delete
    fun deleteMessage(message: MessageEntity)


    /**
     * 删除消息
     */
    @Query("delete from message where id = :id")
    fun deleteById(id: Long)

    /**
     * 和莫莫的消息列表
     */
    @Query("select * from message where fromUserId = :fromUserId and toUserId = :toUserId or fromUserId = :toUserId and toUserId = :fromUserId")
    fun getMessage(
        fromUserId: Long,
        toUserId: Long
    ): MutableList<MessageEntity>

    /**
     * 获取和用户的消息列表
     */
    @Query("select * from message where toUserId = :toUserId group by fromUserId")
    fun getMessageList(toUserId: Long): MutableList<MessageEntity>

    @Query("select * from message")
    fun getAllMessageList(): MutableList<MessageEntity>
}