package com.nuhlp.nursehelper.data.room

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Query("select Count(*) from UserAccount where id=:userId ")
    fun getAvailableId(userId:String):LiveData<Int>



    // crud

    @Query("select * from UserAccount where id =:userId")
    fun getUser(userId: String):UserAccount

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setUser(user: UserAccount)

    @Update
    fun updateUser(user: UserAccount)



    // test 전용

    @Query("select * from UserAccount")
    fun getAll(): List<UserAccount>

    @Query("DELETE FROM UserAccount")
    fun deleteAll()

}



@Database(entities = [UserAccount::class], version =1)
abstract class UserDatabase: RoomDatabase() {
    abstract val userDao: UserDao
}

private lateinit var INSTANCE: UserDatabase

fun getUserDatabase(context: Context,databaseName: String = "users"): UserDatabase {
    synchronized(UserDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                UserDatabase::class.java,
                databaseName).build()
        }

    }
    return INSTANCE
}