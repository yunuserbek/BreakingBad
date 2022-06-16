package com.example.breakingbadapp.Model


import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CharacterModelItem(
    val charId: Int?,
    val img: String?,
    val name: String?,
    val nickname: String?,
) : Parcelable