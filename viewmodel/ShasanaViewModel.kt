package com.mindmatrix.nammashasane.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.mindmatrix.nammashasane.data.ShasanaDatabase
import com.mindmatrix.nammashasane.data.ShasanaRepository
import com.mindmatrix.nammashasane.model.DamageReport
import com.mindmatrix.nammashasane.model.Shasana
import kotlinx.coroutines.launch

class ShasanaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ShasanaRepository
    val allShasanas: LiveData<List<Shasana>>
    val userAddedShasanas: LiveData<List<Shasana>>

    init {
        val dao = ShasanaDatabase.getDatabase(application).shasanaDao()
        repository = ShasanaRepository(dao)
        allShasanas = repository.allShasanas
        userAddedShasanas = repository.userAddedShasanas
    }

    fun insert(shasana: Shasana) = viewModelScope.launch {
        repository.insert(shasana)
    }

    fun update(shasana: Shasana) = viewModelScope.launch {
        repository.update(shasana)
    }

    fun delete(shasana: Shasana) = viewModelScope.launch {
        repository.delete(shasana)
    }

    fun reportDamage(shasana: Shasana, description: String, lat: Double, lng: Double) =
        viewModelScope.launch {
            val updatedShasana = shasana.copy(isDamaged = true, damageReport = description)
            repository.update(updatedShasana)
            repository.reportDamage(
                DamageReport(
                    shasanaId = shasana.id,
                    description = description,
                    latitude = lat,
                    longitude = lng
                )
            )
        }

    fun addUserShasana(
        title: String, dynasty: String, period: String, location: String,
        lat: Double, lng: Double, translationKannada: String, translationEnglish: String,
        king: String, giftOrLaw: String, imagePath: String
    ) = viewModelScope.launch {
        val shasana = Shasana(
            title = title,
            dynasty = dynasty,
            period = period,
            location = location,
            latitude = lat,
            longitude = lng,
            translationKannada = translationKannada,
            translationEnglish = translationEnglish,
            king = king,
            giftOrLaw = giftOrLaw,
            imageUrl = "",
            localImagePath = imagePath,
            isUserAdded = true
        )
        repository.insert(shasana)
    }
}
