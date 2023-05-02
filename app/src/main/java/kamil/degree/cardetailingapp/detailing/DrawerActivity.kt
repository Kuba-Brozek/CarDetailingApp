package kamil.degree.cardetailingapp.detailing

import android.os.Bundle
import androidx.navigation.ui.AppBarConfiguration
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kamil.degree.cardetailingapp.R
import kamil.degree.cardetailingapp.databinding.ActivityDrawerBinding

class DrawerActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityDrawerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDrawerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    true
                }
                R.id.message -> {
                    true
                }
                R.id.settings -> {
                    true
                }

                else -> {true}
            }
        }
    }
    fun loadFragment(fragment: Fragment){
        val transaction = this.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment)
        transaction.commit()
    }



}