package com.example.breakingbadapp.Model


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "character_model")
data class CharacterModelItem(
    @SerializedName("char_id")
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val img: String?,
    val name: String?,
    val nickname: String?,
) : Parcelable
