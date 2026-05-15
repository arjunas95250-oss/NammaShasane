package com.mindmatrix.nammashasane.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "damage_reports")
data class DamageReport(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val shasanaId: Int,
    val description: String,
    val reportedAt: Long = System.currentTimeMillis(),
    val latitude: Double,
    val longitude: Double
)
