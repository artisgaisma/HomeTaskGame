package com.tdlschool.hometaskgame.ui.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.tdlschool.hometaskgame.R
import com.tdlschool.hometaskgame.common.SPAN_COUNT
import com.tdlschool.hometaskgame.common.launchMain
import com.tdlschool.hometaskgame.common.openFragment
import com.tdlschool.hometaskgame.databinding.FragmentGameBinding
import com.tdlschool.hometaskgame.ui.GameViewModel
import kotlinx.coroutines.flow.collect
import timber.log.Timber

class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding

    private val viewModel by activityViewModels<GameViewModel>()
    private val adapter by lazy {
        GameAdapter { gamePiece ->
            Timber.d("Game piece clicked: $gamePiece")
            viewModel.openPiece(gamePiece.value)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentGameBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.gameGrid.adapter = adapter
        binding.gameGrid.layoutManager = GridLayoutManager(requireContext(), SPAN_COUNT)

        viewModel.startGame()

        binding.endGame.setOnClickListener {
            openFragment(R.id.navigation_menu)
        }

        launchMain {
            viewModel.gamePieces.collect { pieces ->
                adapter.gamePieces = pieces
            }
        }
        /** * Shows time after successful game end */
        launchMain {
            viewModel.gameTimer.collect { time ->
                binding.gameTimer.text = time
            }
        }
        /** * Shows count of pushes*/
        launchMain {
            viewModel.pushCounter.collect { count ->
                binding.pushCounter.text = getString(R.string.push_counter_template, count)
            }
        }
    }
}
