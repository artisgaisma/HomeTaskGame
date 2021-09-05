package com.tdlschool.hometaskgame.repository.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tdlschool.hometaskgame.common.HIGH_SCORE_TABLE

@Entity(tableName = HIGH_SCORE_TABLE)
data class HighScoreModel(
    @PrimaryKey val id: Int,
    val date: String,
    val score: String,
    val taps: String
)
