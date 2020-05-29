package com.example.alcoholcounter

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.alcoholcounter.ui.drinks.DrinkEditFragment
import com.example.alcoholcounter.ui.events.EventListFragment
import com.example.alcoholcounter.ui.map.MapFragment
import com.example.alcoholcounter.ui.statistics.StatisticsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        private const val STATE_SAVE_STATE = "save_state"
        private const val STATE_KEEP_FRAGS = "keep_frags"
        private const val STATE_HELPER = "helper"
    }

    private lateinit var stateHelper: FragmentStateHelper

    private val fragments = mutableMapOf<Int, Fragment>()

    private val navigationSelectionListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        var selectedFragment: Fragment? = fragments[item.itemId] ;
        if (selectedFragment == null) {
            selectedFragment = when (item.itemId) {
                R.id.navigation_map -> MapFragment()
                R.id.navigation_statistics -> DrinkEditFragment()
                R.id.navigation_events -> EventListFragment()
                else -> EventListFragment()
            }
        }
        fragments[item.itemId] = selectedFragment

        if (nav_view.selectedItemId != 0) {
            saveCurrentState()
            stateHelper.restoreState(selectedFragment, item.itemId)
        }

        val fragmentManager = supportFragmentManager
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
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

        stateHelper = FragmentStateHelper(supportFragmentManager)

        nav_view.setOnNavigationItemSelectedListener(navigationSelectionListener)

        if (savedInstanceState == null) {
            nav_view.selectedItemId = R.id.navigation_events
        } else {
            /*state_switch.isChecked = savedInstanceState.getBoolean(STATE_SAVE_STATE)
            keep_switch.isChecked = savedInstanceState.getBoolean(STATE_KEEP_FRAGS)*/

            val helperState = savedInstanceState.getBundle(STATE_HELPER)
            if (helperState != null) {
                stateHelper.restoreHelperState(helperState)
            }
        }


        if (ContextCompat.checkSelfPermission(MainApp.appContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 666)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        // Make sure we save the current tab's state too!
        saveCurrentState()
        super.onSaveInstanceState(outState)
    }

    private fun saveCurrentState() {
        fragments[nav_view.selectedItemId]?.let { oldFragment->
            stateHelper.saveState(oldFragment, nav_view.selectedItemId)
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
