package com.tdlschool.hometaskgame.repository

import com.tdlschool.hometaskgame.repository.cache.GameDao
import com.tdlschool.hometaskgame.repository.models.HighScoreModel

class GameRepository(private val gameDao: GameDao) {

    val highScores = gameDao.getHighScores()

    fun insertHighScore(highScoreModel: HighScoreModel) = gameDao.insertHighScore(highScoreModel)

}
