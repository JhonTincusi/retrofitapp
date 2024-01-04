package com.example.appointmentsapp.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.appointmentsapp.R
import com.example.appointmentsapp.databinding.ActivityDashboardBinding
import com.example.appointmentsapp.io.response.AuthorizationResponse
import com.example.appointmentsapp.util.PreferenceHelper
import com.example.appointmentsapp.util.PreferenceHelper.get
import com.google.gson.Gson

class DashboardActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarDashboard.toolbar)
        binding.appBarDashboard.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_dashboard)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_preferences
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //Get Authorization object
        val prefs = PreferenceHelper.customPrefs(this, "UserLoginPrefs")
        val authorizationResponseJson = prefs["AuthorizationResponse", ""]

        if (authorizationResponseJson.isNotEmpty()) {
            val gson = Gson()
            val authorizationResponse = gson.fromJson(authorizationResponseJson, AuthorizationResponse::class.java)

            // Extraer la lista de nombres de módulos
            val moduleNames = authorizationResponse.data.firstOrNull()?.systems?.firstOrNull()?.modules?.map { it.name } ?: listOf()
            val menu = navView.menu
            menu.clear()
            moduleNames.forEachIndexed { index, name ->
                menu.add(Menu.NONE, Menu.FIRST + index, Menu.NONE, name).apply {
                    icon = getDrawable(R.drawable.ic_menu_camera) // Replace with actual icon
                }
            }
        }
        else {
            // Manejar el caso en que no hay datos guardados en SharedPreferences
        }


        //val moduleNames = listOf("Module 1", "Module 2", "Module 3", "Module 4")

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.dashboard, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_dashboard)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.btn_logout -> {
                logout(applicationContext)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun logout(context: Context) {
        val sharedPreferences = context.getSharedPreferences("UserLoginPrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP || keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            PreferenceHelper.showAllPreferences(this)
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}