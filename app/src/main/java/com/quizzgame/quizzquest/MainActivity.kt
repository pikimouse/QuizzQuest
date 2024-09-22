package com.quizzgame.quizzquest
import com.google.android.gms.ads.MobileAds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.quizzgame.quizzquest.databinding.ActivityMainBinding
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adView: AdView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val backgroundScope = CoroutineScope(Dispatchers.IO)
        backgroundScope.launch {
            // Initialize the Google Mobile Ads SDK on a background thread.
            MobileAds.initialize(this@MainActivity) {}

        }

        // Create a new ad view.
        val adView = AdView(this)
        adView.adUnitId = "ca-app-pub-5437932152745997/3397338111"
        adView.setAdSize(AdSize.BANNER)
        this.adView = adView

// Replace ad container with new ad view.
        binding.adViewContainer.removeAllViews()
        binding.adViewContainer.addView(adView)

        // Start loading the ad in the background.
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                // Gérer l'option "Paramètres" ici si besoin
                true
            }
            R.id.action_restart_game -> {
                // Gérer l'option "Relancer le jeu"
                restartGame()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun restartGame() {
        // Ici, vous pouvez réinitialiser l'état du jeu
        // Vider la liste des joueurs, réinitialiser les variables, et revenir à l'écran d'ajout des joueurs
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        navController.popBackStack(R.id.FirstFragment, false) // Revenir à l'écran d'ajout des joueurs
    }
}
