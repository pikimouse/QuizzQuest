package com.quizzgame.quizzquest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.quizzgame.quizzquest.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    private val questions = listOf(
        Question("Quelle est la capitale de la France?"),
        Question("Quel est l'élément chimique avec le symbole H?"),
        Question("Combien de continents y a-t-il sur Terre?")
    )

    private lateinit var players: List<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Assuming players were set in FirstFragment and passed as arguments
        arguments?.let {
            players = it.getStringArrayList("players") ?: emptyList()
        }

        /*binding.startGameButton.setOnClickListener {
            val action = SecondFragmentDirections.actionSecondFragmentToQuizFragment()
            action.players = ArrayList(players)
            action.questions = ArrayList(questions)
            findNavController().navigate(action)
        }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
