package com.mindmatrix.nammashasane.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mindmatrix.nammashasane.model.DamageReport
import com.mindmatrix.nammashasane.model.Shasana

@Dao
interface ShasanaDao {

    @Query("SELECT * FROM shasanas ORDER BY id ASC")
    fun getAllShasanas(): LiveData<List<Shasana>>

    @Query("SELECT * FROM shasanas WHERE id = :id")
    suspend fun getShasanaById(id: Int): Shasana?

    @Query("SELECT * FROM shasanas WHERE isUserAdded = 1")
    fun getUserAddedShasanas(): LiveData<List<Shasana>>

    @Query("SELECT * FROM shasanas WHERE isDamaged = 1")
    fun getDamagedShasanas(): LiveData<List<Shasana>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShasana(shasana: Shasana): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllShasanas(shasanas: List<Shasana>)

    @Update
    suspend fun updateShasana(shasana: Shasana)

    @Delete
    suspend fun deleteShasana(shasana: Shasana)

    @Query("SELECT COUNT(*) FROM shasanas")
    suspend fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDamageReport(report: DamageReport)

    @Query("SELECT * FROM damage_reports WHERE shasanaId = :shasanaId")
    fun getDamageReportsForShasana(shasanaId: Int): LiveData<List<DamageReport>>
}
