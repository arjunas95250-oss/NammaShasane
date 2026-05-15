package com.mindmatrix.nammashasane.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "shasanas")
data class Shasana(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @SerializedName("title") val title: String,
    @SerializedName("dynasty") val dynasty: String,
    @SerializedName("period") val period: String,
    @SerializedName("location") val location: String,
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("translationKannada") val translationKannada: String,
    @SerializedName("translationEnglish") val translationEnglish: String,
    @SerializedName("king") val king: String,
    @SerializedName("giftOrLaw") val giftOrLaw: String,
    @SerializedName("imageUrl") val imageUrl: String = "",
    @SerializedName("isUserAdded") val isUserAdded: Boolean = false,
    @SerializedName("isDamaged") val isDamaged: Boolean = false,
    @SerializedName("damageReport") val damageReport: String = "",
    @SerializedName("localImagePath") val localImagePath: String = ""
)
