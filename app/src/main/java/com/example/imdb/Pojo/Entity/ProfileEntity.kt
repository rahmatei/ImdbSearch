package com.example.imdb.Pojo.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Profile")
data class ProfileEntity(
    @PrimaryKey(autoGenerate = true)
    var Id: Int?,
    var Name: String,
    var Family: String,
    var Email: String
) {
    constructor() : this(1, "", "", "")
}