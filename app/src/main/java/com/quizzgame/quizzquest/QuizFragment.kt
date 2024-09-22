package com.quizzgame.quizzquest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.quizzgame.quizzquest.databinding.FragmentQuizBinding
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.random.Random

class QuizFragment : Fragment() {

    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!

    private lateinit var players: List<String>
    private lateinit var questions: List<String>
    private var currentQuestionIndex = 0
    private var currentPlayerIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Récupérer les joueurs via les arguments
        players = arguments?.getStringArrayList("players") ?: emptyList()

        if (players.isEmpty()) {
            // Gérer le cas où il n'y a pas de joueurs
            binding.questionTextView.text = "Aucun joueur trouvé"
            binding.nextQuestionButton.visibility = View.GONE
        } else {
            // Charger les questions à partir du fichier texte
            loadQuestions()

            // Afficher la première question
            showNextQuestion()
        }

        // Gestion du bouton "Suivant"
        binding.nextQuestionButton.setOnClickListener {
            showNextQuestion()
        }

        // Gestion du bouton "Recommencer", qui est caché au début
        binding.restartGameButton.setOnClickListener {
            restartGame()
        }
    }
    // Fonction pour charger les questions à partir du fichier texte
    private fun loadQuestions() {
        val inputStream = resources.openRawResource(R.raw.questions)
        val reader = BufferedReader(InputStreamReader(inputStream))
        questions = reader.readLines()
        reader.close()
    }
    // Fonction pour afficher la question suivante
    private fun showNextQuestion() {
        if (questions.isNotEmpty()) {
            currentQuestionIndex = Random.nextInt(questions.size)
            val currentPlayer = players[currentPlayerIndex]
            currentPlayerIndex++
            if(currentPlayerIndex == players.size)
                currentPlayerIndex=0;
            val question = questions[currentQuestionIndex]
            binding.questionTextView.text = "Question pour $currentPlayer: ${question}"
        } else {
            // Terminer le quiz
            //endQuiz()
            binding.questionTextView.text = "Aucune question disponible"
        }
    }

    // Fonction pour terminer le quiz et afficher le bouton "Recommencer"
    private fun endQuiz() {
        binding.questionTextView.text = "Quiz terminé !"
        binding.nextQuestionButton.visibility = View.GONE  // Cacher le bouton "Suivant"
        binding.restartGameButton.visibility = View.VISIBLE  // Afficher le bouton "Recommencer"
    }

    // Fonction pour réinitialiser le jeu
    private fun restartGame() {
        // Réinitialiser les index
        currentQuestionIndex = 0
        currentPlayerIndex = 0

        // Cacher le bouton "Recommencer" et afficher le bouton "Suivant"
        binding.restartGameButton.visibility = View.GONE
        binding.nextQuestionButton.visibility = View.VISIBLE

        // Relancer le quiz
        showNextQuestion()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
