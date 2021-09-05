package com.tdlschool.hometaskgame.ui.highscores

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.tdlschool.hometaskgame.R
import com.tdlschool.hometaskgame.common.launchMain
import com.tdlschool.hometaskgame.common.openFragment
import com.tdlschool.hometaskgame.databinding.FragmentHighscoresBinding
import com.tdlschool.hometaskgame.ui.GameViewModel
import kotlinx.coroutines.flow.collect

class HighScoresFragment : Fragment() {

    private lateinit var binding: FragmentHighscoresBinding

    private val viewModel by activityViewModels<GameViewModel>()
    private val adapter by lazy { HighScoreAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHighscoresBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.highScoreList.adapter = adapter

        binding.closeHighScores.setOnClickListener {
            openFragment(R.id.navigation_menu)
        }

        launchMain {
            viewModel.highScores.collect { highScores ->
                binding.emptyHighScore.visibility = if (highScores.isEmpty()) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
                adapter.highScores = highScores
            }
        }
    }
}
