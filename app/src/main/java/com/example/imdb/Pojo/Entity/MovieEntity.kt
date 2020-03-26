package com.example.imdb.Pojo.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Movies")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    var Id: Int?,
    var MovieId: Int,
    var Title: String,
    var Description: String,
    var ReleaseDate: String,
    var AverageVote: Double,
    var ImagePAth: String
) {
    constructor() : this(null, 0, "", "", "", 0.0, "")
}