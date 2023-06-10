package kamil.degree.cardetailingapp.detailing

import android.os.Bundle
import androidx.navigation.ui.AppBarConfiguration
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kamil.degree.cardetailingapp.R
import kamil.degree.cardetailingapp.databinding.ActivityDrawerBinding
import kamil.degree.cardetailingapp.detailing.managebusiness.BusinessBucketFragment
import kamil.degree.cardetailingapp.detailing.settings.SettingsFragment
import kamil.degree.cardetailingapp.detailing.searchbusiness.BusinessSearchFragment

class DrawerActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityDrawerBinding
    private val businessBucketFragment = BusinessBucketFragment()
    private val businessSearchFragment = BusinessSearchFragment()
    private val settingsFragment = SettingsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDrawerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        loadFragment(businessBucketFragment)

        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    loadFragment(businessBucketFragment)
                    true
                }
                R.id.message -> {
                    loadFragment(businessSearchFragment)
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