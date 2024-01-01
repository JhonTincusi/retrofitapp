package com.example.appointmentsapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.appointmentsapp.R
import com.example.appointmentsapp.io.ApiService
import com.example.appointmentsapp.io.response.LoginResponse
import com.example.appointmentsapp.util.PreferenceHelper
import com.example.appointmentsapp.util.PreferenceHelper.get
import com.example.appointmentsapp.util.PreferenceHelper.set
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity() {
    private val apiService: ApiService by lazy {
        ApiService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnGoDashboard = findViewById<Button>(R.id.btn_go_to_dashboard)
        btnGoDashboard.setOnClickListener{
            performLogin()
        }

        val preferences = PreferenceHelper.defaultPrefs(this)
        //Condicion para saber si existe una sesion
        //if (preferences["access", ""].contains(".")) //Al abrir la app por 1ra vez
        if (preferences["session", false]) //Al abrir la app por 1ra vez
            goToLogin()
    }

    private fun goToDashboard() {
        var i = Intent(this, DashboardActivity::class.java)
        startActivity(i)
        finish()
    }

    private fun goToLogin() {
        var i = Intent(this, MainActivity::class.java)
        createSessionPreference()
        startActivity(i)
        finish()
    }

    private fun createSessionPreference(){  //access: String
        val preferences = PreferenceHelper.defaultPrefs(this)
        preferences["session"] = true
    }

    //Metod for validation with api, get id for form
    private fun performLogin(){
        val etUsername = findViewById<EditText>(R.id.et_username).text.toString()
        val etPassword = findViewById<EditText>(R.id.et_password).text.toString()

        val call = apiService.postLogin(etUsername, etPassword)
        call.enqueue(object: retrofit2.Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                val responseString = response.toString()
                // Mostrar el valor completo de response en Logcat
                Log.d("MainActivity", "Response: $responseString")

                if (response.isSuccessful){
                    val loginResponse = response.body()
                    if (loginResponse == null) {
                        Toast.makeText(applicationContext, "Se produjo un error en el servidor 1", Toast.LENGTH_SHORT).show()
                        return
                    }
                    if (loginResponse.status){
                        createSessionPreference() //loginResponse.access
                        goToDashboard()
                    } else {
                        Toast.makeText(applicationContext, "Las credenciales son incorrectas", Toast.LENGTH_SHORT).show()
                    }

                }else{
                    Toast.makeText(applicationContext, "Se produjo un error en el servidor 2", Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "Se produjo un error en el servidor 3", Toast.LENGTH_SHORT).show()

            }


        })
    }
}