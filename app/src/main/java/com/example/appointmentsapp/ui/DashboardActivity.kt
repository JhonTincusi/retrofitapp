package com.example.appointmentsapp.ui

import android.content.Context import android.content.Intent import android.os.Bundle import android.util.Log import android.view.KeyEvent import android.view.Menu import android.view.MenuItem import android.widget.Toast import com.google.android.material.snackbar.Snackbar import com.google.android.material.navigation.NavigationView import androidx.navigation.findNavController import androidx.navigation.ui.AppBarConfiguration import androidx.navigation.ui.navigateUp import androidx.navigation.ui.setupActionBarWithNavController import androidx.navigation.ui.setupWithNavController import androidx.drawerlayout.widget.DrawerLayout import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import com.example.appointmentsapp.R import com.example.appointmentsapp.databinding.ActivityDashboardBinding import com.example.appointmentsapp.io.response.AuthorizationResponse import com.example.appointmentsapp.ui.gallery.GalleryFragment import com.example.appointmentsapp.ui.gallery.GalleryViewModel import com.example.appointmentsapp.ui.home.HomeViewModel import com.example.appointmentsapp.util.PreferenceHelper import com.example.appointmentsapp.util.PreferenceHelper.get import com.google.gson.Gson

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
            val authorizationResponse =
                gson.fromJson(authorizationResponseJson, AuthorizationResponse::class.java)
            val modules = authorizationResponse.data.firstOrNull()?.systems?.firstOrNull()?.modules ?: listOf()
            val menu = navView.menu
            menu.clear()

            modules.forEach { module ->
                menu.add(Menu.NONE, module.module_id, Menu.NONE, module.name).apply {
                    // Aquí puedes asignar íconos dinámicamente si tienes un mapeo de módulo a ícono
                    icon = ContextCompat.getDrawable(this@DashboardActivity, R.drawable.ic_menu_camera) // Reemplaza con el ícono real
                }
            }

            navView.setNavigationItemSelectedListener { menuItem ->
                // Encuentra el módulo seleccionado basado en el ID del elemento del menú
                val selectedModule = modules.find { it.module_id == menuItem.itemId }
                when (selectedModule?.url) {
                    "/category" -> {
                        // Navegar a Categorías
                        Log.d("xdd","catt")
                    }
                    "/subcategory" -> {
                        // Navegar a Subcategorías
                    }
                    "/unit" -> {
                        // Navegar a Unidades
                    }
                    "/presentation" -> {
                        // Navegar a Presentaciones
                    }
                    "/product-input" -> {
                        Log.d("xdd","catt")
                        // Navegar a Ingreso de Productos
                    }
                    "/product-output" -> {
                        // Navegar a Salida de Productos
                    }
                    "/product" -> {
                        // Navegar a Productos
                    }
                    "/brand" -> {
                        // Navegar a Marcas
                    }
                    "/product-subcategory" -> {
                        // Navegar a Subcategoría de Productos
                    }
                    "/product-presentation" -> {
                        // Navegar a Presentación de productos
                    }
                    "/specification" -> {
                        // Navegar a Especificaciones
                    }
                    "/specification-detail" -> {
                        // Navegar a Detalle de Especificación
                    }
                    "/product-detail" -> {
                        // Navegar a Detalle de Productos
                    }
                    else -> {
                        Toast.makeText(this, "Opción no reconocida", Toast.LENGTH_SHORT).show()
                    }
                }
                drawerLayout.closeDrawer(GravityCompat.START)
                true
            }
        } else {
            // Manejar el caso en que no hay datos guardados en SharedPreferences
        }


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