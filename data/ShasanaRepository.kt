package com.mindmatrix.nammashasane.data

import androidx.lifecycle.LiveData
import com.mindmatrix.nammashasane.model.DamageReport
import com.mindmatrix.nammashasane.model.Shasana

class ShasanaRepository(private val dao: ShasanaDao) {

    val allShasanas: LiveData<List<Shasana>> = dao.getAllShasanas()
    val userAddedShasanas: LiveData<List<Shasana>> = dao.getUserAddedShasanas()
    val damagedShasanas: LiveData<List<Shasana>> = dao.getDamagedShasanas()

    suspend fun insert(shasana: Shasana): Long = dao.insertShasana(shasana)

    suspend fun insertAll(shasanas: List<Shasana>) = dao.insertAllShasanas(shasanas)

    suspend fun update(shasana: Shasana) = dao.updateShasana(shasana)

    suspend fun delete(shasana: Shasana) = dao.deleteShasana(shasana)

    suspend fun getById(id: Int): Shasana? = dao.getShasanaById(id)

    suspend fun getCount(): Int = dao.getCount()

    suspend fun reportDamage(report: DamageReport) = dao.insertDamageReport(report)

    fun getDamageReports(shasanaId: Int): LiveData<List<DamageReport>> =
        dao.getDamageReportsForShasana(shasanaId)
}
