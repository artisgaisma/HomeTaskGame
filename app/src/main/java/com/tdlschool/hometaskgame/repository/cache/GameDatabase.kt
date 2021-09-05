package com.tdlschool.hometaskgame.repository.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tdlschool.hometaskgame.repository.models.HighScoreModel

@Database(entities = [HighScoreModel::class], version = 1, exportSchema = false)
abstract class GameDatabase : RoomDatabase() {

    abstract fun gameDao(): GameDao

}
