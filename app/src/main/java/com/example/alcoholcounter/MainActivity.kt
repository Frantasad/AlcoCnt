package com.example.alcoholcounter

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.alcoholcounter.ui.events.EventListFragment
import com.example.alcoholcounter.ui.map.MapFragment
import com.example.alcoholcounter.ui.statistics.StatisticsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val dataHandler : DataHandler = DataHandler(this)

    companion object {
        private const val STATE_SAVE_STATE = "save_state"
        private const val STATE_KEEP_FRAGS = "keep_frags"
        private const val STATE_HELPER = "helper"
    }

    private lateinit var stateHelper: FragmentStateHelper

    private val fragments = mutableMapOf<Int, Fragment>()

    private val navigationSelectionListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        //Toast.makeText(this, "item id" + item.itemId, Toast.LENGTH_SHORT).show()

        var selectedFragment: Fragment? = fragments[item.itemId] ;
        if (selectedFragment == null) {
            when (item.itemId) {
                R.id.navigation_map -> selectedFragment = MapFragment()
                R.id.navigation_statistics -> selectedFragment = StatisticsFragment()
                R.id.navigation_events -> selectedFragment = EventListFragment()
                else -> selectedFragment = EventListFragment()
            }
        }

        fragments[item.itemId] = selectedFragment



        if (nav_view.selectedItemId != 0) {
            saveCurrentState()
            stateHelper.restoreState(selectedFragment, item.itemId)
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, selectedFragment)
            .commitNowAllowingStateLoss()

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




    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dataHandler.loadEvents()
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        //val navController = findNavController(R.id.nav_host_fragment)


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

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

    override fun onDestroy() {
        super.onDestroy()
        dataHandler.saveEvents()
    }*/

    fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(fragment.tag)
        transaction.commit()
    }
}
