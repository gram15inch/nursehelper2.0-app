package com.nuhlp.nursehelper.data.room.app

import android.content.Context
import androidx.room.*


@Database(version =1,
    entities = [Document::class,Patient::class,Product::class,Stock::class])
abstract class AppDatabase: RoomDatabase() {
    abstract val appDao: AppDao
}

private lateinit var INSTANCE: AppDatabase

fun getAppDatabase(context: Context,databaseName: String = "nuhlpDB"): AppDatabase {
    synchronized(AppDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java,
                databaseName) // .createFromAsset("database/bus_schedule.db")
                .build()
        }

    }
    return INSTANCE
}
