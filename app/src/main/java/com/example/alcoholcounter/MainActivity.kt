package com.example.alcoholcounter

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.alcoholcounter.ui.events.EventListFragment
import com.example.alcoholcounter.ui.map.MapFragment
import com.example.alcoholcounter.ui.statistics.StatisticsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val fragments = mutableMapOf<Int, Fragment>()

    private val navigationSelectionListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        var selectedFragment: Fragment? = fragments[item.itemId] ;
        if (selectedFragment == null) {
            selectedFragment = when (item.itemId) {
                R.id.navigation_map -> MapFragment()
                R.id.navigation_statistics -> StatisticsFragment()
                R.id.navigation_events -> EventListFragment()
                else -> EventListFragment()
            }
        }
        fragments[item.itemId] = selectedFragment

        val fragmentManager = supportFragmentManager
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        // Poslouchejte lint warningy, selectedFragment fakt nemuze byt null
        if (selectedFragment != null) {
            fragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, selectedFragment)
                .commit()
        }
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nav_view.setOnNavigationItemSelectedListener(navigationSelectionListener)

        if (savedInstanceState == null) {
            nav_view.selectedItemId = R.id.navigation_events
        }

        if (ContextCompat.checkSelfPermission(MainApp.appContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 666)
        }
    }

    fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment, fragment)
        transaction.addToBackStack(fragment.tag)
        transaction.commit()
    }

    fun setActionBarTitle(title: String?) {
        supportActionBar!!.title = title        // Kdyz bude supportActionBar null -> crash
    }

}
