package com.quizzgame.quizzquest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.quizzgame.quizzquest.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    // Liste pour stocker les noms des joueurs
    private val players = mutableListOf<String>()
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        players.clear()
        // Initialiser l'adapter avec une liste vide
        adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,  // Layout pour chaque élément
            players
        )

        // Assigner l'adapter au ListView
        binding.playersListView.adapter = adapter

        // Lorsque le bouton est cliqué
        binding.addPlayerButton.setOnClickListener {
            val playerName = binding.playerNameEditText.text.toString()

            // Ajouter le nom si non vide et non déjà présent dans la liste
            if (playerName.isNotBlank()) {
                if (players.contains(playerName)) {
                    // Afficher un message d'erreur ou une indication que le joueur existe déjà
                    binding.playerNameEditText.error = "Ce joueur est déjà dans la liste"
                } else {
                    players.add(playerName)
                    adapter.notifyDataSetChanged()  // Met à jour le ListView

                    // Effacer le champ texte
                    binding.playerNameEditText.text.clear()
                    if (players.size == 1) {
                        binding.startGameButton.visibility = View.VISIBLE
                    }
                }
            } else {
                // Afficher un message d'erreur ou une indication que le champ est vide
                binding.playerNameEditText.error = "Le nom ne peut pas être vide"
            }
        }
        binding.startGameButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putStringArrayList("players", ArrayList(players))
            findNavController().navigate(R.id.action_FirstFragment_to_QuizFragment, bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}