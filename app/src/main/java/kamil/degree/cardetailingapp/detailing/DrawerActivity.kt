package kamil.degree.cardetailingapp.detailing

import android.os.Bundle
import androidx.navigation.ui.AppBarConfiguration
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kamil.degree.cardetailingapp.R
import kamil.degree.cardetailingapp.databinding.ActivityDrawerBinding
import kamil.degree.cardetailingapp.detailing.one.OneFragment
import kamil.degree.cardetailingapp.detailing.settings.SettingsFragment
import kamil.degree.cardetailingapp.detailing.two.TwoFragment

class DrawerActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityDrawerBinding
    private val oneFragment = OneFragment()
    private val twoFragment = TwoFragment()
    private val settingsFragment = SettingsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDrawerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    loadFragment(oneFragment)
                    true
                }
                R.id.message -> {
                    loadFragment(twoFragment)
                    true
                }
                R.id.settings -> {
                    loadFragment(settingsFragment)
                    true
                }
                else -> true
            }
        }
    }
    fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment)
        transaction.commit()
    }



}