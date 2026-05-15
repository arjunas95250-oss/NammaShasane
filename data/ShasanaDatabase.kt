package com.mindmatrix.nammashasane.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mindmatrix.nammashasane.model.DamageReport
import com.mindmatrix.nammashasane.model.Shasana
import com.mindmatrix.nammashasane.utils.ShasanaDataSeeder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [Shasana::class, DamageReport::class],
    version = 1,
    exportSchema = false
)
abstract class ShasanaDatabase : RoomDatabase() {

    abstract fun shasanaDao(): ShasanaDao

    companion object {
        @Volatile
        private var INSTANCE: ShasanaDatabase? = null

        fun getDatabase(context: Context): ShasanaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ShasanaDatabase::class.java,
                    "shasana_database"
                ).addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // Pre-populate with seed data
                        INSTANCE?.let { database ->
                            CoroutineScope(Dispatchers.IO).launch {
                                ShasanaDataSeeder.seedDatabase(database.shasanaDao())
                            }
                        }
                    }
                }).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
