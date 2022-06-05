package com.bangkit.skutapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bangkit.skutapplication.model.DailyTreatmentItem

@Database(entities = [DailyTreatmentItem::class], version = 1)
abstract class DailyTreatmentDatabase : RoomDatabase() {

    abstract fun dailyTreatmentDao(): DailyTreatmentDao

    companion object {
        @Volatile
        private var INSTANCE: DailyTreatmentDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): DailyTreatmentDatabase {
            if (INSTANCE == null) {
                synchronized(DailyTreatmentDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        DailyTreatmentDatabase::class.java, "note_database")
                        .build()
                }
            }
            return INSTANCE as DailyTreatmentDatabase
        }
    }
}