package com.example.alcoholcounter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.alcoholcounter.ui.events.EventListFragment
import com.example.alcoholcounter.ui.map.MapFragment
import com.example.alcoholcounter.ui.statistics.StatisticsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_map, R.id.navigation_statistics, R.id.navigation_events))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.setOnNavigationItemSelectedListener {
            var selectedFragment: Fragment? = null;
            when (it.itemId) {
                R.id.navigation_map -> selectedFragment = MapFragment()
                R.id.navigation_statistics -> selectedFragment = StatisticsFragment()
                R.id.navigation_events -> selectedFragment = EventListFragment()
            }
            val fragmentManager = supportFragmentManager
            fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            if (selectedFragment != null) {
                val transaction = fragmentManager.beginTransaction()
                transaction.replace(R.id.nav_host_fragment, selectedFragment)
                transaction.commit()
            }
            true
        }
    }

    fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment, fragment)
        transaction.addToBackStack(fragment.tag)
        transaction.commit()
    }
}

