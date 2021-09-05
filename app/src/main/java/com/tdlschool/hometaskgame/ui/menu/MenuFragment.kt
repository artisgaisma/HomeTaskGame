package com.tdlschool.hometaskgame.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tdlschool.hometaskgame.R
import com.tdlschool.hometaskgame.common.*
import com.tdlschool.hometaskgame.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {

    private lateinit var binding: FragmentMenuBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMenuBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.startEasyGame.setOnClickListener {
            openFragment(R.id.navigation_game)
            PIECE_COUNT = EASY_COUNT
            SPAN_COUNT = 2

        }
        binding.startMediumGame.setOnClickListener {
            openFragment(R.id.navigation_game)
            PIECE_COUNT = MEDIUM_COUNT
            SPAN_COUNT = 4
        }
        binding.startHardGame.setOnClickListener {
            openFragment(R.id.navigation_game)
            PIECE_COUNT = HARD_COUNT
            SPAN_COUNT = 4
        }

        binding.showHighScores.setOnClickListener {
            openFragment(R.id.navigation_high_scores)
        }
    }
}
