package com.tdlschool.hometaskgame.ui

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import com.tdlschool.hometaskgame.App
import com.tdlschool.hometaskgame.common.*
import com.tdlschool.hometaskgame.repository.GameRepository
import com.tdlschool.hometaskgame.repository.models.GamePiece
import com.tdlschool.hometaskgame.repository.models.HighScoreModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import java.util.*
import javax.inject.Inject

class GameViewModel : ViewModel() {

    @Inject
    lateinit var repository: GameRepository

    private var timer = object : CountDownTimer(MAX_GAME_TIME, 10){
        override fun onTick(elapsedTime: Long) {
            val date = Date(MAX_GAME_TIME - elapsedTime)
            _gameTimer.tryEmit(date.time.toTimeString())
        }

        override fun onFinish() {
            onGameOver(false)
        }
    }
    private var counter: Int = 0

    private val _highScores = MutableSharedFlow<List<HighScoreModel>>(replay = 1)
    private val _gamePieces = MutableSharedFlow<List<GamePiece>>(replay = 1)
    private val _nextButton = MutableSharedFlow<Int>(replay = 1)
    private val _gameTimer = MutableSharedFlow<String>(replay = 1)
    private val _onGameOver = MutableSharedFlow<String?>(replay = 1)
    private val _pushCounter = MutableSharedFlow<Int>(replay = 1)

    val highScores: SharedFlow<List<HighScoreModel>> = _highScores
    val gamePieces: SharedFlow<List<GamePiece>> = _gamePieces
    val gameTimer: SharedFlow<String> = _gameTimer
    val pushCounter: SharedFlow<Int> = _pushCounter


    init {
        App.component.inject(this)
        launchIO {
            repository.highScores.collect { notes ->
                _highScores.tryEmit(notes)
            }
        }
    }

    fun startGame() {
        // Reset the last value to null (Skipped by UI)
        _onGameOver.tryEmit(null)
        counter = 0
        val pieces = mutableListOf<GamePiece>()
        (1..PIECE_COUNT/2).forEach { number ->
            pieces.add(GamePiece(number))
            pieces.add(GamePiece(number))
        }

        pieces.shuffle()
        pieces.shuffle()

        _nextButton.tryEmit(1)
        _gamePieces.tryEmit(pieces)
        _pushCounter.tryEmit(counter)
        timer.start()
    }

    fun openPiece(value: Int) {
        val numbers = _gamePieces.replayCache[0].map { it.copy() }
        val selectedNumber = numbers.first { it.value == value }
        val lastSelected = numbers.first { it.value == (value - 1) }

        counter++
        _pushCounter.tryEmit(counter)

        if (selectedNumber.isTapped) return
        selectedNumber.isTapped = true

        if (selectedNumber.value == _nextButton.replayCache[0]) {
            selectedNumber.isFound = true
        }else{
            if(selectedNumber.value > 1) {
                lastSelected.isTapped = false
            }
        }

        if (numbers.find { !it.isFound } == null) {
            onGameOver(true)
        }else if (selectedNumber.isFound) {
            _nextButton.tryEmit(selectedNumber.value.plus(1))
        }
        _gamePieces.tryEmit(numbers)
    }

    private fun onGameOver(isGameWon: Boolean) {
        timer.cancel()
        val score = _gameTimer.replayCache[0]
        val taps = _pushCounter.replayCache[0]
        _onGameOver.tryEmit(_gameTimer.replayCache[0])

        if (isGameWon) {
            launchIO {
                val id = _highScores.replayCache.lastOrNull()?.maxOfOrNull { it.id }?.plus(1) ?: 0
                repository.insertHighScore(HighScoreModel(id, Date().time.toDateString(), score, taps.toString()))
            }
        }
    }
}
